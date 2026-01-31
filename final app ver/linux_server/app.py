from flask import Flask, request, jsonify, render_template, send_file
from crypto_utils import EncryptedDataProcessor, ServerCryptoUtils
import sqlite3
import os
import time
from datetime import datetime

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
TEMPLATES_DIR = os.path.join(BASE_DIR, 'templates')
DB_PATH = os.path.join(BASE_DIR, 'dns_analysis.db')

app = Flask(__name__, template_folder=TEMPLATES_DIR)

SECURE_API_KEY = 'datasentry-secure-api-key-2024'
QUICK_API_KEY = 'datasentry-quick-api-key-12345'
READONLY_API_KEYS = {SECURE_API_KEY, QUICK_API_KEY}
WRITE_API_KEYS = {SECURE_API_KEY}

# Initialize crypto utils and encrypted data processor
crypto_utils = ServerCryptoUtils()
processor = EncryptedDataProcessor(crypto_utils, db_path=DB_PATH)


def _fetchone_value(cursor, default=0):
    row = cursor.fetchone()
    if not row:
        return default
    value = row[0]
    return default if value is None else value


def ensure_database():
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS dns_queries (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            timestamp INTEGER,
            domain TEXT,
            app_name TEXT,
            source_ip TEXT,
            dest_ip TEXT,
            protocol TEXT,
            size_bytes INTEGER,
            is_risk INTEGER DEFAULT 0,
            risk_score INTEGER DEFAULT 0,
            risk_reasons TEXT,
            analysis_timestamp INTEGER,
            analysis_engine TEXT,
            device_id TEXT
        )
    ''')
    conn.commit()
    conn.close()


ensure_database()

@app.route('/')
def dashboard():
    """Serve the web dashboard"""
    try:
        index_path = os.path.join(TEMPLATES_DIR, 'index.html')
        if not os.path.exists(index_path):
            return f"Dashboard file missing: {index_path}", 500
        return send_file(index_path)
    except Exception as e:
        return f"Dashboard template error: {str(e)}", 500

@app.route('/api/stats', methods=['GET'])
def get_stats():
    """Get server statistics"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            return jsonify({'error': 'Invalid API key'}), 401
        
        ensure_database()

        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        ts_seconds_expr = "CASE WHEN timestamp > 1000000000000 THEN timestamp / 1000 ELSE timestamp END"

        now = int(time.time())
        hour_ago = now - 3600

        # Total queries (events)
        cursor.execute('SELECT COUNT(*) FROM dns_queries')
        total_queries = _fetchone_value(cursor, 0)

        # Unique domains accessed
        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries')
        unique_domains = _fetchone_value(cursor, 0)

        # Threat totals
        cursor.execute('SELECT COUNT(*) FROM dns_queries WHERE is_risk = 1')
        total_threat_events = _fetchone_value(cursor, 0)

        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries WHERE is_risk = 1')
        unique_threat_domains = _fetchone_value(cursor, 0)

        # Recent activity (last hour)
        cursor.execute(f'SELECT COUNT(*) FROM dns_queries WHERE {ts_seconds_expr} > ?', (hour_ago,))
        recent_queries = _fetchone_value(cursor, 0)

        cursor.execute(f'SELECT COUNT(*) FROM dns_queries WHERE {ts_seconds_expr} > ? AND is_risk = 1', (hour_ago,))
        recent_threats = _fetchone_value(cursor, 0)

        # Severity breakdown (events)
        # low: 30-49, medium: 50-74, severe: >=75
        cursor.execute('''
            SELECT
                SUM(CASE WHEN is_risk = 1 AND COALESCE(risk_score, 0) BETWEEN 30 AND 49 THEN 1 ELSE 0 END) AS low_count,
                SUM(CASE WHEN is_risk = 1 AND COALESCE(risk_score, 0) BETWEEN 50 AND 74 THEN 1 ELSE 0 END) AS medium_count,
                SUM(CASE WHEN is_risk = 1 AND COALESCE(risk_score, 0) >= 75 THEN 1 ELSE 0 END) AS severe_count
            FROM dns_queries
        ''')
        sev_row = cursor.fetchone() or (0, 0, 0)
        severity_low = int(sev_row[0] or 0)
        severity_medium = int(sev_row[1] or 0)
        severity_severe = int(sev_row[2] or 0)
        
        # Get storage used
        db_size = os.path.getsize(DB_PATH) if os.path.exists(DB_PATH) else 0
        storage_used = f"{db_size / (1024 * 1024):.2f} MB"
        
        # Get last analysis (use analysis_timestamp when present; normalize ms to seconds)
        cursor.execute('''
            SELECT MAX(
                CASE
                    WHEN analysis_timestamp IS NOT NULL THEN
                        CASE WHEN analysis_timestamp > 1000000000000 THEN analysis_timestamp / 1000 ELSE analysis_timestamp END
                    ELSE
                        CASE WHEN timestamp > 1000000000000 THEN timestamp / 1000 ELSE timestamp END
                END
            )
            FROM dns_queries
        ''')
        last_analysis_ts = _fetchone_value(cursor, None)
        last_analysis = datetime.fromtimestamp(last_analysis_ts).strftime('%Y-%m-%d %H:%M') if last_analysis_ts else "Never"

        # Last threat analyzed
        cursor.execute(f'''
            SELECT
                domain,
                app_name,
                device_id,
                COALESCE(risk_score, 0) AS risk_score,
                risk_reasons,
                {ts_seconds_expr} AS ts_seconds
            FROM dns_queries
            WHERE is_risk = 1
            ORDER BY ts_seconds DESC
            LIMIT 1
        ''')
        lt = cursor.fetchone()
        last_threat = None
        if lt:
            last_threat = {
                'domain': lt[0],
                'app_name': lt[1],
                'device_id': lt[2],
                'risk_score': int(lt[3] or 0),
                'risk_reasons': lt[4],
                'timestamp': int(lt[5] or 0)
            }

        # Active devices (last hour)
        cursor.execute(f'''
            SELECT
                device_id,
                MAX({ts_seconds_expr}) AS last_seen,
                COUNT(*) AS total_queries,
                SUM(CASE WHEN is_risk = 1 THEN 1 ELSE 0 END) AS threat_events
            FROM dns_queries
            WHERE device_id IS NOT NULL AND device_id != '' AND {ts_seconds_expr} > ?
            GROUP BY device_id
            ORDER BY last_seen DESC
            LIMIT 20
        ''', (hour_ago,))
        device_rows = cursor.fetchall()
        active_devices = []
        for device_id, last_seen, dev_total, dev_threats in device_rows:
            active_devices.append({
                'device_id': device_id,
                'last_seen': int(last_seen or 0),
                'total_queries': int(dev_total or 0),
                'threat_events': int(dev_threats or 0)
            })

        active_device_count = len(active_devices)
        
        conn.close()
        
        return jsonify({
            'totalQueries': total_queries,
            'uniqueDomains': unique_domains,
            'totalThreats': total_threat_events,
            'uniqueThreatDomains': unique_threat_domains,
            # Backwards-compatible fields for older dashboards
            'totalDomains': unique_domains,
            'riskDomains': unique_threat_domains,
            'storageUsed': storage_used,
            'lastAnalysis': last_analysis,
            'lastThreat': last_threat,
            'activeDevices': active_devices,
            'activeDeviceCount': active_device_count,
            'chartData': {
                'labels': ['24h ago', '23h ago', '22h ago', '21h ago', '20h ago', '19h ago', '18h ago', '17h ago', '16h ago', '15h ago', '14h ago', '13h ago', '12h ago', '11h ago', '10h ago', '9h ago', '8h ago', '7h ago', '6h ago', '5h ago', '4h ago', '3h ago', '2h ago', '1h ago', 'Now'],
                'dnsQueries': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, recent_queries],
                'threats': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, recent_threats]
            },
            'severityData': {
                'low': severity_low,
                'medium': severity_medium,
                'severe': severity_severe
            }
        })
        
    except Exception as e:
        return jsonify({
            'error': f'Database error: {str(e)}',
            'totalQueries': 0,
            'uniqueDomains': 0,
            'totalThreats': 0,
            'uniqueThreatDomains': 0,
            'totalDomains': 0,
            'riskDomains': 0,
            'storageUsed': '0 MB',
            'lastAnalysis': 'Never',
            'lastThreat': None,
            'activeDevices': [],
            'activeDeviceCount': 0,
            'chartData': {
                'labels': [],
                'dnsQueries': [],
                'threats': []
            },
            'severityData': {
                'low': 0,
                'medium': 0,
                'severe': 0
            }
        }), 500


@app.route('/api/recent-sites', methods=['GET'])
def recent_sites():
    """Return most recent DNS queries (normalized timestamps)."""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            return jsonify({'error': 'Invalid API key'}), 401

        limit = request.args.get('limit', default=25, type=int)
        limit = max(1, min(limit, 200))

        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()

        ts_seconds_expr = "CASE WHEN timestamp > 1000000000000 THEN timestamp / 1000 ELSE timestamp END"
        cursor.execute(f'''
            SELECT
                domain,
                app_name,
                is_risk,
                risk_score,
                risk_reasons,
                {ts_seconds_expr} AS ts_seconds
            FROM dns_queries
            ORDER BY ts_seconds DESC
            LIMIT ?
        ''', (limit,))

        rows = cursor.fetchall()
        conn.close()

        items = []
        for domain, app_name, is_risk, risk_score, risk_reasons, ts_seconds in rows:
            items.append({
                'domain': domain,
                'app_name': app_name,
                'is_risk': bool(is_risk),
                'risk_score': int(risk_score or 0),
                'risk_reasons': risk_reasons,
                'timestamp': int(ts_seconds or 0)
            })

        return jsonify({'items': items})

    except Exception as e:
        return jsonify({'error': f'Database error: {str(e)}'}), 500


@app.route('/api/dangerous-sites', methods=['GET'])
def dangerous_sites():
    """Return aggregated risky domains."""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            return jsonify({'error': 'Invalid API key'}), 401

        limit = request.args.get('limit', default=25, type=int)
        limit = max(1, min(limit, 200))

        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()

        ts_seconds_expr = "CASE WHEN timestamp > 1000000000000 THEN timestamp / 1000 ELSE timestamp END"
        cursor.execute(f'''
            SELECT
                domain,
                MAX(COALESCE(risk_score, 0)) AS max_risk_score,
                COUNT(*) AS hits,
                MAX({ts_seconds_expr}) AS last_seen
            FROM dns_queries
            WHERE is_risk = 1
            GROUP BY domain
            ORDER BY max_risk_score DESC, hits DESC, last_seen DESC
            LIMIT ?
        ''', (limit,))

        rows = cursor.fetchall()
        conn.close()

        items = []
        for domain, max_risk_score, hits, last_seen in rows:
            items.append({
                'domain': domain,
                'max_risk_score': int(max_risk_score or 0),
                'hits': int(hits or 0),
                'last_seen': int(last_seen or 0)
            })

        return jsonify({'items': items})

    except Exception as e:
        return jsonify({'error': f'Database error: {str(e)}'}), 500

@app.route('/api/debug-data', methods=['GET'])
def debug_data():
    """Debug endpoint to see what's in the database"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            return jsonify({'error': 'Invalid API key'}), 401
        
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        # Get all recent entries
        cursor.execute('SELECT domain, is_risk, risk_score, risk_reasons, timestamp FROM dns_queries ORDER BY timestamp DESC LIMIT 10')
        entries = cursor.fetchall()
        
        # Get counts
        cursor.execute('SELECT COUNT(*) FROM dns_queries')
        total_count = _fetchone_value(cursor, 0)
        
        cursor.execute('SELECT COUNT(*) FROM dns_queries WHERE is_risk = 1')
        risk_count = _fetchone_value(cursor, 0)
        
        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries')
        domain_count = _fetchone_value(cursor, 0)
        
        conn.close()
        
        return jsonify({
            'total_entries': total_count,
            'risk_entries': risk_count,
            'unique_domains': domain_count,
            'recent_entries': [
                {
                    'domain': entry[0],
                    'is_risk': entry[1],
                    'risk_score': entry[2],
                    'risk_reasons': entry[3],
                    'timestamp': entry[4]
                } for entry in entries
            ]
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/test-db', methods=['GET'])
def test_database():
    """Test database connection and data"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key and api_key not in READONLY_API_KEYS:
            return jsonify({'success': False, 'error': 'Invalid API key'}), 401

        ensure_database()

        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        # Test basic query
        cursor.execute('SELECT COUNT(*) FROM dns_queries')
        count = _fetchone_value(cursor, 0)
        
        # Test recent data
        cursor.execute('SELECT domain, is_risk, risk_score FROM dns_queries ORDER BY timestamp DESC LIMIT 5')
        recent = cursor.fetchall()
        
        conn.close()
        
        return jsonify({
            'success': True,
            'total_entries': count,
            'recent_entries': [
                {'domain': row[0], 'is_risk': row[1], 'risk_score': row[2]} 
                for row in recent
            ]
        })
        
    except Exception as e:
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500

@app.route('/api/clear-data', methods=['DELETE'])
def clear_data():
    """Clear all server data"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key not in WRITE_API_KEYS:
            return jsonify({'error': 'Invalid API key'}), 401
        
        # Connect to database
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        # Create tables if they don't exist
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS dns_queries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp INTEGER,
                domain TEXT,
                app_name TEXT,
                source_ip TEXT,
                dest_ip TEXT,
                protocol TEXT,
                size_bytes INTEGER,
                is_risk INTEGER DEFAULT 0,
                risk_score INTEGER DEFAULT 0,
                risk_reasons TEXT,
                analysis_timestamp INTEGER,
                analysis_engine TEXT,
                device_id TEXT
            )
        ''')
        
        # Clear all data
        cursor.execute('DELETE FROM dns_queries')
        
        conn.commit()
        conn.close()
        
        return jsonify({'success': True, 'message': 'All data cleared successfully'})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/encrypted-dns-data', methods=['POST'])
