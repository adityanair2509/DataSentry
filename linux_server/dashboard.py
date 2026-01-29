#!/usr/bin/env python3
"""
Web Dashboard for DataSentry Analysis
Real-time visualization of DNS analysis and threat intelligence
"""

import sqlite3
import json
from datetime import datetime, timedelta
import logging

logger = logging.getLogger(__name__)

class Dashboard:
    def __init__(self):
        pass
    
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
            logger.error(f"Error getting dashboard data: {str(e)}")
            return {'error': str(e)}
    
    def _get_overview_stats(self):
        """Get overview statistics"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Total DNS queries
            cursor.execute('SELECT COUNT(*) FROM dns_queries')
            total_queries = cursor.fetchone()[0]
            
            # Unique domains
            cursor.execute('SELECT COUNT(DISTINCT domain) FROM dns_queries')
            unique_domains = cursor.fetchone()[0]
            
            # High-risk domains
            cursor.execute('''
                SELECT COUNT(DISTINCT domain) FROM analysis_results 
                WHERE risk_score > 70 AND created_at > datetime('now', '-24 hours')
            ''')
            high_risk_domains = cursor.fetchone()[0]
            
            # Recent threats
            cursor.execute('''
                SELECT COUNT(*) FROM threat_detections 
                WHERE detected_at > datetime('now', '-24 hours')
            ''')
            recent_threats = cursor.fetchone()[0]
            
            # Active devices
            cursor.execute('''
                SELECT COUNT(DISTINCT device_id) FROM dns_queries 
                WHERE created_at > datetime('now', '-1 hour')
            ''')
            active_devices = cursor.fetchone()[0]
            
            conn.close()
            
            return {
                'total_queries': total_queries,
                'unique_domains': unique_domains,
                'high_risk_domains': high_risk_domains,
                'recent_threats': recent_threats,
                'active_devices': active_devices,
                'privacy_score': max(0, 100 - (high_risk_domains * 5))  # Simple calculation
            }
            
        except Exception as e:
            logger.error(f"Error getting overview stats: {str(e)}")
            return {}
    
    def _get_recent_domains(self):
        """Get recently analyzed domains"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            cursor.execute('''
                SELECT DISTINCT d.domain, 
                       MAX(d.created_at) as last_seen,
                       COUNT(d.id) as query_count,
                       MAX(a.risk_score) as max_risk_score
                FROM dns_queries d
                LEFT JOIN analysis_results a ON d.domain = a.domain
                WHERE d.created_at > datetime('now', '-6 hours')
                GROUP BY d.domain
                ORDER BY last_seen DESC
                LIMIT 20
            ''')
            
            domains = []
            for row in cursor.fetchall():
                domains.append({
                    'domain': row[0],
                    'last_seen': row[1],
                    'query_count': row[2],
                    'risk_score': row[3] or 0,
                    'risk_level': self._get_risk_level(row[3] or 0)
                })
            
            conn.close()
            return domains
            
        except Exception as e:
            logger.error(f"Error getting recent domains: {str(e)}")
            return []
    
    def _get_top_threats(self):
        """Get top threat detections"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            cursor.execute('''
                SELECT domain, threat_type, risk_score, details, detected_at
                FROM threat_detections 
                WHERE detected_at > datetime('now', '-24 hours')
                ORDER BY risk_score DESC, detected_at DESC
                LIMIT 10
            ''')
            
            threats = []
            for row in cursor.fetchall():
                details = json.loads(row[3]) if row[3] else {}
                threats.append({
                    'domain': row[0],
                    'threat_type': row[1],
                    'risk_score': row[2],
                    'details': details,
                    'detected_at': row[4],
                    'severity': self._get_severity_level(row[2])
                })
            
            conn.close()
            return threats
            
        except Exception as e:
            logger.error(f"Error getting top threats: {str(e)}")
            return []
    
    def _get_traffic_patterns(self):
        """Get traffic pattern analysis"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Queries per hour for last 24 hours
            cursor.execute('''
                SELECT 
                    strftime('%H', datetime(created_at/1000, 'unixepoch')) as hour,
                    COUNT(*) as query_count,
                    COUNT(DISTINCT domain) as unique_domains
                FROM dns_queries 
                WHERE created_at > datetime('now', '-24 hours')
                GROUP BY hour
                ORDER BY hour
            ''')
            
            hourly_data = []
            for row in cursor.fetchall():
                hourly_data.append({
                    'hour': int(row[0]),
                    'query_count': row[1],
                    'unique_domains': row[2]
                })
            
            # Top apps by query count
            cursor.execute('''
                SELECT app_name, COUNT(*) as query_count
                FROM dns_queries 
                WHERE created_at > datetime('now', '-24 hours')
                GROUP BY app_name
                ORDER BY query_count DESC
                LIMIT 10
            ''')
            
            app_data = []
            for row in cursor.fetchall():
                app_data.append({
                    'app_name': row[0],
                    'query_count': row[1]
                })
            
            conn.close()
            
            return {
                'hourly_patterns': hourly_data,
                'top_apps': app_data
            }
            
        except Exception as e:
            logger.error(f"Error getting traffic patterns: {str(e)}")
            return {}
    
    def _get_risk_distribution(self):
        """Get risk score distribution"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            cursor.execute('''
                SELECT 
                    CASE 
                        WHEN risk_score <= 20 THEN 'Low'
                        WHEN risk_score <= 50 THEN 'Medium'
                        WHEN risk_score <= 70 THEN 'High'
                        ELSE 'Critical'
                    END as risk_level,
                    COUNT(*) as count
                FROM analysis_results 
                WHERE created_at > datetime('now', '-24 hours')
                GROUP BY risk_level
            ''')
            
            distribution = {}
            for row in cursor.fetchall():
                distribution[row[0]] = row[1]
            
            # Ensure all risk levels are present
            for level in ['Low', 'Medium', 'High', 'Critical']:
                if level not in distribution:
                    distribution[level] = 0
            
            conn.close()
            return distribution
            
        except Exception as e:
            logger.error(f"Error getting risk distribution: {str(e)}")
            return {}
    
    def _get_timeline_data(self):
        """Get timeline data for visualization"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Last 6 hours of data
            cursor.execute('''
                SELECT 
                    datetime(created_at/1000, 'unixepoch') as time_bucket,
                    COUNT(*) as query_count,
                    COUNT(DISTINCT domain) as unique_domains,
                    COUNT(DISTINCT app_name) as unique_apps
                FROM dns_queries 
                WHERE created_at > datetime('now', '-6 hours')
                GROUP BY strftime('%Y-%m-%d %H:', datetime(created_at/1000, 'unixepoch'))
                ORDER BY time_bucket
            ''')
            
            timeline = []
            for row in cursor.fetchall():
                timeline.append({
                    'time': row[0],
                    'queries': row[1],
                    'domains': row[2],
                    'apps': row[3]
                })
            
            conn.close()
            return timeline
            
        except Exception as e:
            logger.error(f"Error getting timeline data: {str(e)}")
            return []
    
    def _get_geographic_data(self):
        """Get geographic distribution of destinations"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Group by first octet for rough geographic grouping
            cursor.execute('''
                SELECT 
                    SUBSTR(dest_ip, 1, INSTR(dest_ip, '.') - 1) as first_octet,
                    COUNT(*) as query_count,
                    COUNT(DISTINCT domain) as unique_domains
                FROM dns_queries 
                WHERE created_at > datetime('now', '-24 hours')
                GROUP BY first_octet
                ORDER BY query_count DESC
                LIMIT 10
            ''')
            
            geo_data = []
            for row in cursor.fetchall():
                # Map first octet to rough geographic regions
                region = self._map_octet_to_region(int(row[0]))
                geo_data.append({
                    'region': region,
                    'ip_range': f"{row[0]}.x.x.x",
                    'query_count': row[1],
                    'unique_domains': row[2]
                })
            
            conn.close()
            return geo_data
            
        except Exception as e:
            logger.error(f"Error getting geographic data: {str(e)}")
            return []
    
    def _get_app_breakdown(self):
        """Get detailed app analysis"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            cursor.execute('''
                SELECT 
                    app_name,
                    COUNT(*) as total_queries,
                    COUNT(DISTINCT domain) as unique_domains,
                    COUNT(DISTINCT dest_ip) as unique_ips,
                    AVG(size_bytes) as avg_query_size,
                    MAX(CASE WHEN is_risk = 1 THEN 1 ELSE 0 END) as has_risk
                FROM dns_queries 
                WHERE created_at > datetime('now', '-24 hours')
                GROUP BY app_name
                ORDER BY total_queries DESC
            ''')
            
            apps = []
            for row in cursor.fetchall():
                apps.append({
                    'app_name': row[0],
                    'total_queries': row[1],
                    'unique_domains': row[2],
                    'unique_ips': row[3],
                    'avg_query_size': round(row[4], 2) if row[4] else 0,
                    'has_risk': bool(row[5]),
                    'risk_level': 'High' if row[5] else 'Normal'
                })
            
            conn.close()
            return apps
            
        except Exception as e:
            logger.error(f"Error getting app breakdown: {str(e)}")
            return []
    
    def _get_risk_level(self, score):
        """Convert risk score to risk level"""
        if score <= 20:
            return 'Low'
        elif score <= 50:
            return 'Medium'
        elif score <= 70:
            return 'High'
        else:
            return 'Critical'
    
    def _get_severity_level(self, score):
        """Convert risk score to severity level"""
        if score <= 30:
            return 'Low'
        elif score <= 60:
            return 'Medium'
        elif score <= 80:
            return 'High'
        else:
            return 'Critical'
    
    def _map_octet_to_region(self, first_octet):
        """Map IP first octet to rough geographic region"""
        # This is a simplified mapping - in reality, you'd use GeoIP databases
        region_map = {
            1: 'US East',
            8: 'Google (US)',
            52: 'AWS US East',
            54: 'AWS US East',
            64: 'US West',
            74: 'Google (US)',
            91: 'Europe',
            104: 'Cloudflare (Global)',
            142: 'Google (Global)',
            172: 'Google (US)',
            192: 'Private/Local',
            203: 'Asia Pacific'
        }
        return region_map.get(first_octet, 'Unknown')
    
    def get_domain_details(self, domain):
        """Get detailed analysis for a specific domain"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Basic domain info
            cursor.execute('''
                SELECT 
                    COUNT(*) as query_count,
                    MIN(created_at) as first_seen,
                    MAX(created_at) as last_seen,
                    COUNT(DISTINCT app_name) as apps,
                    COUNT(DISTINCT source_ip) as sources,
                    COUNT(DISTINCT dest_ip) as destinations
                FROM dns_queries 
                WHERE domain = ?
            ''', (domain,))
            
            basic_info = cursor.fetchone()
            
            # Analysis results
            cursor.execute('''
                SELECT analysis_type, result, risk_score, created_at
                FROM analysis_results 
                WHERE domain = ?
                ORDER BY created_at DESC
            ''', (domain,))
            
            analyses = []
            for row in cursor.fetchall():
                analyses.append({
                    'type': row[0],
                    'result': json.loads(row[1]) if row[1] else {},
                    'risk_score': row[2],
                    'created_at': row[3]
                })
            
            # Threat detections
            cursor.execute('''
                SELECT threat_type, risk_score, details, detected_at
                FROM threat_detections 
                WHERE domain = ?
                ORDER BY detected_at DESC
            ''', (domain,))
            
            threats = []
            for row in cursor.fetchall():
                threats.append({
                    'threat_type': row[0],
                    'risk_score': row[1],
                    'details': json.loads(row[2]) if row[2] else {},
                    'detected_at': row[3]
                })
            
            # Recent queries
            cursor.execute('''
                SELECT timestamp, app_name, source_ip, dest_ip, size_bytes, is_risk
                FROM dns_queries 
                WHERE domain = ?
                ORDER BY timestamp DESC
                LIMIT 50
            ''', (domain,))
            
            recent_queries = []
            for row in cursor.fetchall():
                recent_queries.append({
                    'timestamp': row[0],
                    'app_name': row[1],
                    'source_ip': row[2],
                    'dest_ip': row[3],
                    'size_bytes': row[4],
                    'is_risk': bool(row[5])
                })
            
            conn.close()
            
            return {
                'domain': domain,
                'basic_info': {
                    'query_count': basic_info[0],
                    'first_seen': basic_info[1],
                    'last_seen': basic_info[2],
                    'unique_apps': basic_info[3],
                    'unique_sources': basic_info[4],
                    'unique_destinations': basic_info[5]
                },
                'analyses': analyses,
                'threats': threats,
                'recent_queries': recent_queries
            }
            
        except Exception as e:
            logger.error(f"Error getting domain details: {str(e)}")
            return {'error': str(e)}
