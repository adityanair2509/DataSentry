from flask import Flask, request, jsonify
from crypto_utils import EncryptedDataProcessor
import sqlite3
import os
from datetime import datetime

app = Flask(__name__)

# Initialize the encrypted data processor
processor = EncryptedDataProcessor()

@app.route('/api/stats', methods=['GET'])
def get_stats():
    """Get server statistics"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key != 'datasentry-secure-api-key-2024':
            return jsonify({'error': 'Invalid API key'}), 401
        
        # Connect to database
        conn = sqlite3.connect('dns_analysis.db')
        cursor = conn.cursor()
        
        # Get total domains
        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries')
        total_domains = cursor.fetchone()[0]
        
        # Get risk domains
        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries WHERE is_risk = 1')
        risk_domains = cursor.fetchone()[0]
        
        # Get storage used
        db_size = os.path.getsize('dns_analysis.db') if os.path.exists('dns_analysis.db') else 0
        storage_used = f"{db_size / (1024 * 1024):.2f} MB"
        
        # Get last analysis
        cursor.execute('SELECT MAX(timestamp) FROM dns_queries')
        last_analysis_timestamp = cursor.fetchone()[0]
        last_analysis = datetime.fromtimestamp(last_analysis_timestamp).strftime('%Y-%m-%d %H:%M') if last_analysis_timestamp else "Never"
        
        conn.close()
        
        return jsonify({
            'totalDomains': total_domains,
            'riskDomains': risk_domains,
            'storageUsed': storage_used,
            'lastAnalysis': last_analysis
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/clear-data', methods=['DELETE'])
def clear_data():
    """Clear all server data"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key != 'datasentry-secure-api-key-2024':
            return jsonify({'error': 'Invalid API key'}), 401
        
        # Connect to database
        conn = sqlite3.connect('dns_analysis.db')
        cursor = conn.cursor()
        
        # Clear all data
        cursor.execute('DELETE FROM dns_queries')
        cursor.execute('DELETE FROM analysis_results')
        
        conn.commit()
        conn.close()
        
        return jsonify({'success': True, 'message': 'All data cleared successfully'})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/encrypted-dns-data', methods=['POST'])
def process_encrypted_dns_data():
    """Process encrypted DNS data with enhanced response"""
    try:
        api_key = request.headers.get('X-API-Key')
        if api_key != 'datasentry-secure-api-key-2024':
            return jsonify({'error': 'Invalid API key'}), 401
        
        encrypted_payload = request.get_json()
        if not encrypted_payload:
            return jsonify({'error': 'No data provided'}), 400
        
        # Process the encrypted data
        result = processor.process_encrypted_payload(encrypted_payload)
        
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
        
        return jsonify(response)
        
    except Exception as e:
        return jsonify({
            'success': False,
            'error': str(e),
            'message': 'Failed to process encrypted data'
        }), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