def process_encrypted_dns_data():
    """Process encrypted DNS data with enhanced response"""
    try:
        print(f"[DEBUG] Received request to /api/encrypted-dns-data")
        print(f"[DEBUG] Headers: {dict(request.headers)}")
        
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            print(f"[DEBUG] Invalid API key: {api_key}")
            return jsonify({'error': 'Invalid API key'}), 401
        
        encrypted_payload = request.get_json()
        print(f"[DEBUG] Received payload: {encrypted_payload}")
        
        if not encrypted_payload:
            print(f"[DEBUG] No data provided")
            return jsonify({'error': 'No data provided'}), 400
        
        # Process the encrypted data
        print(f"[DEBUG] Processing encrypted payload...")
        result = processor.process_encrypted_payload(encrypted_payload)
        print(f"[DEBUG] Processing result: {result}")
        
        # Enhanced response format
        response = {
            'success': True,
            'message': 'Data processed successfully',
            'processed_entries': result.get('processed_count', 0),
            'device_id': encrypted_payload.get('device_id', 'unknown'),
            'timestamp': encrypted_payload.get('timestamp', 0)
        }
        
        # Add analysis results if available
        if 'analysis_results' in result:
            response['results'] = result['analysis_results']
        
        print(f"[DEBUG] Sending response: {response}")
        return jsonify(response)
        
    except Exception as e:
        print(f"[DEBUG] Error processing data: {str(e)}")
        import traceback
        traceback.print_exc()
        return jsonify({
            'success': False,
            'error': str(e),
            'message': 'Failed to process encrypted data'
        }), 500

@app.route('/api/test-connection', methods=['POST'])
def test_connection():
    """Simple test endpoint to bypass encryption"""
    try:
        print(f"[DEBUG] Test connection received")
        print(f"[DEBUG] Headers: {dict(request.headers)}")
        
        api_key = request.headers.get('X-API-Key')
        if api_key not in READONLY_API_KEYS:
            print(f"[DEBUG] Invalid API key: {api_key}")
            return jsonify({'error': 'Invalid API key'}), 401
        
        data = request.get_json()
        print(f"[DEBUG] Test data received: {data}")
        
        return jsonify({
            'success': True,
            'message': 'Connection test successful',
            'received_data': data,
            'timestamp': datetime.now().isoformat()
        })
        
    except Exception as e:
        print(f"[DEBUG] Test connection error: {str(e)}")
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
