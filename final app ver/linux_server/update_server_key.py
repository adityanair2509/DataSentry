#!/usr/bin/env python3
import base64

# The key from Android app
android_key_b64 = "AWnheBkwOK67kUvXgDFKt6rPmQNFknFwoMrL2Tgo1YI="

# Decode and save to server key file
key_bytes = base64.b64decode(android_key_b64)

with open('crypto_keys.dat', 'wb') as f:
    f.write(key_bytes)

import os
os.chmod('crypto_keys.dat', 0o600)

print(f"Server key updated with Android key: {android_key_b64}")
print(f"Key length: {len(key_bytes)} bytes")
