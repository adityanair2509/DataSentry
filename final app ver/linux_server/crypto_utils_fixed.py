#!/usr/bin/env python3
"""
Server-side Crypto Utilities for DataSentry
Handles AES-256 GCM decryption of encrypted data from Android app
"""

import base64
import json
import logging
import os
import time
import math
import subprocess
import re
import sqlite3
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend

# Set up logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class ServerCryptoUtils:
    """Server-side encryption utilities"""
    
    def __init__(self):
        self.key = base64.b64decode('AWnheBkwOK67kUvXgDFKt6rPmQNFknFwoMrL2Tgo1YI=')
    
    def decrypt_data(self, encrypted_data: str) -> dict:
        """Decrypt encrypted data from Android app"""
        try:
            # Decode base64
            encrypted_bytes = base64.b64decode(encrypted_data)
            
            # Extract IV and ciphertext
            iv = encrypted_bytes[:12]
            ciphertext = encrypted_bytes[12:-16]
            tag = encrypted_bytes[-16:]
            
            # Decrypt
            cipher = Cipher(algorithms.AES(self.key), modes.GCM(iv, tag), backend=default_backend())
            decryptor = cipher.decryptor()
            plaintext = decryptor.update(ciphertext) + decryptor.finalize()
            
            return json.loads(plaintext.decode('utf-8'))
            
        except Exception as e:
            logger.error(f"Decryption failed: {str(e)}")
            raise

