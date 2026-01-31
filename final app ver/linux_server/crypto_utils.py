#!/usr/bin/env python3
"""
Server-side Crypto Utilities for DataSentry
Handles AES-256 GCM decryption of encrypted data from Android app
"""

import base64
import json
import logging
import os
import struct
import time
from datetime import datetime, timedelta
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
import sqlite3

logger = logging.getLogger(__name__)

class ServerCryptoUtils:
    """
    Server-side AES-256 GCM encryption/decryption utilities
    """
    
    def __init__(self, key_file_path="crypto_keys.dat"):
        self.key_file_path = key_file_path
        self.backend = default_backend()
        self.key = self._load_or_generate_key()
        self.aesgcm = AESGCM(self.key)
        
    def _load_or_generate_key(self) -> bytes:
        """
        Load existing key from file or generate new one
        """
        try:
            if os.path.exists(self.key_file_path):
                with open(self.key_file_path, 'rb') as f:
                    key_data = f.read()
                    if len(key_data) == 32:  # 256 bits
                        logger.info("Loaded existing AES-256 key")
                        return key_data
                    else:
                        logger.warning("Invalid key length, generating new key")
            
            # Generate new key
            key = AESGCM.generate_key(bit_length=256)
            
            # Save key to file
            with open(self.key_file_path, 'wb') as f:
                f.write(key)
            
            # Set secure file permissions
            os.chmod(self.key_file_path, 0o600)
            
            logger.info("Generated new AES-256 key")
            return key
            
        except Exception as e:
            logger.error(f"Failed to load/generate key: {str(e)}")
            # Fallback to environment variable
            env_key = os.getenv('DATASENTRY_AES_KEY')
            if env_key:
                return base64.b64decode(env_key.encode())
            else:
                raise Exception("No encryption key available")
    
    def decrypt_payload(self, encrypted_data: str) -> dict:
        """
        Decrypt Base64 encoded payload from Android app
        
        Args:
            encrypted_data: Base64 encoded encrypted data
            
        Returns:
            Decrypted JSON data as dictionary
        """
        try:
            # Decode Base64
            encrypted_bytes = base64.b64decode(encrypted_data.encode())
            
            logger.info(f"Encrypted bytes length: {len(encrypted_bytes)}")
            
            # Extract IV (first 12 bytes for GCM)
            if len(encrypted_bytes) < 12:
                raise ValueError("Encrypted data too short")
            
            iv = encrypted_bytes[:12]
            ciphertext = encrypted_bytes[12:]
            
            logger.info(f"IV length: {len(iv)}")
            logger.info(f"Ciphertext length: {len(ciphertext)}")
            
            # Decrypt using AES-GCM
            decrypted_bytes = self.aesgcm.decrypt(iv, ciphertext, None)
            
            # Parse JSON
            decrypted_data = json.loads(decrypted_bytes.decode('utf-8'))
            
            logger.debug(f"Successfully decrypted payload with {len(decrypted_data.get('entries', []))} entries")
            return decrypted_data
            
        except Exception as e:
            logger.error(f"Decryption failed: {str(e)}")
            raise ValueError(f"Failed to decrypt payload: {str(e)}")
    
    def encrypt_payload(self, data: dict) -> str:
        """
        Encrypt data for sending back to Android app (if needed)
        
        Args:
            data: Dictionary data to encrypt
            
        Returns:
            Base64 encoded encrypted data
        """
        try:
            # Convert to JSON
            json_data = json.dumps(data).encode('utf-8')
            
            # Generate random IV
            iv = os.urandom(12)
            
            # Encrypt
            ciphertext = self.aesgcm.encrypt(iv, json_data, None)
            
            # Combine IV + ciphertext and encode as Base64
            encrypted_bytes = iv + ciphertext
            return base64.b64encode(encrypted_bytes).decode('utf-8')
            
        except Exception as e:
            logger.error(f"Encryption failed: {str(e)}")
            raise ValueError(f"Failed to encrypt payload: {str(e)}")
    
    def validate_encryption(self, test_data: str = "DataSentry test") -> bool:
        """
        Test encryption/decryption functionality
        
        Args:
            test_data: Test string to encrypt/decrypt
            
        Returns:
            True if encryption/decryption works correctly
        """
        try:
            # Encrypt test data
            encrypted = self.encrypt_payload({"test": test_data})
            
            # Decrypt test data
            decrypted = self.decrypt_payload(encrypted)
            
            return decrypted.get("test") == test_data
            
        except Exception as e:
            logger.error(f"Encryption validation failed: {str(e)}")
            return False
    
    def get_key_info(self) -> dict:
        """
        Get information about the encryption key
        
        Returns:
            Dictionary with key information
        """
        return {
            "key_length": len(self.key) * 8,  # bits
            "algorithm": "AES-256-GCM",
            "key_file_exists": os.path.exists(self.key_file_path),
            "key_file_path": self.key_file_path,
            "backend": str(self.backend)
        }
    
    def rotate_key(self, new_key_file: str = None) -> bool:
        """
        Rotate encryption key (generate new key)
        
        Args:
            new_key_file: Optional new key file path
            
        Returns:
            True if key rotation successful
        """
        try:
            # Backup old key
            if os.path.exists(self.key_file_path):
                backup_path = f"{self.key_file_path}.backup.{int(time.time())}"
                os.rename(self.key_file_path, backup_path)
                logger.info(f"Backed up old key to {backup_path}")
            
            # Generate new key
            if new_key_file:
                self.key_file_path = new_key_file
            
            self.key = self._load_or_generate_key()
            self.aesgcm = AESGCM(self.key)
            
            logger.info("Successfully rotated encryption key")
            return True
            
        except Exception as e:
            logger.error(f"Key rotation failed: {str(e)}")
            return False


