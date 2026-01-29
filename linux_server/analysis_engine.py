#!/usr/bin/env python3
"""
Advanced DNS Analysis Engine
Uses various Linux tools for comprehensive domain analysis
"""

import subprocess
import re
import json
import sqlite3
import threading
import time
import logging
from datetime import datetime, timedelta
from collections import defaultdict, Counter
import socket
import whois
import dns.resolver
from scapy.all import *

# Set debug level for detailed logging
logging.getLogger(__name__).setLevel(logging.DEBUG)
logger = logging.getLogger(__name__)

class DNSAnalysisEngine:
    def __init__(self):
        self.domain_stats = defaultdict(lambda: {
            'query_count': 0,
            'first_seen': None,
            'last_seen': None,
            'unique_ips': set(),
            'query_patterns': [],
            'burst_events': []
        })
        self.monitoring = False
        
    def start_monitoring(self):
        """Start continuous network monitoring"""
        self.monitoring = True
        threading.Thread(target=self._monitor_network, daemon=True).start()
        threading.Thread(target=self._analyze_patterns, daemon=True).start()
        
    def analyze_domain(self, domain):
        """Comprehensive domain analysis using multiple tools"""
        results = {}
        
        try:
            # 1. WHOIS Analysis
            whois_result = self._analyze_whois(domain)
            results['whois'] = whois_result
            if not isinstance(whois_result, dict):
                logger.warning(f"WHOIS analysis returned non-dict: {type(whois_result)}")
            
            # 2. DNS Analysis with dig
            dns_result = self._analyze_dns(domain)
            results['dns'] = dns_result
            if not isinstance(dns_result, dict):
                logger.warning(f"DNS analysis returned non-dict: {type(dns_result)}")
            
            # 3. Network Analysis with tcpdump/tshark
            network_result = self._analyze_network_traffic(domain)
            results['network'] = network_result
            if not isinstance(network_result, dict):
                logger.warning(f"Network analysis returned non-dict: {type(network_result)}")
            
            # 4. Connection Pattern Analysis
            patterns_result = self._analyze_connection_patterns(domain)
            results['patterns'] = patterns_result
            if not isinstance(patterns_result, dict):
                logger.warning(f"Patterns analysis returned non-dict: {type(patterns_result)}")
            
            # 5. Fast-Flux Detection
            fastflux_result = self._detect_fast_flux(domain)
            results['fastflux'] = fastflux_result
            if not isinstance(fastflux_result, dict):
                logger.warning(f"Fast-flux detection returned non-dict: {type(fastflux_result)}")
            
            # 6. Beaconing Detection
            beaconing_result = self._detect_beaconing(domain)
            results['beaconing'] = beaconing_result
            if not isinstance(beaconing_result, dict):
                logger.warning(f"Beaconing detection returned non-dict: {type(beaconing_result)}")
            
            # 7. Burst Behavior Analysis
            burst_result = self._analyze_burst_behavior(domain)
            results['burst'] = burst_result
            if not isinstance(burst_result, dict):
                logger.warning(f"Burst analysis returned non-dict: {type(burst_result)}")
            
            # 8. Domain Reputation
            reputation_result = self._analyze_reputation(domain)
            results['reputation'] = reputation_result
            if not isinstance(reputation_result, dict):
                logger.warning(f"Reputation analysis returned non-dict: {type(reputation_result)}")
            
            # Calculate overall risk score
            results['risk_score'] = self._calculate_risk_score(results)
            
        except Exception as e:
            logger.error(f"Error analyzing domain {domain}: {str(e)}")
            results['error'] = str(e)
            
        return results
    
    def _analyze_whois(self, domain):
        """WHOIS analysis for domain intelligence"""
        try:
            # Try to use whois module
            try:
                w = whois.whois(domain)
            except Exception as whois_error:
                logger.warning(f"WHOIS module failed for {domain}: {whois_error}")
                # Fallback to command line whois
                try:
                    result = subprocess.run(['whois', domain], capture_output=True, text=True, timeout=10)
                    whois_data = result.stdout
                    
                    # Parse basic info from command line output
                    creation_date = None
                    registrar = 'unknown'
                    
                    # Simple parsing for creation date
                    if 'Creation Date:' in whois_data:
                        for line in whois_data.split('\n'):
                            if 'Creation Date:' in line:
                                date_str = line.split('Creation Date:')[-1].strip()
                                try:
                                    creation_date = datetime.strptime(date_str.split()[0], '%Y-%m-%d')
                                except:
                                    pass
                    
                    # Simple parsing for registrar
                    if 'Registrar:' in whois_data:
                        for line in whois_data.split('\n'):
                            if 'Registrar:' in line:
                                registrar = line.split('Registrar:')[-1].strip()
                                break
                    
                    domain_age = None
                    if creation_date:
                        domain_age = (datetime.now() - creation_date).days
                    
                    suspicious_registrars = [
                        'namecheap', 'godaddy', 'register.com', 'network solutions'
                    ]
                    is_suspicious_registrar = any(sus in registrar.lower() for sus in suspicious_registrars)
                    
                    return {
                        'domain_age_days': domain_age,
                        'registrar': registrar,
                        'creation_date': str(creation_date) if creation_date else None,
                        'expiration_date': None,
                        'name_servers': [],
                        'is_suspicious_registrar': is_suspicious_registrar,
                        'risk_score': self._calculate_whois_risk(domain_age, is_suspicious_registrar)
                    }
                    
                except Exception as cmd_error:
                    logger.warning(f"Command line WHOIS failed for {domain}: {cmd_error}")
                    return {'error': f'WHOIS unavailable: {str(cmd_error)}', 'risk_score': 50}
            
            # Original whois module logic (if it works)
            creation_date = w.creation_date
            if isinstance(creation_date, list):
                creation_date = creation_date[0]
            
            domain_age = None
            if creation_date:
                domain_age = (datetime.now() - creation_date).days
            
            # Registrar reputation analysis
            suspicious_registrars = [
                'namecheap', 'godaddy', 'register.com', 'network solutions'
            ]
            registrar = w.registrar.lower() if w.registrar else 'unknown'
            is_suspicious_registrar = any(sus in registrar for sus in suspicious_registrars)
            
            return {
                'domain_age_days': domain_age,
                'registrar': w.registrar,
                'creation_date': str(creation_date) if creation_date else None,
                'expiration_date': str(w.expiration_date) if w.expiration_date else None,
                'name_servers': w.name_servers,
                'is_suspicious_registrar': is_suspicious_registrar,
                'risk_score': self._calculate_whois_risk(domain_age, is_suspicious_registrar)
            }
            
        except Exception as e:
            logger.error(f"WHOIS analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _analyze_dns(self, domain):
        """DNS analysis using dig and dns.resolver"""
        try:
            results = {}
            
            # Use dig for comprehensive DNS analysis
            dig_commands = [
                ['dig', '+short', domain],                    # A records
                ['dig', '+short', 'AAAA', domain],           # AAAA records
                ['dig', '+short', 'MX', domain],             # MX records
                ['dig', '+short', 'NS', domain],             # NS records
                ['dig', '+short', 'TXT', domain],            # TXT records
                ['dig', '+short', 'CNAME', domain],          # CNAME records
            ]
            
            for cmd in dig_commands:
                try:
                    output = subprocess.run(cmd, capture_output=True, text=True, timeout=10)
                    record_type = cmd[2] if len(cmd) > 2 else 'A'
                    results[record_type.lower()] = [
                        line.strip() for line in output.stdout.split('\n') if line.strip()
                    ]
                except subprocess.TimeoutExpired:
                    record_type = cmd[2] if len(cmd) > 2 else 'A'
                    results[record_type.lower()] = ['timeout']
                except Exception as e:
                    record_type = cmd[2] if len(cmd) > 2 else 'A'
                    results[record_type.lower()] = [f'error: {str(e)}']
            
            # DNS resolution with Python for additional analysis
            resolver = dns.resolver.Resolver()
            resolver.timeout = 5
            resolver.lifetime = 5
            
            try:
                answers = resolver.resolve(domain, 'A')
                ips = [str(rdata) for rdata in answers]
                results['python_a_records'] = ips
                results['ip_count'] = len(ips)
            except:
                results['python_a_records'] = []
                results['ip_count'] = 0
            
            # Check for multiple IPs (potential fast-flux)
            multiple_ips = results.get('ip_count', 0) > 3
            results['potential_fast_flux'] = multiple_ips
            
            return {
                **results,
                'risk_score': self._calculate_dns_risk(results)
            }
            
        except Exception as e:
            logger.error(f"DNS analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _analyze_network_traffic(self, domain):
        """Network traffic analysis using tcpdump/tshark"""
        try:
            results = {}
            
            # Get IP addresses for the domain
            try:
                ips = socket.gethostbyname_ex(domain)[2]
            except:
                ips = []
            
            # Analyze recent traffic with tshark
            if ips:
                for ip in ips[:3]:  # Limit to first 3 IPs
                    try:
                        # Capture recent traffic to/from this IP
                        cmd = [
                            'tshark', '-r', '/tmp/capture.pcap',
                            '-Y', f'ip.addr == {ip}',
                            '-T', 'fields',
                            '-e', 'frame.time_epoch',
                            '-e', 'ip.src',
                            '-e', 'ip.dst',
                            '-e', 'tcp.port',
                            '-e', 'udp.port',
                            '-E', 'separator=,'
                        ]
                        
                        output = subprocess.run(cmd, capture_output=True, text=True, timeout=30)
                        
                        if output.stdout:
                            connections = []
                            for line in output.stdout.strip().split('\n'):
                                if line:
                                    parts = line.split(',')
                                    connections.append({
                                        'timestamp': parts[0],
                                        'src_ip': parts[1],
                                        'dst_ip': parts[2],
                                        'port': parts[3] if len(parts) > 3 else None
                                    })
                            
                            results[f'ip_{ip}'] = {
                                'connection_count': len(connections),
                                'connections': connections[:10]  # Limit to 10 recent connections
                            }
                    
                    except subprocess.TimeoutExpired:
                        results[f'ip_{ip}'] = {'error': 'timeout'}
                    except Exception as e:
                        results[f'ip_{ip}'] = {'error': str(e)}
            
            return {
                **results,
                'risk_score': self._calculate_network_risk(results)
            }
            
        except Exception as e:
            logger.error(f"Network analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _analyze_connection_patterns(self, domain):
        """Analyze connection patterns using netsniff-ng"""
        try:
            # This would typically involve analyzing captured packets
            # For now, we'll analyze patterns from our database
            
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Get query patterns for this domain
            cursor.execute('''
                SELECT timestamp, source_ip, dest_ip 
                FROM dns_queries 
                WHERE domain = ? 
                ORDER BY timestamp DESC 
                LIMIT 100
            ''', (domain,))
            
            queries = cursor.fetchall()
            conn.close()
            
            if not queries:
                return {'patterns': [], 'risk_score': 0}
            
            # Analyze timing patterns
            timestamps = [q[0] for q in queries]
            intervals = []
            
            for i in range(1, len(timestamps)):
                intervals.append(timestamps[i] - timestamps[i-1])
            
            # Detect regular intervals (potential beaconing)
            if intervals:
                avg_interval = sum(intervals) / len(intervals)
                variance = sum((x - avg_interval) ** 2 for x in intervals) / len(intervals)
                std_dev = variance ** 0.5
                
                # Low standard deviation indicates regular intervals
                regularity_score = max(0, 100 - (std_dev / avg_interval * 100) if avg_interval > 0 else 0)
            else:
                regularity_score = 0
            
            return {
                'query_count': len(queries),
                'unique_sources': len(set(q[1] for q in queries)),
                'unique_destinations': len(set(q[2] for q in queries)),
                'avg_interval_seconds': avg_interval / 1000 if intervals else 0,
                'regularity_score': regularity_score,
                'potential_beaconing': regularity_score > 70,
                'risk_score': min(100, regularity_score)
            }
            
        except Exception as e:
            logger.error(f"Pattern analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _detect_fast_flux(self, domain):
        """Detect fast-flux DNS behavior"""
        try:
            # Query the domain multiple times to see if IPs change
            ip_sets = []
            
            for _ in range(5):  # Query 5 times with 2-second intervals
                try:
                    ips = socket.gethostbyname_ex(domain)[2]
                    ip_sets.append(set(ips))
                    time.sleep(2)
                except:
                    pass
            
            if not ip_sets:
                return {'fast_flux_detected': False, 'risk_score': 0}
            
            # Check if IPs change between queries
            all_ips = set()
            for ip_set in ip_sets:
                all_ips.update(ip_set)
            
            # If we see many different IPs, it might be fast-flux
            fast_flux_score = min(100, len(all_ips) * 10)
            
            return {
                'fast_flux_detected': len(all_ips) > 5,
                'unique_ips_seen': len(all_ips),
                'ip_changes': len(set(tuple(sorted(s)) for s in ip_sets)),
                'risk_score': fast_flux_score
            }
            
        except Exception as e:
            logger.error(f"Fast-flux detection failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _detect_beaconing(self, domain):
        """Detect beaconing patterns"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Get queries from last 24 hours
            since = int((datetime.now() - timedelta(hours=24)).timestamp() * 1000)
            
            cursor.execute('''
                SELECT timestamp 
                FROM dns_queries 
                WHERE domain = ? AND timestamp > ?
                ORDER BY timestamp
            ''', (domain, since))
            
            timestamps = [row[0] for row in cursor.fetchall()]
            conn.close()
            
            if len(timestamps) < 5:
                return {'beaconing_detected': False, 'risk_score': 0}
            
            # Convert to seconds and calculate intervals
            timestamps_sec = [t / 1000 for t in timestamps]
            intervals = []
            
            for i in range(1, len(timestamps_sec)):
                intervals.append(timestamps_sec[i] - timestamps_sec[i-1])
            
            # Look for regular intervals (characteristic of beaconing)
            if intervals:
                avg_interval = sum(intervals) / len(intervals)
                variance = sum((x - avg_interval) ** 2 for x in intervals) / len(intervals)
                std_dev = variance ** 0.5
                
                # Coefficient of variation - lower means more regular
                cv = (std_dev / avg_interval * 100) if avg_interval > 0 else 100
                
                beaconing_score = max(0, 100 - cv)
                beaconing_detected = beaconing_score > 75 and avg_interval < 3600  # Less than hourly
            else:
                beaconing_score = 0
                beaconing_detected = False
            
            return {
                'beaconing_detected': beaconing_detected,
                'query_count_24h': len(timestamps),
                'avg_interval_seconds': avg_interval,
                'coefficient_of_variation': cv if intervals else 100,
                'beaconing_score': beaconing_score,
                'risk_score': beaconing_score
            }
            
        except Exception as e:
            logger.error(f"Beaconing detection failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _analyze_burst_behavior(self, domain):
        """Analyze burst behavior in DNS queries"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            # Get queries from last hour
            since = int((datetime.now() - timedelta(hours=1)).timestamp() * 1000)
            
            cursor.execute('''
                SELECT timestamp 
                FROM dns_queries 
                WHERE domain = ? AND timestamp > ?
                ORDER BY timestamp
            ''', (domain, since))
            
            timestamps = [row[0] for row in cursor.fetchall()]
            conn.close()
            
            if len(timestamps) < 2:
                return {'burst_detected': False, 'risk_score': 0}
            
            # Detect bursts - many queries in short time
            burst_threshold = 10  # 10 queries
            burst_window = 60000   # 1 minute in milliseconds
            
            bursts = []
            for i in range(len(timestamps)):
                burst_count = 0
                for j in range(i, len(timestamps)):
                    if timestamps[j] - timestamps[i] <= burst_window:
                        burst_count += 1
                    else:
                        break
                
                if burst_count >= burst_threshold:
                    bursts.append({
                        'start_time': timestamps[i],
                        'query_count': burst_count,
                        'duration_ms': timestamps[i + burst_count - 1] - timestamps[i]
                    })
            
            return {
                'burst_detected': len(bursts) > 0,
                'burst_count': len(bursts),
                'max_burst_size': max([b['query_count'] for b in bursts]) if bursts else 0,
                'bursts': bursts[:5],  # Limit to 5 most recent bursts
                'risk_score': min(100, len(bursts) * 20)
            }
            
        except Exception as e:
            logger.error(f"Burst analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _analyze_reputation(self, domain):
        """Analyze domain reputation"""
        try:
            # Check against known threat intelligence sources
            suspicious_patterns = [
                r'.*\.tk$',           # Free TLDs often used maliciously
                r'.*\.ml$',           # Free TLDs
                r'.*\.ga$',           # Free TLDs
                r'[0-9]{1,3}\-[0-9]{1,3}\-[0-9]{1,3}\-[0-9]{1,3}',  # Dynamic DNS
                r'.*\.duckdns\.org',  # Dynamic DNS
                r'.*\.no-ip\.org',    # Dynamic DNS
            ]
            
            is_suspicious = any(re.match(pattern, domain.lower()) for pattern in suspicious_patterns)
            
            # Check domain length
            domain_length = len(domain)
            is_long_domain = domain_length > 50
            
            # Check for random-looking domains
            import string
            random_score = 0
            if len(domain) > 10:
                vowels = sum(1 for c in domain.lower() if c in 'aeiou')
                consonants = len(domain) - vowels - domain.count('.')
                if consonants > 0:
                    vowel_ratio = vowels / consonants
                    random_score = abs(vowel_ratio - 0.4) * 100  # Normal ratio is around 0.4
            
            return {
                'is_suspicious_tld': is_suspicious,
                'is_long_domain': is_long_domain,
                'domain_length': domain_length,
                'random_score': random_score,
                'risk_score': (50 if is_suspicious else 0) + 
                              (20 if is_long_domain else 0) + 
                              min(30, random_score)
            }
            
        except Exception as e:
            logger.error(f"Reputation analysis failed for {domain}: {str(e)}")
            return {'error': str(e), 'risk_score': 50}
    
    def _calculate_risk_score(self, results):
        """Calculate overall risk score from all analyses"""
        total_score = 0
        weight_sum = 0
        
        weights = {
            'whois': 0.15,
            'dns': 0.20,
            'network': 0.15,
            'patterns': 0.20,
            'fastflux': 0.15,
            'beaconing': 0.10,
            'burst': 0.05
        }
        
        for analysis_type, weight in weights.items():
            if analysis_type in results:
                analysis_result = results[analysis_type]
                logger.debug(f"Processing {analysis_type}: {type(analysis_result)} - {analysis_result}")
                
                # Handle both dict and int types
                if isinstance(analysis_result, dict):
                    if 'risk_score' in analysis_result:
                        total_score += analysis_result['risk_score'] * weight
                        weight_sum += weight
                    elif 'error' in analysis_result:
                        # Use default risk score for errors
                        total_score += 50 * weight
                        weight_sum += weight
                elif isinstance(analysis_result, int):
                    total_score += analysis_result * weight
                    weight_sum += weight
                else:
                    logger.warning(f"Unexpected type for {analysis_type}: {type(analysis_result)}")
                    # Use default risk score
                    total_score += 50 * weight
                    weight_sum += weight
        
        return int(total_score / weight_sum) if weight_sum > 0 else 50
    
    def _calculate_whois_risk(self, domain_age, is_suspicious_registrar):
        """Calculate WHOIS-specific risk score"""
        risk = 0
        
        if domain_age is None:
            risk += 30  # Unknown age
        elif domain_age < 30:  # Less than 30 days
            risk += 40
        elif domain_age < 365:  # Less than 1 year
            risk += 20
        
        if is_suspicious_registrar:
            risk += 20
        
        return min(100, risk)
    
    def _calculate_dns_risk(self, dns_results):
        """Calculate DNS-specific risk score"""
        risk = 0
        
        # Handle both dict and int types
        if isinstance(dns_results, dict):
            if dns_results.get('potential_fast_flux'):
                risk += 40
            
            ip_count = dns_results.get('ip_count', 0)
            if ip_count > 10:
                risk += 30
            elif ip_count > 5:
                risk += 15
        elif isinstance(dns_results, int):
            # If it's just an integer risk score, use it directly
            risk = dns_results
        
        return min(100, risk)
    
    def _calculate_network_risk(self, network_results):
        """Calculate network-specific risk score"""
        risk = 0
        
        # Handle both dict and int types
        if isinstance(network_results, dict):
            for key, value in network_results.items():
                if isinstance(value, dict) and 'connection_count' in value:
                    conn_count = value['connection_count']
                    if conn_count > 1000:
                        risk += 30
                    elif conn_count > 100:
                        risk += 15
        elif isinstance(network_results, int):
            # If it's just an integer risk score, use it directly
            risk = network_results
        
        return min(100, risk)
    
    def _monitor_network(self):
        """Background network monitoring using netsniff-ng"""
        while self.monitoring:
            try:
                # This would typically involve running netsniff-ng
                # For demonstration, we'll simulate with periodic checks
                time.sleep(60)  # Check every minute
                
                # In a real implementation, you'd:
                # 1. Start netsniff-ng capture
                # 2. Parse captured packets
                # 3. Update domain statistics
                # 4. Detect anomalies
                
            except Exception as e:
                logger.error(f"Network monitoring error: {str(e)}")
                time.sleep(60)
    
    def _analyze_patterns(self):
        """Background pattern analysis"""
        while self.monitoring:
            try:
                time.sleep(300)  # Analyze every 5 minutes
                
                # Periodic pattern analysis
                # This would involve checking for:
                # 1. New domains with suspicious patterns
                # 2. Changes in established patterns
                # 3. Correlations between domains
                
            except Exception as e:
                logger.error(f"Pattern analysis error: {str(e)}")
                time.sleep(300)
    
    def get_domain_analysis(self, domain):
        """Get cached or fresh analysis for a domain"""
        # Check if we have recent analysis
        conn = sqlite3.connect('datasentry.db')
        cursor = conn.cursor()
        
        cursor.execute('''
            SELECT result FROM analysis_results 
            WHERE domain = ? AND created_at > datetime('now', '-1 hour')
            ORDER BY created_at DESC LIMIT 1
        ''', (domain,))
        
        row = cursor.fetchone()
        conn.close()
        
        if row:
            return json.loads(row[0])
        else:
            return self.analyze_domain(domain)
