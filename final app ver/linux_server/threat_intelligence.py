#!/usr/bin/env python3
"""
Threat Intelligence Module
Integrates with various threat feeds and security tools
"""

import requests
import json
import sqlite3
import threading
import time
import logging
from datetime import datetime, timedelta
import hashlib
import re
from collections import defaultdict

logger = logging.getLogger(__name__)

class ThreatIntel:
    def __init__(self):
        self.threat_feeds = {
            'malware_domains': self._load_malware_domains(),
            'suspicious_ips': self._load_suspicious_ips(),
            'known_c2': self._load_known_c2_servers()
        }
        self.monitoring = False
        
    def start_monitoring(self):
        """Start continuous threat monitoring"""
        self.monitoring = True
        threading.Thread(target=self._update_threat_feeds, daemon=True).start()
        threading.Thread(target=self._monitor_new_domains, daemon=True).start()
        
    def _load_malware_domains(self):
        """Load known malware domains from various sources"""
        domains = set()
        
        # In a real implementation, you'd fetch from:
        # - MalwareDomainList
        # - PhishTank
        # - OpenPhish
        # - Various security vendors
        
        # For demonstration, include some known patterns
        suspicious_patterns = [
            r'.*\.tk$',
            r'.*\.ml$',
            r'.*\.ga$',
            r'.*\.cf$',
            r'[0-9]{1,3}\-[0-9]{1,3}\-[0-9]{1,3}\-[0-9]{1,3}.*\.onion',
            '.*\.duckdns\.org',
            '.*\.no-ip\.org',
            '.*\.ddns\.net'
        ]
        
        return suspicious_patterns
    
    def _load_suspicious_ips(self):
        """Load known suspicious IP ranges"""
        # Known malicious IP ranges (examples)
        return [
            '192.0.2.0/24',     # TEST-NET-1
            '198.51.100.0/24',  # TEST-NET-2
            '203.0.113.0/24',   # TEST-NET-3
            # In reality, you'd load from threat intelligence feeds
        ]
    
    def _load_known_c2_servers(self):
        """Load known command and control servers"""
        # Known C2 domains and IPs
        return {
            'domains': [
                'malicious-c2.example.com',
                'botnet-controller.example.org'
            ],
            'ips': [
                '10.0.0.0/8',     # Private networks (shouldn't be internet-facing)
                '172.16.0.0/12',  # Private networks
                '192.168.0.0/16'  # Private networks
            ]
        }
    
    def check_domain_threat(self, domain):
        """Check if domain is flagged in threat intelligence"""
        threats = []
        risk_score = 0
        
        # Check against malware domain patterns
        for pattern in self.threat_feeds['malware_domains']:
            if re.match(pattern, domain.lower()):
                threats.append({
                    'type': 'malware_domain_pattern',
                    'pattern': pattern,
                    'severity': 'high'
                })
                risk_score += 40
        
        # Check domain hash against known malicious hashes
        domain_hash = hashlib.sha256(domain.encode()).hexdigest()
        if self._is_known_malicious_hash(domain_hash):
            threats.append({
                'type': 'known_malicious_hash',
                'hash': domain_hash,
                'severity': 'critical'
            })
            risk_score += 80
        
        # Check for DGA (Domain Generation Algorithm) patterns
        dga_score = self._detect_dga(domain)
        if dga_score > 70:
            threats.append({
                'type': 'dga_detected',
                'score': dga_score,
                'severity': 'high'
            })
            risk_score += 30
        
        return {
            'threats': threats,
            'risk_score': min(100, risk_score),
            'is_malicious': risk_score > 60
        }
    
    def check_ip_threat(self, ip):
        """Check if IP is flagged in threat intelligence"""
        threats = []
        risk_score = 0
        
        # Check against suspicious IP ranges
        for suspicious_range in self.threat_feeds['suspicious_ips']:
            if self._ip_in_range(ip, suspicious_range):
                threats.append({
                    'type': 'suspicious_range',
                    'range': suspicious_range,
                    'severity': 'medium'
                })
                risk_score += 30
        
        # Check against known C2 servers
        for c2_range in self.threat_feeds['known_c2']['ips']:
            if self._ip_in_range(ip, c2_range):
                threats.append({
                    'type': 'known_c2',
                    'range': c2_range,
                    'severity': 'critical'
                })
                risk_score += 70
        
        # Additional IP reputation checks
        if self._is_tor_exit_node(ip):
            threats.append({
                'type': 'tor_exit_node',
                'severity': 'medium'
            })
            risk_score += 20
        
        return {
            'threats': threats,
            'risk_score': min(100, risk_score),
            'is_malicious': risk_score > 60
        }
    
    def _detect_dga(self, domain):
        """Detect Domain Generation Algorithm patterns"""
        score = 0
        
        # Remove TLD
        domain_parts = domain.split('.')
        if len(domain_parts) < 2:
            return 0
        
        main_domain = domain_parts[0]
        
        # Length analysis
        if len(main_domain) > 20:
            score += 20
        elif len(main_domain) > 15:
            score += 10
        
        # Character distribution analysis
        vowels = 'aeiou'
        consonants = 'bcdfghjklmnpqrstvwxyz'
        
        vowel_count = sum(1 for c in main_domain.lower() if c in vowels)
        consonant_count = sum(1 for c in main_domain.lower() if c in consonants)
        
        if consonant_count > 0:
            vowel_ratio = vowel_count / consonant_count
            # DGA domains often have unusual vowel/consonant ratios
            if vowel_ratio < 0.2 or vowel_ratio > 0.8:
                score += 25
        
        # Repeated characters
        repeated_chars = len(set(main_domain)) / len(main_domain) if main_domain else 0
        if repeated_chars < 0.5:  # Many repeated characters
            score += 15
        
        # Numeric characters
        numeric_count = sum(1 for c in main_domain if c.isdigit())
        if numeric_count > len(main_domain) * 0.3:  # More than 30% numbers
            score += 20
        
        # Random-looking sequences
        if self._has_random_sequences(main_domain):
            score += 20
        
        return min(100, score)
    
    def _has_random_sequences(self, domain):
        """Check for random-looking character sequences"""
        # Simple heuristic: look for alternating consonants and vowels
        domain = domain.lower()
        alternating_score = 0
        
        for i in range(len(domain) - 2):
            a, b, c = domain[i], domain[i+1], domain[i+2]
            
            # Check if pattern alternates between vowel and consonant
            if ((a in 'aeiou' and b not in 'aeiou' and c in 'aeiou') or
                (a not in 'aeiou' and b in 'aeiou' and c not in 'aeiou')):
                alternating_score += 1
        
        return alternating_score > len(domain) * 0.3
    
    def _is_known_malicious_hash(self, domain_hash):
        """Check if domain hash is in known malicious list"""
        # In reality, you'd check against threat intelligence databases
        # For demonstration, return False
        return False
    
    def _ip_in_range(self, ip, range_str):
        """Check if IP is in CIDR range"""
        import ipaddress
        try:
            ip_obj = ipaddress.ip_address(ip)
            network = ipaddress.ip_network(range_str, strict=False)
            return ip_obj in network
        except:
            return False
    
    def _is_tor_exit_node(self, ip):
        """Check if IP is a known Tor exit node"""
        # In reality, you'd fetch from Tor exit node lists
        # For demonstration, return False
        return False
    
    def _update_threat_feeds(self):
        """Periodically update threat intelligence feeds"""
        while self.monitoring:
            try:
                # Update feeds every hour
                time.sleep(3600)
                
                # In reality, you'd:
                # 1. Fetch from malware domain lists
                # 2. Update from IP reputation services
                # 3. Get latest C2 server lists
                # 4. Update from security vendor feeds
                
                logger.info("Updated threat intelligence feeds")
                
            except Exception as e:
                logger.error(f"Error updating threat feeds: {str(e)}")
                time.sleep(3600)
    
    def _monitor_new_domains(self):
        """Monitor newly seen domains for threats"""
        while self.monitoring:
            try:
                time.sleep(300)  # Check every 5 minutes
                
                # Get recently seen domains
                conn = sqlite3.connect('datasentry.db')
                cursor = conn.cursor()
                
                cursor.execute('''
                    SELECT DISTINCT domain, COUNT(*) as query_count
                    FROM dns_queries 
                    WHERE created_at > datetime('now', '-5 minutes')
                    GROUP BY domain
                ''')
                
                recent_domains = cursor.fetchall()
                conn.close()
                
                for domain, count in recent_domains:
                    if domain != 'unknown.domain':
                        threat_info = self.check_domain_threat(domain)
                        
                        if threat_info['is_malicious']:
                            logger.warning(f"Malicious domain detected: {domain} - {threat_info}")
                            
                            # Store threat detection
                            conn = sqlite3.connect('datasentry.db')
                            cursor = conn.cursor()
                            
                            cursor.execute('''
                                INSERT INTO threat_detections 
                                (domain, threat_type, risk_score, details, detected_at)
                                VALUES (?, ?, ?, ?, ?)
                            ''', (
                                domain,
                                'threat_intelligence',
                                threat_info['risk_score'],
                                json.dumps(threat_info),
                                datetime.now()
                            ))
                            
                            conn.commit()
                            conn.close()
                
            except Exception as e:
                logger.error(f"Error monitoring new domains: {str(e)}")
                time.sleep(300)
    
    def get_recent_threats(self, hours=24):
        """Get recent threat detections"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            cursor.execute('''
                SELECT domain, threat_type, risk_score, details, detected_at
                FROM threat_detections 
                WHERE detected_at > datetime('now', '-{} hours')
                ORDER BY detected_at DESC
                LIMIT 50
            '''.format(hours))
            
            threats = []
            for row in cursor.fetchall():
                threats.append({
                    'domain': row[0],
                    'threat_type': row[1],
                    'risk_score': row[2],
                    'details': json.loads(row[3]),
                    'detected_at': row[4]
                })
            
            conn.close()
            return threats
            
        except Exception as e:
            logger.error(f"Error getting recent threats: {str(e)}")
            return []
    
    def analyze_domain_chain(self, domains):
        """Analyze a chain of related domains"""
        try:
            # Look for patterns across multiple domains
            patterns = {
                'similar_structure': self._find_similar_structure(domains),
                'same_registrar': self._check_same_registrar(domains),
                'temporal_correlation': self._check_temporal_correlation(domains),
                'ip_correlation': self._check_ip_correlation(domains)
            }
            
            # Calculate overall chain risk
            chain_risk = 0
            for pattern_name, pattern_data in patterns.items():
                if pattern_data.get('risk_score', 0) > 0:
                    chain_risk += pattern_data['risk_score']
            
            return {
                'patterns': patterns,
                'chain_risk_score': min(100, chain_risk),
                'domain_count': len(domains)
            }
            
        except Exception as e:
            logger.error(f"Error analyzing domain chain: {str(e)}")
            return {'error': str(e)}
    
    def _find_similar_structure(self, domains):
        """Find domains with similar structural patterns"""
        if len(domains) < 2:
            return {'risk_score': 0}
        
        # Extract structural features
        structures = []
        for domain in domains:
            parts = domain.split('.')
            if len(parts) >= 2:
                structure = {
                    'length': len(parts[0]),
                    'tld': parts[-1],
                    'subdomain_count': len(parts) - 2,
                    'has_numbers': any(c.isdigit() for c in parts[0]),
                    'has_hyphens': '-' in parts[0]
                }
                structures.append(structure)
        
        # Check for similarities
        similar_count = 0
        for i in range(len(structures)):
            for j in range(i + 1, len(structures)):
                similarity = 0
                s1, s2 = structures[i], structures[j]
                
                if s1['tld'] == s2['tld']:
                    similarity += 25
                if abs(s1['length'] - s2['length']) <= 2:
                    similarity += 25
                if s1['has_numbers'] == s2['has_numbers']:
                    similarity += 25
                if s1['has_hyphens'] == s2['has_hyphens']:
                    similarity += 25
                
                if similarity >= 75:
                    similar_count += 1
        
        similarity_ratio = similar_count / (len(domains) * (len(domains) - 1) / 2)
        
        return {
            'similar_pairs': similar_count,
            'similarity_ratio': similarity_ratio,
            'risk_score': int(similarity_ratio * 100)
        }
    
    def _check_same_registrar(self, domains):
        """Check if domains use the same registrar"""
        # In reality, you'd perform WHOIS lookups
        # For demonstration, return mock data
        return {
            'same_registrar': False,
            'risk_score': 0
        }
    
    def _check_temporal_correlation(self, domains):
        """Check temporal correlation between domain queries"""
        try:
            conn = sqlite3.connect('datasentry.db')
            cursor = conn.cursor()
            
            correlations = []
            for domain in domains:
                cursor.execute('''
                    SELECT timestamp FROM dns_queries 
                    WHERE domain = ? 
                    ORDER BY timestamp DESC LIMIT 10
                ''', (domain,))
                
                timestamps = [row[0] for row in cursor.fetchall()]
                if timestamps:
                    correlations.append({
                        'domain': domain,
                        'first_seen': min(timestamps),
                        'last_seen': max(timestamps),
                        'query_count': len(timestamps)
                    })
            
            conn.close()
            
            # Check if domains appeared around the same time
            if len(correlations) > 1:
                first_times = [c['first_seen'] for c in correlations]
                time_span = max(first_times) - min(first_times)
                
                # If all domains appeared within a short time window
                if time_span < 3600000:  # 1 hour in milliseconds
                    return {
                        'temporal_correlation': True,
                        'time_span_hours': time_span / 3600000,
                        'risk_score': 50
                    }
            
            return {
                'temporal_correlation': False,
                'risk_score': 0
            }
            
        except Exception as e:
            logger.error(f"Error checking temporal correlation: {str(e)}")
            return {'risk_score': 0}
    
    def _check_ip_correlation(self, domains):
        """Check if domains resolve to related IPs"""
        try:
            import socket
            
            domain_ips = {}
            for domain in domains:
                try:
                    ips = socket.gethostbyname_ex(domain)[2]
                    domain_ips[domain] = set(ips)
                except:
                    domain_ips[domain] = set()
            
            # Check for overlapping IPs or same subnets
            all_ips = set()
            for ips in domain_ips.values():
                all_ips.update(ips)
            
            # Check if domains share IPs or are in same subnet
            shared_resources = 0
            for domain1, ips1 in domain_ips.items():
                for domain2, ips2 in domain_ips.items():
                    if domain1 != domain2:
                        # Direct IP overlap
                        if ips1 & ips2:
                            shared_resources += 1
                        
                        # Same subnet check (simplified)
                        for ip1 in ips1:
                            for ip2 in ips2:
                                if self._same_subnet(ip1, ip2):
                                    shared_resources += 1
            
            correlation_score = min(100, shared_resources * 20)
            
            return {
                'shared_ips': shared_resources,
                'correlation_score': correlation_score,
                'risk_score': correlation_score
            }
            
        except Exception as e:
            logger.error(f"Error checking IP correlation: {str(e)}")
            return {'risk_score': 0}
    
    def _same_subnet(self, ip1, ip2):
        """Check if two IPs are in the same /24 subnet"""
        try:
            return ip1.rsplit('.', 1)[0] == ip2.rsplit('.', 1)[0]
        except:
            return False
