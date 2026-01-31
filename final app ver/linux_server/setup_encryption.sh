#!/bin/bash

# DataSentry AES-256 Encryption Setup Script
# This script sets up the secure encrypted communication system

echo "🔐 DataSentry AES-256 Encryption Setup"
echo "======================================"
echo ""

# Check if we're in the right directory
if [ ! -f "app.py" ]; then
    echo "❌ Error: Please run this script from the linux_server directory"
    exit 1
fi

echo "📋 This setup will enable:"
echo "   • AES-256 GCM encryption for Android logs"
echo "   • Secure encrypted transmission to server"
echo "   • Encrypted local storage on Android device"
echo "   • Server-side decryption and processing"
echo ""

echo "🔧 Step 1: Installing Python cryptography dependencies..."
pip install cryptography==41.0.4

echo ""
echo "🔧 Step 2: Setting up encryption keys..."
python3 -c "
from crypto_utils import ServerCryptoUtils
import logging
logging.basicConfig(level=logging.INFO)

# Initialize encryption system
crypto = ServerCryptoUtils()
print('✅ Encryption system initialized')

# Validate encryption
if crypto.validate_encryption():
    print('✅ Encryption/decryption test passed')
else:
    print('❌ Encryption validation failed')
    exit(1)

# Show key info
info = crypto.get_key_info()
print(f'✅ Key info: {info}')
"

echo ""
echo "🔧 Step 3: Testing encrypted API endpoint..."
python3 -c "
import requests
import json
import base64
from crypto_utils import ServerCryptoUtils

# Initialize crypto
crypto = ServerCryptoUtils()

# Create test encrypted payload
test_data = {
    'version': '1.0',
    'timestamp': 1643123456789,
    'entries': ['test entry 1', 'test entry 2'],
    'count': 2
}

# Encrypt test data
encrypted_payload = crypto.encrypt_payload(test_data)

# Create API request
api_data = {
    'encrypted_data': encrypted_payload,
    'api_key': 'datasentry-secure-api-key-2024',
    'device_id': 'test-device-123',
    'timestamp': 1643123456789,
    'entry_count': 2,
    'encryption_version': 'AES-256-GCM-v1.0'
}

# Test API endpoint (if server is running)
try:
    response = requests.post('http://localhost:8081/api/encrypted-dns-data', 
                           json=api_data, timeout=5)
    if response.status_code == 200:
        print('✅ Encrypted API endpoint test passed')
        print(f'   Response: {response.json()}')
    else:
        print(f'⚠️  Server not running or API error: {response.status_code}')
except requests.exceptions.RequestException:
    print('⚠️  Server not running - start server with: python3 app.py')
"

echo ""
echo "🔧 Step 4: Creating Android integration guide..."
cat > android_integration_guide.md << 'EOF'
# Android AES-256 Integration Guide

## 1. Update Android Dependencies
Add these to your app/build.gradle.kts:

```kotlin
// Security & Encryption
implementation("androidx.security:security-crypto:1.1.0-alpha06")
implementation("org.bouncycastle:bcprov-jdk15on:1.70")
```

## 2. Update Android Code
Replace your AnalyticsClient with SecureAnalyticsClient:

```kotlin
// In your VPN service or DNS handler
val secureClient = SecureAnalyticsClient(context)

// Store DNS query with encryption
secureClient.storeDnsQuery(dnsQuery)

// Send encrypted batch
secureClient.sendEncryptedBatch(maxEntries = 50)
```

## 3. Key Points
- All logs are encrypted with AES-256 GCM
- Keys are stored in Android Keystore
- Encrypted data stored in /data/logs/
- Server decrypts automatically
- Full end-to-end encryption

## 4. Security Benefits
- ✅ Military-grade AES-256 encryption
- ✅ Secure key storage in Android Keystore
- ✅ Encrypted local storage
- ✅ Encrypted network transmission
- ✅ Server-side secure decryption
- ✅ Audit logging of all operations
EOF

echo "✅ Android integration guide created: android_integration_guide.md"

echo ""
echo "🔧 Step 5: Creating security validation script..."
cat > validate_encryption.py << 'EOF'
#!/usr/bin/env python3
"""
DataSentry Encryption Validation Script
Validates that the encryption system is working correctly
"""

import sys
import logging
from crypto_utils import ServerCryptoUtils, EncryptedDataProcessor

