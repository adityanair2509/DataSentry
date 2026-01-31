#!/usr/bin/env python3
"""
Test script to verify dashboard data loading
"""

import sqlite3
import json
import time

def test_database():
    """Test database connection and data"""
    try:
        conn = sqlite3.connect('dns_analysis.db')
        cursor = conn.cursor()
        
        # Test basic query
        cursor.execute('SELECT COUNT(*) FROM dns_queries')
        count = cursor.fetchone()[0]
        print(f"Total entries: {count}")
        
        # Test risk domains
        cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries WHERE is_risk = 1')
        risk_count = cursor.fetchone()[0] or 0
        print(f"Risk domains: {risk_count}")
        
        # Test recent data
        cursor.execute('SELECT domain, is_risk, risk_score FROM dns_queries ORDER BY timestamp DESC LIMIT 5')
        recent = cursor.fetchall()
        print("\nRecent entries:")
        for row in recent:
            print(f"  {row[0]} - Risk: {row[1]} - Score: {row[2]}")
        
        # Test stats calculation
        now = int(time.time())
        hour_ago = now - 3600
        cursor.execute('SELECT COUNT(*) FROM dns_queries WHERE timestamp > ?', (hour_ago,))
        recent_queries = cursor.fetchone()[0] or 0
        
        cursor.execute('SELECT COUNT(*) FROM dns_queries WHERE timestamp > ? AND is_risk = 1', (hour_ago,))
        recent_threats = cursor.fetchone()[0] or 0
        
        print(f"\nRecent activity (last hour):")
        print(f"  Queries: {recent_queries}")
        print(f"  Threats: {recent_threats}")
        
        conn.close()
        
        # Simulate stats API response
        stats = {
            'totalDomains': count,
            'riskDomains': risk_count,
            'storageUsed': '0.01 MB',
            'lastAnalysis': 'Just now',
            'chartData': {
                'labels': ['24h ago', '23h ago', '22h ago', '21h ago', '20h ago', '19h ago', '18h ago', '17h ago', '16h ago', '15h ago', '14h ago', '13h ago', '12h ago', '11h ago', '10h ago', '9h ago', '8h ago', '7h ago', '6h ago', '5h ago', '4h ago', '3h ago', '2h ago', '1h ago', 'Now'],
                'dnsQueries': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, recent_queries],
                'threats': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, recent_threats]
            }
        }
        
        print(f"\nDashboard stats response:")
        print(json.dumps(stats, indent=2))
        
        return True
        
    except Exception as e:
        print(f"Database test failed: {e}")
        return False

if __name__ == "__main__":
    print("Testing DataSentry Dashboard Data...")
    print("=" * 50)
    
    if test_database():
        print("\n✅ Database test PASSED")
        print("✅ Dashboard data is ready")
        print("✅ Start the server with: python app.py")
    else:
        print("\n❌ Database test FAILED")
        print("❌ Check database file and permissions")
