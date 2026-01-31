#!/bin/bash

echo "🚀 Alternative Tunnel Services for DataSentry"
echo "=============================================="

echo "Since ngrok was banned, try these alternatives:"
echo ""

echo "1️⃣  LocalTunnel (requires npm/nodejs)"
echo "   npm install -g localtunnel"
echo "   lt --port 8081"
echo ""

echo "2️⃣  Cloudflare Tunnel (free and reliable)"
echo "   # Install cloudflared"
echo "   wget https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64.deb"
echo "   sudo dpkg -i cloudflared-linux-amd64.deb"
echo "   cloudflared tunnel --url http://localhost:8081"
echo ""

echo "3️⃣  Serveo (no installation required)"
echo "   ssh -R 80:localhost:8081 serveo.net"
echo ""

echo "4️⃣  Restart ngrok (often gets new URL)"
echo "   ngrok http 8081"
echo ""

echo "5️⃣  Direct Connection (if VM network fixed)"
echo "   # Update Android app with your Kali IP"
echo "   hostname -I  # Get your IP"
echo ""

echo "🔧 Quick Test - Try new ngrok tunnel:"
echo "ngrok http 8081"
