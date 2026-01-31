#!/usr/bin/env python3
"""
DataSentry Linux Analysis Server
Receives DNS data from Android app and performs advanced security analysis
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import sqlite3
import threading
import time
import json
import logging
from datetime import datetime, timedelta
from analysis_engine import DNSAnalysisEngine
from threat_intelligence import ThreatIntel
from dashboard import Dashboard
from crypto_utils import ServerCryptoUtils, EncryptedDataProcessor

app = Flask(__name__)
CORS(app)

# Configure logging - use local directory to avoid permission issues
import os
log_dir = 'logs'
os.makedirs(log_dir, exist_ok=True)

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(f'{log_dir}/server.log'),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

# Initialize components
analysis_engine = DNSAnalysisEngine()
threat_intel = ThreatIntel()
dashboard = Dashboard()

# Initialize encryption components
crypto_utils = ServerCryptoUtils()
encrypted_processor = EncryptedDataProcessor(crypto_utils)

logger.info(f"Initialized encryption: {crypto_utils.get_key_info()}")

@app.route('/health')
def health_check():
    return jsonify({
        'status': 'healthy',
        'timestamp': datetime.now().isoformat(),
        'endpoints': {
            'dashboard': '/api/dashboard/data',
            'safety_assessment': '/api/safety-assessment',
            'dns_data': '/api/dns-data',
            'domain_analysis': '/api/analysis/domain/<domain>',
            'recent_threats': '/api/threats/recent'
        }
    })

@app.route('/')
def index():
    """Serve the main dashboard"""
    try:
        with open('dashboard.html', 'r') as f:
            return f.read()
    except FileNotFoundError:
        return jsonify({'error': 'Dashboard not found'}), 404

# Database setup
def init_db():
    conn = sqlite3.connect('datasentry.db')
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
            is_risk BOOLEAN,
            device_id TEXT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS analysis_results (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            domain TEXT,
            analysis_type TEXT,
            result TEXT,
            risk_score INTEGER,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    conn.commit()
    conn.close()

@app.route('/api/encrypted-dns-data', methods=['POST'])
def receive_encrypted_dns_data():
    """Receive encrypted DNS data from Android app"""
    try:
        data = request.get_json()
        
        # Validate API key
        api_key = data.get('api_key')
        if api_key != 'datasentry-secure-api-key-2024':
            return jsonify({'error': 'Invalid API key'}), 401
        
        # Validate required fields
        if not data.get('encrypted_data'):
            return jsonify({'error': 'Missing encrypted_data field'}), 400
        
        # Process encrypted payload
        result = encrypted_processor.process_encrypted_payload(data)
        
        if result['status'] == 'success':
            # Trigger analysis in background for processed entries
            threading.Thread(
                target=analyze_encrypted_batch,
                args=(result['processed_entries'], result['device_id']),
                daemon=True
            ).start()
            
            logger.info(f"Received and decrypted {len(result['processed_entries'])} DNS queries from device {result['device_id']}")
            
            return jsonify({
                'status': 'success',
                'processed': len(result['processed_entries']),
                'device_id': result['device_id'],
                'timestamp': result['timestamp'],
                'encryption_version': result['encryption_version']
            })
        else:
            return jsonify({
                'status': 'error',
                'message': result.get('message', 'Unknown error'),
                'processed': 0
            }), 500
        
    except Exception as e:
        logger.error(f"Error processing encrypted DNS data: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

@app.route('/api/dns-data', methods=['POST'])
def receive_dns_data():
    """Receive DNS data from Android app"""
    try:
        data = request.get_json()
        
        # Validate API key
        api_key = request.headers.get('X-API-Key')
        if api_key != 'datasentry-quick-api-key-12345':
            return jsonify({'error': 'Invalid API key'}), 401
        
        # Store DNS queries
        conn = sqlite3.connect('datasentry.db')
        cursor = conn.cursor()
        
        for query in data.get('queries', []):
            cursor.execute('''
                INSERT INTO dns_queries 
                (timestamp, domain, app_name, source_ip, dest_ip, protocol, size_bytes, is_risk, device_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            ''', (
                query['timestamp'],
                query['domain'],
                query['appName'],
                query['sourceIp'],
                query['destIp'],
                query['protocol'],
                query['sizeBytes'],
                query['isRisk'],
                data['deviceId']
            ))
        
        conn.commit()
        conn.close()
        
        # Trigger analysis in background
        threading.Thread(
            target=analyze_domain_batch,
            args=(data.get('queries', []),),
            daemon=True
        ).start()
        
        logger.info(f"Received {len(data.get('queries', []))} DNS queries from device {data.get('deviceId')}")
        
        return jsonify({'status': 'success', 'processed': len(data.get('queries', []))})
        
    except Exception as e:
        logger.error(f"Error processing DNS data: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

@app.route('/api/safety-assessment', methods=['GET'])
def get_safety_assessment():
    """Get overall safety assessment and recommendations"""
    try:
        data = dashboard.get_dashboard_data()
        overview = data.get('overview', {})
        recent_domains = data.get('recent_domains', [])
        app_breakdown = data.get('app_breakdown', [])
        
        # Calculate overall safety score
        total_queries = overview.get('total_queries', 0)
        high_risk_domains = overview.get('high_risk_domains', 0)
        privacy_score = overview.get('privacy_score', 100)
        
        # Determine safety status
        if high_risk_domains == 0:
            safety_status = "SAFE"
            safety_color = "green"
            safety_message = "Your network is secure. No threats detected."
        elif high_risk_domains <= 3:
            safety_status = "CAUTION"
            safety_color = "yellow"
            safety_message = "Minor risks detected. Monitor suspicious activity."
        else:
            safety_status = "DANGER"
            safety_color = "red"
            safety_message = "Multiple threats detected! Immediate action required."
        
        # Get critical threats
        critical_domains = [d for d in recent_domains if d.get('risk_level') == 'Critical']
        
        # Get risky apps
        risky_apps = [app for app in app_breakdown if app.get('has_risk', False)]
        
        # Generate recommendations
        recommendations = []
        if critical_domains:
            recommendations.append(f"🚨 Block {len(critical_domains)} critical domains immediately")
        if len(risky_apps) > 0:
            recommendations.append(f"📱 Monitor {len(risky_apps)} suspicious applications")
        if privacy_score < 70:
            recommendations.append("🔒 Review privacy settings and permissions")
        if total_queries > 1000:
            recommendations.append("📊 Consider implementing query rate limiting")
        
        if not recommendations:
            recommendations.append("✅ Continue monitoring network activity")
        
        assessment = {
            'safety_status': safety_status,
            'safety_color': safety_color,
            'safety_message': safety_message,
            'privacy_score': privacy_score,
            'total_threats': high_risk_domains,
            'critical_domains': critical_domains[:5],  # Top 5 critical
            'risky_apps': risky_apps[:5],  # Top 5 risky apps
            'recommendations': recommendations,
            'last_updated': datetime.now().isoformat()
        }
        
        return jsonify(assessment)
        
    except Exception as e:
        logger.error(f"Error generating safety assessment: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

@app.route('/api/dashboard/data', methods=['GET'])
def get_dashboard_data():
    """Get dashboard analytics data"""
    try:
        data = dashboard.get_dashboard_data()
        return jsonify(data)
    except Exception as e:
        logger.error(f"Error getting dashboard data: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

@app.route('/api/analysis/domain/<domain>', methods=['GET'])
def get_domain_analysis(domain):
    """Get detailed analysis for a specific domain"""
    try:
        analysis = analysis_engine.get_domain_analysis(domain)
        return jsonify(analysis)
    except Exception as e:
        logger.error(f"Error getting domain analysis: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

@app.route('/api/threats/recent', methods=['GET'])
def get_recent_threats():
    """Get recent threat detections"""
    try:
        hours = request.args.get('hours', 24, type=int)
        threats = threat_intel.get_recent_threats(hours)
        return jsonify(threats)
    except Exception as e:
        logger.error(f"Error getting recent threats: {str(e)}")
        return jsonify({'error': 'Internal server error'}), 500

def analyze_domain_batch(queries):
    """Background analysis of DNS queries"""
    logger.info(f"Starting batch analysis for {len(queries)} queries")
    for query in queries:
        domain = query.get('domain')
        logger.info(f"Analyzing domain: {domain}")
        if domain and domain != 'unknown.domain':
            try:
                # Perform comprehensive analysis
                analysis_result = analysis_engine.analyze_domain(domain)
                logger.info(f"Analysis completed for {domain}: {type(analysis_result)}")
                
                # Store results in database
                conn = sqlite3.connect('datasentry.db')
                cursor = conn.cursor()
                
                for analysis_type, result in analysis_result.items():
                    # Handle both dict and int types for risk score
                    if isinstance(result, dict):
                        risk_score = result.get('risk_score', 0)
                    elif isinstance(result, int):
                        risk_score = result
                    else:
                        risk_score = 0
                    
                    cursor.execute('''
                        INSERT INTO analysis_results 
                        (domain, analysis_type, result, risk_score)
                        VALUES (?, ?, ?, ?)
                    ''', (domain, analysis_type, json.dumps(result), risk_score))
                
                conn.commit()
                conn.close()
                
                logger.info(f"Completed analysis for domain: {domain}")
                
            except Exception as e:
                logger.error(f"Error analyzing domain {domain}: {str(e)}")
                import traceback
                logger.error(f"Full traceback: {traceback.format_exc()}")
        else:
            logger.warning(f"Skipping analysis for domain: {domain}")
    logger.info("Batch analysis completed")

def analyze_encrypted_batch(processed_entries, device_id):
    """Background analysis of decrypted DNS queries from encrypted payload"""
    logger.info(f"Starting encrypted batch analysis for {len(processed_entries)} entries from device {device_id}")
    
    # Handle empty entries list
    if not processed_entries:
        logger.info("No entries to analyze")
        return
    
    # Extract unique domains for analysis
    domains_to_analyze = set()
    for entry in processed_entries:
        dest_ip = entry.get('dest_ip', '')
        if dest_ip and dest_ip != 'unknown.domain' and not dest_ip.startswith('192.168.'):
            domains_to_analyze.add(dest_ip)
    
    logger.info(f"Found {len(domains_to_analyze)} unique domains to analyze")
    
    for domain in domains_to_analyze:
        logger.info(f"Analyzing encrypted domain: {domain}")
        try:
            # Perform comprehensive analysis
            analysis_result = analysis_engine.analyze_domain(domain)
            logger.info(f"Analysis completed for {domain}: {type(analysis_result)}")
            
            # Store results in database
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            for analysis_type, result in analysis_result.items():
                # Handle both dict and int types for risk score
                if isinstance(result, dict):
                    risk_score = result.get('risk_score', 0)
                elif isinstance(result, int):
                    risk_score = result
                else:
                    risk_score = 0
                
                cursor.execute('''
                    INSERT INTO analysis_results 
                    (domain, analysis_type, result, risk_score)
                    VALUES (?, ?, ?, ?)
                ''', (domain, analysis_type, str(result), risk_score))
            
            conn.commit()
            conn.close()
            
        except Exception as e:
            logger.error(f"Error analyzing encrypted domain {domain}: {str(e)}")
    
    logger.info("Encrypted batch analysis completed")

if __name__ == '__main__':
    init_db()
    
    # Start background analysis threads
    threading.Thread(target=analysis_engine.start_monitoring, daemon=True).start()
    threading.Thread(target=threat_intel.start_monitoring, daemon=True).start()
    
    logger.info("DataSentry Linux Analysis Server starting on port 8081")
    
    # Use HTTP for easier testing (no SSL issues)
    app.run(host='0.0.0.0', port=8081, debug=False)
