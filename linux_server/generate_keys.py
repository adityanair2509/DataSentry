#!/usr/bin/env python3
"""
DataSentry Key Sharing Utility (Standalone)
Generates and shares encryption keys between server and Android
"""

import base64
import json
import os
import secrets

def generate_shared_key():
    """Generate a shared key for both server and Android"""
    # Generate 32-byte (256-bit) key
    key = secrets.token_bytes(32)
    
    # Encode for sharing
    key_b64 = base64.b64encode(key).decode('utf-8')
    
    return {
        'key': key_b64,
        'key_bytes': key,
        'created_at': '2026-01-31T02:15:00Z',
        'algorithm': 'AES-256-GCM',
        'iv_length': 12,
        'tag_length': 16
    }

def save_android_key_info(key_info, filename='android_key.json'):
    """Save key info for Android app"""
    android_config = {
        'encryption_key': key_info['key'],
        'algorithm': key_info['algorithm'],
        'iv_length': key_info['iv_length'],
        'tag_length': key_info['tag_length'],
        'created_at': key_info['created_at'],
        'server_url': 'https://thomasine-hyperdulic-gilda.ngrok-free.dev',
        'api_endpoint': '/api/encrypted-dns-data',
        'api_key': 'datasentry-secure-api-key-2024'
    }
    
    with open(filename, 'w') as f:
        json.dump(android_config, f, indent=2)
    
    print(f"Android key config saved to: {filename}")
    return android_config

def update_server_key(key_bytes):
    """Update server to use the shared key"""
    key_file = 'crypto_keys.dat'
    
    # Save the shared key
    with open(key_file, 'wb') as f:
        f.write(key_bytes)
    
    # Set secure permissions
    os.chmod(key_file, 0o600)
    
    print(f"Server key updated: {key_file}")
    return key_file

def main():
    print("DataSentry Key Synchronization")
    print("=" * 40)
    
    # Generate shared key
    key_info = generate_shared_key()
    
    print(f"Generated {key_info['algorithm']} key")
    print(f"Created at: {key_info['created_at']}")
    print(f"Key length: {len(key_info['key_bytes'])} bytes")
    
    # Save for Android
    android_config = save_android_key_info(key_info)
    
    # Update server
    server_key_file = update_server_key(key_info['key_bytes'])
    
    print("\nAndroid Integration:")
    print(f"   1. Copy this key: {key_info['key']}")
    print(f"   2. Replace 'YOUR_SHARED_KEY_HERE' in CryptoUtils.kt")
    print(f"   3. Server URL: {android_config['server_url']}")
    
    print("\nServer Integration:")
    print(f"   1. Server key updated: {server_key_file}")
    print(f"   2. Restart server: python3 app.py")
    print(f"   3. Test with: curl -X POST {android_config['server_url']}{android_config['api_endpoint']}")
    
    print("\nShared Key (Base64):")
    print(key_info['key'])
    
    print("\nKey synchronization complete!")
    print("Both server and Android now use the same encryption key!")

if __name__ == "__main__":
    main()
