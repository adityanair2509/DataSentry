#!/usr/bin/env python3
"""
Simple Dashboard for DataSentry Analysis
Minimal implementation that works without dependencies
"""

import sqlite3
from datetime import datetime, timedelta

class SimpleDashboard:
    def __init__(self):
        self.db_path = 'datasentry.db'
    
    def get_dashboard_data(self):
        """Get comprehensive dashboard data"""
        try:
            data = {
                'overview': self._get_overview_stats(),
                'recent_domains': self._get_recent_domains(),
                'top_threats': self._get_top_threats(),
                'traffic_patterns': self._get_traffic_patterns(),
                'risk_distribution': self._get_risk_distribution(),
                'timeline_data': self._get_timeline_data(),
                'geographic_data': self._get_geographic_data(),
                'app_breakdown': self._get_app_breakdown()
            }
            return data
        except Exception as e:
            print(f"Dashboard error: {e}")
            return self._get_empty_dashboard()
    
    def _get_empty_dashboard(self):
        """Return empty dashboard structure"""
        return {
            'overview': {
                'total_queries': 0,
                'unique_domains': 0,
                'high_risk_domains': 0,
                'privacy_score': 100,
                'active_devices': 0,
                'recent_threats': 0
            },
            'recent_domains': [],
            'top_threats': [],
            'traffic_patterns': {
                'hourly_patterns': [],
                'top_apps': []
            },
            'risk_distribution': {
                'Low': 0,
                'Medium': 0,
                'High': 0,
                'Critical': 0
            },
            'timeline_data': [],
            'geographic_data': [],
            'app_breakdown': []
        }
    
    def _get_overview_stats(self):
        """Get overview statistics"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            # Total queries
            cursor.execute("SELECT COUNT(*) FROM dns_queries")
            total_queries = cursor.fetchone()[0]
            
            # Unique domains
            cursor.execute("SELECT COUNT(DISTINCT domain) FROM dns_queries")
            unique_domains = cursor.fetchone()[0]
            
            # High risk domains (placeholder)
            high_risk_domains = 0
            
            # Active devices
            cursor.execute("SELECT COUNT(DISTINCT device_id) FROM dns_queries")
            active_devices = cursor.fetchone()[0]
            
            # Recent threats (placeholder)
            recent_threats = 0
            
            conn.close()
            
            return {
                'total_queries': total_queries,
                'unique_domains': unique_domains,
                'high_risk_domains': high_risk_domains,
                'privacy_score': max(0, 100 - (high_risk_domains * 10)),
                'active_devices': active_devices,
                'recent_threats': recent_threats
            }
        except Exception as e:
            print(f"Overview stats error: {e}")
            return {
                'total_queries': 0,
                'unique_domains': 0,
                'high_risk_domains': 0,
                'privacy_score': 100,
                'active_devices': 0,
                'recent_threats': 0
            }
    
    def _get_recent_domains(self):
        """Get recent domains"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            cursor.execute("""
                SELECT domain, app_name, datetime(timestamp, 'unixepoch'), is_risk
                FROM dns_queries 
                ORDER BY timestamp DESC 
                LIMIT 10
            """)
            
            recent_domains = []
            for row in cursor.fetchall():
                recent_domains.append({
                    'domain': row[0],
                    'app_name': row[1],
                    'timestamp': row[2],
                    'is_risk': bool(row[3])
                })
            
            conn.close()
            return recent_domains
        except Exception as e:
            print(f"Recent domains error: {e}")
            return []
    
    def _get_top_threats(self):
        """Get top threats (placeholder)"""
        return []
    
    def _get_traffic_patterns(self):
        """Get traffic patterns"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            # Top apps
            cursor.execute("""
                SELECT app_name, COUNT(*) as count
                FROM dns_queries 
                GROUP BY app_name 
                ORDER BY count DESC 
                LIMIT 5
            """)
            
            top_apps = []
            for row in cursor.fetchall():
                top_apps.append({
                    'app_name': row[0],
                    'count': row[1]
                })
            
            conn.close()
            
            return {
                'hourly_patterns': [],
                'top_apps': top_apps
            }
        except Exception as e:
            print(f"Traffic patterns error: {e}")
            return {
                'hourly_patterns': [],
                'top_apps': []
            }
    
    def _get_risk_distribution(self):
        """Get risk distribution"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            cursor.execute("""
                SELECT 
                    CASE 
                        WHEN is_risk = 1 THEN 'High'
                        ELSE 'Low'
                    END as risk_level,
                    COUNT(*) as count
                FROM dns_queries 
                GROUP BY is_risk
            """)
            
            risk_dist = {'Low': 0, 'Medium': 0, 'High': 0, 'Critical': 0}
            for row in cursor.fetchall():
                risk_dist[row[0]] = row[1]
            
            conn.close()
            return risk_dist
        except Exception as e:
            print(f"Risk distribution error: {e}")
            return {'Low': 0, 'Medium': 0, 'High': 0, 'Critical': 0}
    
    def _get_timeline_data(self):
        """Get timeline data"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            cursor.execute("""
                SELECT 
                    datetime(timestamp, 'unixepoch', 'localtime') as hour,
                    COUNT(*) as count
                FROM dns_queries 
                WHERE timestamp > strftime('%s', 'now', '-24 hours')
                GROUP BY strftime('%Y-%m-%d %H', datetime(timestamp, 'unixepoch', 'localtime'))
                ORDER BY hour
            """)
            
            timeline = []
            for row in cursor.fetchall():
                timeline.append({
                    'time': row[0],
                    'count': row[1]
                })
            
            conn.close()
            return timeline
        except Exception as e:
            print(f"Timeline data error: {e}")
            return []
    
    def _get_geographic_data(self):
        """Get geographic data (placeholder)"""
        return []
    
    def _get_app_breakdown(self):
        """Get app breakdown"""
        return self._get_traffic_patterns()['top_apps']
