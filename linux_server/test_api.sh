#!/bin/bash
echo "🧪 Testing DataSentry API Endpoints..."
echo "===================================="

KALI_IP="192.168.29.205"
PORT="8081"

echo "🌐 Testing dashboard API..."
curl -k -s "https://$KALI_IP:$PORT/api/dashboard/data" | python3 -m json.tool 2>/dev/null || echo "❌ Dashboard API failed"

echo ""
echo "🌐 Testing server status..."
if curl -k -s "https://$KALI_IP:$PORT/api/dashboard/data" > /dev/null; then
    echo "✅ Server is responding"
else
    echo "❌ Server not responding"
fi

echo ""
echo "📱 Use this URL in Android app:"
echo "   SERVER_URL = \"https://$KALI_IP:$PORT/api/dns-data\""
echo ""
echo "🌐 Dashboard URL:"
echo "   https://$KALI_IP:$PORT"
