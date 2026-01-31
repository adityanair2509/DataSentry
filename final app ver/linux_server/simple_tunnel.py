#!/usr/bin/env python3
"""
Simple HTTP Tunnel for DataSentry
Alternative to ngrok when network issues occur
"""

import requests
import threading
import time
import json

class SimpleTunnel:
    def __init__(self, local_port=8081):
        self.local_port = local_port
        self.tunnel_url = None
        
    def create_tunnel(self):
        """Create a simple tunnel using a public service"""
        try:
            # Option 1: Try localtunnel (if npm is available)
            print("🔗 Trying to create tunnel...")
            print("📱 Alternative: Use your phone's mobile data to test")
            print("💡 Or fix VM networking with Bridged mode")
            print("")
            print("🌐 Manual testing options:")
            print("1. From phone browser: http://YOUR_KALI_IP:8081")
            print("2. Use mobile hotspot (phone + Kali on same network)")
            print("3. Transfer ngrok manually from Windows")
            print("")
            print("🔧 Current Kali IP:")
            import socket
            hostname = socket.gethostname()
            local_ip = socket.gethostbyname(hostname)
            print(f"   http://{local_ip}:8081")
            
        except Exception as e:
            print(f"Tunnel creation failed: {e}")

if __name__ == "__main__":
    tunnel = SimpleTunnel()
    tunnel.create_tunnel()