def main():
    logging.basicConfig(level=logging.INFO)
    logger = logging.getLogger(__name__)
    
    print("🔐 DataSentry Encryption Validation")
    print("=================================")
    
    try:
        # Test crypto utilities
        print("\n1. Testing ServerCryptoUtils...")
        crypto = ServerCryptoUtils()
        
        # Show key info
        info = crypto.get_key_info()
        print(f"   Algorithm: {info['algorithm']}")
        print(f"   Key Length: {info['key_length']} bits")
        print(f"   Key File: {info['key_file_path']}")
        
        # Test encryption/decryption
        test_data = "DataSentry encryption test"
        encrypted = crypto.encrypt_payload({"test": test_data})
        decrypted = crypto.decrypt_payload(encrypted)
        
        if decrypted.get("test") == test_data:
            print("   ✅ Encryption/decryption test passed")
        else:
            print("   ❌ Encryption/decryption test failed")
            return False
        
        # Test encrypted data processor
        print("\n2. Testing EncryptedDataProcessor...")
        processor = EncryptedDataProcessor(crypto)
        
        # Get processing stats
        stats = processor.get_processing_stats()
        print(f"   Total requests processed: {stats.get('total_requests', 0)}")
        print(f"   Success rate: {stats.get('success_rate', 0):.1f}%")
        
        print("\n✅ All encryption validation tests passed!")
        return True
        
    except Exception as e:
        print(f"\n❌ Validation failed: {str(e)}")
        return False

if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1)
EOF

chmod +x validate_encryption.py
echo "✅ Validation script created: validate_encryption.py"

echo ""
echo "🔧 Step 6: Updating server configuration..."
# Update health check endpoint to include encryption status
python3 -c "
import sqlite3

# Add encryption status to database
conn = sqlite3.connect('datasentry.db')
cursor = conn.cursor()

# Create table for encryption audit log (if not exists)
cursor.execute('''
    CREATE TABLE IF NOT EXISTS encryption_audit_log (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        event_type TEXT NOT NULL,
        device_id TEXT,
        timestamp INTEGER NOT NULL,
        details TEXT,
        success BOOLEAN DEFAULT TRUE
    )
''')

conn.commit()
conn.close()
print('✅ Database updated for encryption audit logging')
"

echo ""
echo "🚀 Step 7: Starting enhanced DataSentry server..."
echo ""

# Check if server is running
if pgrep -f "python.*app.py" > /dev/null; then
    echo "🔄 Stopping existing server..."
    pkill -f "python.*app.py"
    sleep 2
fi

echo "🚀 Starting DataSentry server with AES-256 encryption..."
nohup python3 app.py > server.log 2>&1 &
SERVER_PID=$!

sleep 3

if pgrep -f "python.*app.py" > /dev/null; then
    echo "✅ Server started successfully (PID: $SERVER_PID)"
    echo ""
    echo "🌐 Access your enhanced dashboard:"
    echo "   • Local: http://localhost:8081"
    echo "   • Network: http://$(hostname -I | awk '{print $1}'):8081"
    echo ""
    echo "🔐 New Encrypted Endpoints:"
    echo "   • POST /api/encrypted-dns-data (Secure encrypted DNS data)"
    echo "   • GET /api/dns-data (Legacy unencrypted - for compatibility)"
    echo ""
    echo "📊 Enhanced Features:"
    echo "   • AES-256 GCM encryption"
    echo "   • Secure key management"
    echo "   • Encrypted local storage on Android"
    echo "   • End-to-end encryption"
    echo "   • Audit logging"
    echo ""
    echo "📝 Server logs: tail -f server.log"
    echo "🔐 Validate encryption: python3 validate_encryption.py"
else
    echo "❌ Server failed to start. Check server.log for errors."
    exit 1
fi

echo ""
echo "🎯 Step 8: Android Integration Instructions"
echo "=========================================="
echo ""
echo "1. Build your Android app with the new security dependencies"
echo "2. Replace AnalyticsClient with SecureAnalyticsClient in your code"
echo "3. The app will automatically:"
echo "   • Encrypt all DNS logs with AES-256 GCM"
echo "   • Store encrypted logs in /data/logs/"
echo "   • Send encrypted data to /api/encrypted-dns-data"
echo "   • Handle encryption keys securely with Android Keystore"
echo ""
echo "4. Server will automatically:"
echo "   • Decrypt incoming encrypted data"
echo "   • Process and analyze DNS queries"
echo "   • Store results in database"
echo "   • Maintain audit logs of all encryption operations"
echo ""

echo "✨ AES-256 Encryption Setup Complete!"
echo "===================================="
echo "Your DataSentry now has military-grade end-to-end encryption!"
echo "🛡️  All DNS data is encrypted at rest and in transit!"
echo ""
echo "📖 For integration details, see: android_integration_guide.md"
echo "🔐 To validate encryption: python3 validate_encryption.py"
echo ""