class EncryptedDataProcessor:
    """Process encrypted DNS data with security analysis"""
    
    def __init__(self, crypto_utils: ServerCryptoUtils):
        self.crypto_utils = crypto_utils
    
    def process_encrypted_payload(self, payload: dict) -> dict:
        """Process encrypted payload and analyze domains"""
        try:
            # Decrypt the data
            decrypted_data = self.crypto_utils.decrypt_data(payload['encrypted_data'])
            
            # Analyze each DNS query
            analyzed_queries = []
            for query in decrypted_data.get('dns_queries', []):
                analyzed_query = self._analyze_domain(query)
                analyzed_queries.append(analyzed_query)
            
            # Store in database
            self._store_dns_queries(analyzed_queries, payload.get('device_id', ''))
            
            return {
                'status': 'success',
                'processed_entries': analyzed_queries,
                'processed_count': len(analyzed_queries)
            }
            
        except Exception as e:
            logger.error(f"Processing failed: {str(e)}")
            return {
                'status': 'error',
                'error': str(e),
                'processed_count': 0
            }
    
    def _calculate_entropy(self, domain: str) -> float:
        """Calculate entropy of domain string"""
        try:
            if not domain:
                return 0.0
            
            # Count character frequencies
            char_counts = {}
            for char in domain.lower():
                char_counts[char] = char_counts.get(char, 0) + 1
            
            # Calculate entropy
            entropy = 0.0
            for count in char_counts.values():
                probability = count / len(domain)
                entropy -= probability * math.log2(probability)
            
            return entropy
            
        except Exception as e:
            logger.error(f"Entropy calculation failed: {str(e)}")
            return 0.0
    
    def _analyze_domain(self, dns_query: dict) -> dict:
        """Perform server-side domain analysis using security tools"""
        try:
            domain = dns_query['domain']
            logger.info(f"Performing security analysis on: {domain}")
            
            # Initialize analysis results
            risk_score = 0
            is_risk = False
            risk_reasons = []
            
            # WHITELIST known legitimate domains
            legitimate_domains = [
                'google.com', 'youtube.com', 'googlevideo.com', 'googleapis.com',
                'googleusercontent.com', 'gstatic.com', 'ggpht.com', 'ytimg.com',
                'google.co.in', 'google.ca', 'google.co.uk', 'google.de',
                'google.fr', 'google.jp', 'google.com.au', 'google.com.br',
                'google.es', 'google.it', 'google.ru', 'google.cn',
                'google.com.mx', 'google.nl', 'google.se', 'google.no',
                'google.dk', 'google.fi', 'google.ch', 'google.at',
                'google.be', 'google.ie', 'google.pl', 'google.gr',
                'google.pt', 'google.cz', 'google.hu', 'google.ro',
                'google.bg', 'google.hr', 'google.si', 'google.sk',
                'google.ee', 'google.lv', 'google.lt', 'mtalk.google.com',
                's.youtube.com', 'youtubei.googleapis.com', 'redirector.googlevideo.com'
            ]
            
            # Check if domain or parent domain is in whitelist
            domain_lower = domain.lower()
            is_legitimate = False
            for legit_domain in legitimate_domains:
                if legit_domain in domain_lower or domain_lower.endswith('.' + legit_domain):
                    is_legitimate = True
                    break
            
            if is_legitimate:
                logger.info(f"Domain {domain} is whitelisted as legitimate")
                analyzed_query = dns_query.copy()
                analyzed_query.update({
                    'is_risk': False,
                    'risk_score': 0,
                    'risk_reasons': ['Whitelisted legitimate domain'],
                    'analysis_timestamp': int(time.time()),
                    'analysis_engine': 'Security Analysis'
                })
                return analyzed_query
            
            # 1. Basic Pattern Analysis (fast, reliable)
            patterns_result = self._basic_pattern_analysis(domain)
            risk_score += patterns_result['risk_score']
            risk_reasons.extend(patterns_result['reasons'])
            
            # 2. Try WHOIS (quick timeout)
            try:
                whois_result = subprocess.run(['whois', domain], capture_output=True, text=True, timeout=3)
                if whois_result.returncode == 0:
                    whois_analysis = self._analyze_whois(whois_result.stdout, domain)
                    risk_score += whois_analysis['risk_score']
                    risk_reasons.extend(whois_analysis['reasons'])
                    logger.info(f"WHOIS analysis completed for {domain}")
                else:
                    logger.warning(f"WHOIS failed for {domain}")
            except subprocess.TimeoutExpired:
                logger.warning(f"WHOIS timeout for {domain}")
                risk_score += 5
                risk_reasons.append("WHOIS timeout")
            except Exception as e:
                logger.error(f"WHOIS error for {domain}: {str(e)}")
            
            # 3. Try DIG (quick timeout)
            try:
                dig_result = subprocess.run(['dig', '+short', '+time=1', domain], capture_output=True, text=True, timeout=2)
                if dig_result.returncode == 0:
                    dig_analysis = self._analyze_dig(dig_result.stdout, domain)
                    risk_score += dig_analysis['risk_score']
                    risk_reasons.extend(dig_analysis['reasons'])
                    logger.info(f"DIG analysis completed for {domain}")
                else:
                    logger.warning(f"DIG failed for {domain}")
            except subprocess.TimeoutExpired:
                logger.warning(f"DIG timeout for {domain}")
                risk_score += 5
                risk_reasons.append("DNS timeout")
            except Exception as e:
                logger.error(f"DIG error for {domain}: {str(e)}")
                risk_score += 5
                risk_reasons.append("DNS analysis failed")
            
            # 4. Network Analysis (quick ping)
            try:
                ping_result = subprocess.run(['ping', '-c', '1', '-W', '1', domain], capture_output=True, text=True, timeout=3)
                if 'bytes from' in ping_result.stdout:
                    logger.info(f"Host responds to ping: {domain}")
                else:
                    risk_score += 10
                    risk_reasons.append("Host unreachable")
            except Exception as e:
                logger.warning(f"Network analysis failed for {domain}: {str(e)}")
            
            # 5. C2 Detection (beaconing patterns)
            c2_result = self._detect_c2_patterns(domain)
            risk_score += c2_result['risk_score']
            risk_reasons.extend(c2_result['reasons'])
            
            # 6. Buffer Overflow Detection
            buffer_result = self._detect_buffer_overflow(domain)
            risk_score += buffer_result['risk_score']
            risk_reasons.extend(buffer_result['reasons'])
            
            # Determine final risk assessment
            is_risk = risk_score >= 30
            
            # Update the query with analysis results
            analyzed_query = dns_query.copy()
            analyzed_query.update({
                'is_risk': is_risk,
                'risk_score': risk_score,
                'risk_reasons': risk_reasons,
                'analysis_timestamp': int(time.time()),
                'analysis_engine': 'Security Analysis'
            })
            
            if is_risk:
                logger.warning(f"DOMAIN MARKED AS RISKY: {domain} (score: {risk_score})")
                logger.warning(f"Risk reasons: {risk_reasons}")
            else:
                logger.info(f"Domain appears safe: {domain} (score: {risk_score})")
            
            return analyzed_query
            
        except Exception as e:
            logger.error(f"Failed to analyze domain {dns_query.get('domain', 'unknown')}: {str(e)}")
            # Return original query with default analysis
            dns_query.update({
                'is_risk': False,
                'risk_score': 0,
                'risk_reasons': ['Analysis failed'],
                'analysis_timestamp': int(time.time()),
                'analysis_engine': 'Security Analysis'
            })
            return dns_query
    
    def _basic_pattern_analysis(self, domain: str) -> dict:
        """Basic pattern analysis for domain security"""
        risk_score = 0
        reasons = []
        
        # High entropy check
        entropy = self._calculate_entropy(domain)
        if entropy > 3.5:
            risk_score += 15
            reasons.append(f"High entropy domain: {entropy:.2f}")
        
        # Length check
        if len(domain) > 50:
            risk_score += 10
            reasons.append("Very long domain")
        
        # Character patterns
        if re.search(r'[0-9]{3,}', domain):
            risk_score += 10
            reasons.append("Multiple consecutive numbers")
        
        if re.search(r'[a-z]{10,}', domain):
            risk_score += 5
            reasons.append("Long consecutive letters")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _detect_c2_patterns(self, domain: str) -> dict:
        """Detect C2 beaconing patterns"""
        risk_score = 0
        reasons = []
        
        # Common C2 domain patterns
        c2_patterns = [
            r'.*\.tk$',  # Free TLDs often used for C2
            r'.*\.ml$',  # Free TLDs often used for C2
            r'.*\.ga$',  # Free TLDs often used for C2
            r'.*\.cf$',  # Free TLDs often used for C2
            r'[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}',  # IP addresses
            r'[a-f0-9]{32,}',  # Hexadecimal domains
            r'.*\.onion$',  # Tor domains
        ]
        
        for pattern in c2_patterns:
            if re.match(pattern, domain.lower()):
                risk_score += 25
                reasons.append(f"C2 pattern detected: {pattern}")
        
        # Check for common C2 keywords
        c2_keywords = ['botnet', 'c2', 'malware', 'phish', 'command', 'control']
        for keyword in c2_keywords:
            if keyword in domain.lower():
                risk_score += 30
                reasons.append(f"C2 keyword: {keyword}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _detect_buffer_overflow(self, domain: str) -> dict:
        """Detect potential buffer overflow patterns in domain"""
        risk_score = 0
        reasons = []
        
        # Check for very long domains (potential buffer overflow attempts)
        if len(domain) > 100:
            risk_score += 20
            reasons.append("Extremely long domain (potential buffer overflow)")
        elif len(domain) > 60:
            risk_score += 10
            reasons.append("Very long domain")
        
        # Check for repeated characters (common in overflow attempts)
        if len(domain) > 10:
            char_counts = {}
            for char in domain.lower():
                char_counts[char] = char_counts.get(char, 0) + 1
            
            for char, count in char_counts.items():
                if count > 5:
                    risk_score += 15
                    reasons.append(f"Repeated character pattern: {char}x{count}")
                    break
        
        # Check for suspicious character sequences
        suspicious_sequences = ['aaaa', 'bbbb', 'cccc', 'dddd', '1111', '2222', '3333', '4444']
        for seq in suspicious_sequences:
            if seq in domain.lower():
                risk_score += 25
                reasons.append(f"Suspicious sequence: {seq}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _analyze_whois(self, whois_output: str, domain: str) -> dict:
        """Analyze WHOIS output for security indicators"""
        risk_score = 0
        reasons = []
        
        # Check for privacy protection
        if 'privacy protect' in whois_output.lower() or 'whois privacy' in whois_output.lower():
            risk_score += 10
            reasons.append("WHOIS privacy protection")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _analyze_dig(self, dig_output: str, domain: str) -> dict:
        """Analyze DIG output for security indicators"""
        risk_score = 0
        reasons = []
        
        # Check if domain resolves to IP
        if not dig_output.strip():
            risk_score += 15
            reasons.append("Domain does not resolve")
        
        # Check for multiple IPs (potential load balancing/fast flux)
        ips = dig_output.strip().split('\n')
        if len(ips) > 3:
            risk_score += 20
            reasons.append("Multiple IP addresses")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _store_dns_queries(self, dns_queries: list, device_id: str):
        """Store DNS queries in SQLite database"""
        try:
            # Create database if it doesn't exist
            conn = sqlite3.connect('dns_analysis.db')
            cursor = conn.cursor()
            
            # Create table if it doesn't exist
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
            
            # Insert each DNS query
            for query in dns_queries:
                cursor.execute('''
                    INSERT INTO dns_queries 
                    (timestamp, domain, app_name, source_ip, dest_ip, protocol, size_bytes, 
                     is_risk, risk_score, risk_reasons, analysis_timestamp, analysis_engine, device_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ''', (
                    query.get('timestamp'),
                    query.get('domain'),
                    query.get('app_name'),
                    query.get('source_ip'),
                    query.get('dest_ip'),
                    query.get('protocol'),
                    query.get('size_bytes'),
                    query.get('is_risk', False),
                    query.get('risk_score', 0),
                    str(query.get('risk_reasons', [])),
                    query.get('analysis_timestamp'),
                    query.get('analysis_engine'),
                    device_id
                ))
            
            conn.commit()
            conn.close()
            
            logger.info(f"Stored {len(dns_queries)} DNS queries in database")
            
        except Exception as e:
            logger.error(f"Failed to store DNS queries: {str(e)}")
            raise