class EncryptedDataProcessor:
    """
    Processes encrypted data from Android app
    """
    
    def __init__(self, crypto_utils: ServerCryptoUtils, db_path: str = "dns_analysis.db"):
        self.crypto_utils = crypto_utils
        self.db_path = db_path
        self._init_database()
    
    def _init_database(self):
        """Initialize database for storing decrypted data"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            # Create table for encrypted data tracking
            cursor.execute('''
                CREATE TABLE IF NOT EXISTS encrypted_data_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    device_id TEXT NOT NULL,
                    timestamp INTEGER NOT NULL,
                    entry_count INTEGER NOT NULL,
                    encryption_version TEXT,
                    processed_at INTEGER DEFAULT CURRENT_TIMESTAMP,
                    success BOOLEAN DEFAULT TRUE,
                    error_message TEXT
                )
            ''')
            
            conn.commit()
            conn.close()
            
        except Exception as e:
            logger.error(f"Failed to initialize encrypted data database: {str(e)}")
    
    def process_encrypted_payload(self, encrypted_payload: dict) -> dict:
        """
        Process encrypted payload from Android app
        
        Args:
            encrypted_payload: Dictionary containing encrypted data and metadata
            
        Returns:
            Processing result with status and details
        """
        try:
            # Extract metadata
            encrypted_data = encrypted_payload.get('encrypted_data')
            device_id = encrypted_payload.get('device_id', 'unknown')
            timestamp = encrypted_payload.get('timestamp', int(time.time()))
            entry_count = encrypted_payload.get('entry_count', 0)
            encryption_version = encrypted_payload.get('encryption_version', 'unknown')
            
            # Decrypt the payload
            logger.info(f"Received encrypted_data length: {len(encrypted_data)}")
            logger.info(f"First 100 chars of encrypted_data: {encrypted_data[:100]}")
            
            decrypted_data = self.crypto_utils.decrypt_payload(encrypted_data)
            
            logger.info(f"Decrypted data type: {type(decrypted_data)}")
            logger.info(f"Decrypted data: {str(decrypted_data)[:200]}...")
            
            # Extract DNS entries
            entries = decrypted_data.get('entries', [])
            processed_entries = []
            
            logger.info(f"Found {len(entries)} entries in decrypted data")
            
            for i, entry in enumerate(entries):
                try:
                    # Parse individual log entry
                    logger.info(f"Processing entry {i}: {entry[:100]}...")
                    log_entry = json.loads(entry)
                    
                    # Convert to database format
                    dns_query = {
                        'timestamp': log_entry.get('timestamp', 0),
                        'domain': log_entry.get('data', {}).get('destIp', 'unknown.domain'),
                        'app_name': log_entry.get('data', {}).get('appName', 'unknown'),
                        'source_ip': log_entry.get('data', {}).get('sourceIp', 'unknown'),
                        'dest_ip': log_entry.get('data', {}).get('destIp', 'unknown'),
                        'protocol': log_entry.get('data', {}).get('protocol', 'unknown'),
                        'size_bytes': log_entry.get('data', {}).get('sizeBytes', 0),
                        'is_risk': log_entry.get('data', {}).get('isRisk', False),
                        'device_id': log_entry.get('deviceId', 'unknown')
                    }
                    
                    # Perform server-side analysis
                    analyzed_query = self._analyze_domain(dns_query)
                    processed_entries.append(analyzed_query)
                    logger.info(f"Successfully parsed entry {i}: {analyzed_query['domain']} (risk: {analyzed_query['is_risk']})")
                    
                except Exception as e:
                    logger.warning(f"Failed to parse log entry {i}: {str(e)}")
                    continue
            
            logger.info(f"Processed {len(processed_entries)} entries successfully")
            
            # Store in database
            if processed_entries:
                self._store_dns_queries(processed_entries)
            
            # Log processing
            self._log_processing_result(
                device_id=device_id,
                timestamp=timestamp,
                entry_count=len(processed_entries),
                encryption_version=encryption_version,
                success=True
            )
            
            return {
                'status': 'success',
                'processed_entries': processed_entries,  # Return the actual list
                'device_id': device_id,
                'timestamp': timestamp,
                'encryption_version': encryption_version
            }
            
        except Exception as e:
            logger.error(f"Failed to process encrypted payload: {str(e)}")
            
            # Log processing failure
            self._log_processing_result(
                device_id=encrypted_payload.get('device_id', 'unknown'),
                timestamp=encrypted_payload.get('timestamp', int(time.time())),
                entry_count=0,
                encryption_version=encrypted_payload.get('encryption_version', 'unknown'),
                success=False,
                error_message=str(e)
            )
            
            return {
                'status': 'error',
                'message': str(e),
                'processed_entries': 0
            }
    
    def _store_dns_queries(self, dns_queries: list):
        """Store DNS queries in database"""
        try:
            conn = sqlite3.connect(self.db_path)
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
            
            # Check if columns exist, add them if they don't
            cursor.execute("PRAGMA table_info(dns_queries)")
            columns = [row[1] for row in cursor.fetchall()]
            
            # Add missing columns if needed
            if 'risk_score' not in columns:
                cursor.execute("ALTER TABLE dns_queries ADD COLUMN risk_score INTEGER DEFAULT 0")
                logger.info("Added risk_score column to dns_queries table")
            
            if 'risk_reasons' not in columns:
                cursor.execute("ALTER TABLE dns_queries ADD COLUMN risk_reasons TEXT")
                logger.info("Added risk_reasons column to dns_queries table")
            
            if 'analysis_timestamp' not in columns:
                cursor.execute("ALTER TABLE dns_queries ADD COLUMN analysis_timestamp INTEGER")
                logger.info("Added analysis_timestamp column to dns_queries table")
            
            if 'analysis_engine' not in columns:
                cursor.execute("ALTER TABLE dns_queries ADD COLUMN analysis_engine TEXT")
                logger.info("Added analysis_engine column to dns_queries table")
            
            for query in dns_queries:
                cursor.execute('''
                    INSERT INTO dns_queries 
                    (timestamp, source_ip, dest_ip, protocol, size_bytes, app_name, is_risk, device_id, domain, risk_score, risk_reasons, analysis_timestamp, analysis_engine)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ''', (
                    query['timestamp'],
                    query['source_ip'],
                    query['dest_ip'],
                    query['protocol'],
                    query['size_bytes'],
                    query['app_name'],
                    query['is_risk'],
                    query['device_id'],
                    query['domain'],
                    query.get('risk_score', 0),
                    str(query.get('risk_reasons', [])),
                    query.get('analysis_timestamp', query['timestamp']),
                    query.get('analysis_engine', 'Advanced Security Analysis')
                ))
            
            conn.commit()
            conn.close()
            
            logger.info(f"Stored {len(dns_queries)} DNS queries in database")
            
        except Exception as e:
            logger.error(f"Failed to store DNS queries: {str(e)}")
            raise
    
    def _analyze_domain(self, dns_query: dict) -> dict:
        """Perform server-side domain analysis using security tools"""
        try:
            domain = dns_query['domain']
            logger.info(f"Performing security analysis on: {domain}")
            
            # Initialize analysis results
            risk_score = 0
            is_risk = False
            risk_reasons = []
            
            domain_lower = (domain or '').strip().lower().rstrip('.')

            trusted_suffixes = {
                'google.com', 'googleusercontent.com', 'googlevideo.com', 'googleapis.com',
                'gstatic.com', 'ggpht.com', 'ytimg.com', 'youtube.com',
                'youtubei.googleapis.com', 'gvt1.com', 'gvt2.com',
                'android.com', 'googleadservices.com', 'doubleclick.net',
                'cloudflare.com', 'cloudflare-dns.com',
                'amazonaws.com', 'cloudfront.net',
                'akamai.net', 'akamaiedge.net', 'akamaitechnologies.com',
                'fastly.net', 'fastlylb.net', 'jsdelivr.net', 'unpkg.com',
                'github.com', 'githubusercontent.com',
                'microsoft.com', 'windows.net', 'live.com', 'office.com',
                'apple.com', 'icloud.com',
                'mozilla.org',
                'facebook.com', 'fbcdn.net', 'whatsapp.net', 'instagram.com',
                'netflix.com', 'nflxvideo.net', 'nflximg.net',
                'spotify.com', 'scdn.co'
            }

            trusted_exact = {
                'mtalk.google.com',
                's.youtube.com',
                'redirector.googlevideo.com'
            }

            is_legitimate = (domain_lower in trusted_exact)
            if not is_legitimate:
                for suffix in trusted_suffixes:
                    if domain_lower == suffix or domain_lower.endswith('.' + suffix):
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
                import subprocess
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
                import subprocess
                ping_result = subprocess.run(['ping', '-c', '1', '-W', '1', domain], capture_output=True, text=True, timeout=3)
                if 'bytes from' in ping_result.stdout:
                    logger.info(f"Host responds to ping: {domain}")
                else:
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
            
            # 7. Fast Flux Detection
            fastflux_result = self._detect_fastflux_dns(domain)
            risk_score += fastflux_result['risk_score']
            risk_reasons.extend(fastflux_result['reasons'])
            
            # 8. DGA Detection
            dga_result = self._detect_dga_patterns(domain)
            risk_score += dga_result['risk_score']
            risk_reasons.extend(dga_result['reasons'])
            
            # 9. HTTP Host Header Analysis
            http_result = self._analyze_http_headers(domain)
            risk_score += http_result['risk_score']
            risk_reasons.extend(http_result['reasons'])
            
            # 10. Redirect Chain Analysis
            redirect_result = self._analyze_redirect_chains(domain)
            risk_score += redirect_result['risk_score']
            risk_reasons.extend(redirect_result['reasons'])
            
            if risk_score < 0:
                risk_score = 0
            if risk_score > 100:
                risk_score = 100

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
        
        import re
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
    
    def _detect_fastflux_dns(self, domain: str) -> dict:
        """Detect Fast Flux DNS networks"""
        risk_score = 0
        reasons = []
        
        # Try to resolve domain multiple times quickly
        try:
            import subprocess
            import time
            
            ip_sets = []
            for i in range(3):
                try:
                    dig_result = subprocess.run(['dig', '+short', domain], capture_output=True, text=True, timeout=2)
                    if dig_result.returncode == 0:
                        ips = set(dig_result.stdout.strip().split('\n'))
                        ip_sets.append(ips)
                    time.sleep(0.5)
                except:
                    continue
            
            # Check if IPs change rapidly (Fast Flux indicator)
            if len(ip_sets) >= 2:
                all_ips = set()
                for ip_set in ip_sets:
                    all_ips.update(ip_set)
                
                if len(all_ips) > 3:  # Multiple different IPs
                    risk_score += 35
                    reasons.append(f"Fast Flux DNS detected: {len(all_ips)} different IPs")
        except Exception as e:
            logger.debug(f"Fast flux detection failed for {domain}: {str(e)}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _detect_dga_patterns(self, domain: str) -> dict:
        """Detect Domain Generation Algorithm (DGA) patterns"""
        risk_score = 0
        reasons = []
        
        # High entropy domains often indicate DGA
        entropy = self._calculate_entropy(domain)
        if entropy > 4.0:
            risk_score += 25
            reasons.append(f"High entropy domain (DGA indicator): {entropy:.2f}")
        
        # Check for random-looking subdomains
        if '.' in domain:
            subdomain = domain.split('.')[0]
            if len(subdomain) > 8 and entropy > 3.5:
                risk_score += 20
                reasons.append("Random-looking subdomain (DGA pattern)")
        
        # Common DGA patterns
        import re
        dga_patterns = [
            r'[a-z]{8,}\d{4,}',  # Letters + numbers
            r'\d{8,}',  # Long numeric sequences
            r'[a-f0-9]{16,}',  # Hexadecimal sequences
        ]
        
        for pattern in dga_patterns:
            if re.search(pattern, domain.lower()):
                risk_score += 30
                reasons.append(f"DGA pattern detected: {pattern}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _analyze_http_headers(self, domain: str) -> dict:
        """Analyze HTTP headers for security indicators"""
        risk_score = 0
        reasons = []
        
        try:
            import subprocess
            import json
            
            # Use curl to get headers
            curl_cmd = ['curl', '-s', '-I', '--connect-timeout', '3', f'http://{domain}']
            result = subprocess.run(curl_cmd, capture_output=True, text=True, timeout=5)
            
            if result.returncode == 0:
                headers = result.stdout.lower()
                
                # Check for suspicious headers
                suspicious_headers = [
                    'x-forwarded-for',
                    'x-real-ip',
                    'x-originating-ip',
                    'server: unknown',
                    'server: nginx/1.0.15',  # Old versions
                ]
                
                for header in suspicious_headers:
                    if header in headers:
                        risk_score += 15
                        reasons.append(f"Suspicious HTTP header: {header}")
                
                # Check for missing security headers
                security_headers = [
                    'x-frame-options',
                    'x-content-type-options',
                    'strict-transport-security',
                    'content-security-policy',
                ]
                
                missing_security = 0
                for header in security_headers:
                    if header not in headers:
                        missing_security += 1
                
                if missing_security >= 3:
                    risk_score += 20
                    reasons.append(f"Missing {missing_security} security headers")
                    
        except Exception as e:
            logger.debug(f"HTTP header analysis failed for {domain}: {str(e)}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _analyze_redirect_chains(self, domain: str) -> dict:
        """Analyze redirect chains for malicious behavior"""
        risk_score = 0
        reasons = []
        
        try:
            import subprocess
            
            # Use curl to follow redirects and count them
            curl_cmd = ['curl', '-s', '-L', '-w', '%{redirect_url}', '--connect-timeout', '3', '-o', '/dev/null', f'http://{domain}']
            result = subprocess.run(curl_cmd, capture_output=True, text=True, timeout=8)
            
            if result.returncode == 0:
                # Count redirects by checking how many times curl had to follow
                redirect_count = result.stdout.count('http')
                
                if redirect_count > 5:
                    risk_score += 30
                    reasons.append(f"Excessive redirects: {redirect_count}")
                elif redirect_count > 2:
                    risk_score += 15
                    reasons.append(f"Multiple redirects: {redirect_count}")
                
                # Check for suspicious redirect domains
                if 'bit.ly' in result.stdout or 'tinyurl.com' in result.stdout:
                    risk_score += 20
                    reasons.append("URL shortener in redirect chain")
                    
        except Exception as e:
            logger.debug(f"Redirect analysis failed for {domain}: {str(e)}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _analyze_whois(self, whois_output: str, domain: str) -> dict:
        """Analyze WHOIS output for security indicators"""
        risk_score = 0
        reasons = []
        domain_age_days = None  # Initialize here to fix scope issue
        
        # Check domain age
        if 'Creation Date:' in whois_output or 'created:' in whois_output:
            # Extract creation date (simplified)
            import re
            date_patterns = [
                r'Creation Date:\s*(\d{4}-\d{2}-\d{2})',
                r'created:\s*(\d{4}-\d{2}-\d{2})',
                r'Creation Date:\s*(\d{2}/\d{2}/\d{4})'
            ]
            
            for pattern in date_patterns:
                match = re.search(pattern, whois_output)
                if match:
                    try:
                        from datetime import datetime
                        creation_date = datetime.strptime(match.group(1), '%Y-%m-%d' if '-' in match.group(1) else '%m/%d/%Y')
                        domain_age_days = (datetime.now() - creation_date).days
                        break
                    except:
                        continue
            
            if domain_age_days is not None:
                if domain_age_days < 7:  # Very new domain
                    risk_score += 40
                    reasons.append(f"Very new domain: {domain_age_days} days old")
                elif domain_age_days < 30:  # New domain
                    risk_score += 20
                    reasons.append(f"New domain: {domain_age_days} days old")
                elif domain_age_days > 365 * 5:  # Very old domain
                    risk_score += 10
                    reasons.append(f"Very old domain: {domain_age_days} days old")
        
        # Check for privacy protection
        privacy_indicators = ['Privacy protection', 'Whois Privacy Protection', 'Domains By Proxy']
        for indicator in privacy_indicators:
            if indicator.lower() in whois_output.lower():
                risk_score += 30
                reasons.append("Domain uses privacy protection")
                break
        
        # Check for suspicious registrars
        suspicious_registrars = ['namecheap', 'godaddy', 'register.com', 'enom']
        for registrar in suspicious_registrars:
            if registrar.lower() in whois_output.lower():
                risk_score += 5
                reasons.append(f"Registered with {registrar}")
                break
        
        return {'risk_score': risk_score, 'reasons': reasons, 'domain_age_days': domain_age_days}
    
    def _analyze_dig(self, dig_output: str, domain: str) -> dict:
        """Analyze DIG output for security indicators"""
        risk_score = 0
        reasons = []
        
        lines = dig_output.strip().split('\n')
        if not lines or not lines[0].strip():
            risk_score += 20
            reasons.append("Domain does not resolve")
        else:
            # Check if resolving to suspicious IPs
            for line in lines:
                ip = line.strip()
                if ip and self._is_suspicious_ip(ip):
                    risk_score += 25
                    reasons.append(f"Resolves to suspicious IP: {ip}")
        
        return {'risk_score': risk_score, 'reasons': reasons, 'resolves_to': lines}
    
    def _analyze_network(self, domain: str) -> dict:
        """Network analysis using available tools"""
        import subprocess
        risk_score = 0
        reasons = []
        
        # Use ping instead of raw sockets (no root required)
        try:
            ping_result = subprocess.run(['ping', '-c', '1', '-W', '2', domain], capture_output=True, text=True, timeout=5)
            if 'bytes from' in ping_result.stdout:
                reasons.append("Host responds to ping")
            else:
                risk_score += 15
                reasons.append("Host does not respond to ping")
        except subprocess.TimeoutExpired:
            risk_score += 10
            reasons.append("Host ping timeout")
        except Exception as e:
            risk_score += 5
            reasons.append(f"Network analysis failed: {str(e)[:50]}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _run_custom_analysis(self, domain: str) -> dict:
        """Run custom security analysis scripts"""
        risk_score = 0
        reasons = []
        
        # Check for common attack patterns in domain
        attack_patterns = [
            r'.*\.tk$',  # Free TLD often abused
            r'.*\.ml$',  # Free TLD often abused
            r'[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}',  # IP addresses
            r'[a-f0-9]{32,}',  # Hexadecimal domains
            r'.*\.onion$',  # Tor domains
        ]
        
        import re
        for pattern in attack_patterns:
            if re.match(pattern, domain.lower()):
                risk_score += 35
                reasons.append(f"Matches attack pattern: {pattern}")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _basic_pattern_analysis(self, domain: str) -> dict:
        """Basic pattern analysis as fallback"""
        risk_score = 0
        reasons = []
        
        # Length analysis
        if len(domain) > 50:
            risk_score += 15
            reasons.append("Unusually long domain")
        elif len(domain) < 4:
            risk_score += 10
            reasons.append("Very short domain")
        
        # Character entropy
        entropy = self._calculate_entropy(domain)
        if entropy > 3.5:
            risk_score += 20
            reasons.append(f"High entropy domain: {entropy:.2f}")
        
        # Subdomain count
        if domain.count('.') > 3:
            risk_score += 15
            reasons.append("Excessive subdomains")
        
        return {'risk_score': risk_score, 'reasons': reasons}
    
    def _is_suspicious_ip(self, ip: str) -> bool:
        """Check if IP address is suspicious"""
        try:
            parts = ip.split('.')
            if len(parts) != 4:
                return True
            
            # Check for private IP ranges
            if (parts[0] in ['10', '172', '192'] or 
                (parts[0] == '172' and 16 <= int(parts[1]) <= 31) or
                (parts[0] == '192' and parts[1] == '168')):
                return False
            
            # Check for known suspicious ranges
            suspicious_ranges = [
                (0, 0, 0, 0, 0, 255, 255, 255),  # 0.0.0.0/8
                (169, 254, 0, 0, 169, 254, 255, 255),  # 169.254.0.0/16
                (192, 0, 2, 0, 192, 0, 2, 255),  # 192.0.2.0/24
            ]
            
            for start in suspicious_ranges:
                if (int(parts[0]) >= start[0] and int(parts[0]) <= start[4] and
                    int(parts[1]) >= start[1] and int(parts[1]) <= start[5] and
                    int(parts[2]) >= start[2] and int(parts[2]) <= start[6] and
                    int(parts[3]) >= start[3] and int(parts[3]) <= start[7]):
                    return True
            
            return False
        except:
            return True
    
    def _calculate_entropy(self, s: str) -> float:
        """Calculate Shannon entropy of string"""
        char_set = set(s.lower())
        if not char_set:
            return 0
        entropy = 0
        for char in char_set:
            p = s.lower().count(char) / len(s)
            if p > 0:
                import math
                entropy -= p * math.log2(p)
        return entropy
    
    def _log_processing_result(self, device_id: str, timestamp: int, entry_count: int,
                               encryption_version: str, success: bool, error_message: str = None):
        """Log processing result for audit trail"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            cursor.execute('''
                INSERT INTO encrypted_data_log 
                (device_id, timestamp, entry_count, encryption_version, success, error_message)
                VALUES (?, ?, ?, ?, ?, ?)
            ''', (device_id, timestamp, entry_count, encryption_version, success, error_message))
            
            conn.commit()
            conn.close()
            
        except Exception as e:
            logger.error(f"Failed to log processing result: {str(e)}")
    
    def get_processing_stats(self) -> dict:
        """Get statistics about encrypted data processing"""
        try:
            conn = sqlite3.connect(self.db_path)
            cursor = conn.cursor()
            
            # Get total stats
            cursor.execute('''
                SELECT 
                    COUNT(*) as total_requests,
                    SUM(entry_count) as total_entries,
                    COUNT(CASE WHEN success = 1 THEN 1 END) as successful_requests,
                    COUNT(CASE WHEN success = 0 THEN 1 END) as failed_requests,
                    MAX(timestamp) as last_request
                FROM encrypted_data_log
            ''')
            
            stats = cursor.fetchone()
            
            # Get recent activity (last 24 hours)
            cursor.execute('''
                SELECT COUNT(*) as recent_requests
                FROM encrypted_data_log
                WHERE timestamp > ?
            ''', (int(time.time()) - 86400,))
            
            recent_stats = cursor.fetchone()
            
            conn.close()
            
            return {
                'total_requests': stats[0] or 0,
                'total_entries': stats[1] or 0,
                'successful_requests': stats[2] or 0,
                'failed_requests': stats[3] or 0,
                'last_request': stats[4] or 0,
                'recent_requests_24h': recent_stats[0] or 0,
                'success_rate': (stats[2] / stats[0] * 100) if stats[0] > 0 else 0
            }
            
        except Exception as e:
            logger.error(f"Failed to get processing stats: {str(e)}")
            return {}
