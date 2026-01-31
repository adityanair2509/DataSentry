#!/bin/bash

# DataSentry Dashboard Deployment Script
# Run this on your LINUX server to deploy the enhanced dashboard

echo "🚀 Deploying DataSentry Enhanced Dashboard..."

# Check if we're in the right directory
if [ ! -f "app.py" ]; then
    echo "❌ Error: Please run this script from the linux_server directory"
    exit 1
fi

# Create backup of original files
echo "📦 Creating backup..."
cp app.py app.py.backup 2>/dev/null || true

# Copy the enhanced dashboard (if it doesn't exist)
if [ ! -f "dashboard.html" ]; then
    echo "⚠️  dashboard.html not found. Please copy it from your Windows machine first!"
    echo "   Copy this file to your Linux server: c:\\Users\\tanay\\Downloads\\DataSentry-main\\DataSentry-main\\linux_server\\dashboard.html"
    exit 1
fi

# Check if the safety assessment endpoint exists
if ! grep -q "safety-assessment" app.py; then
    echo "❌ Error: app.py doesn't have the safety assessment endpoint"
    echo "   Please copy the updated app.py from your Windows machine"
    exit 1
fi

# Restart the server
echo "🔄 Restarting DataSentry server..."
pkill -f "python.*app.py" 2>/dev/null || true
sleep 2

# Start the server in background
nohup python3 app.py > server.log 2>&1 &
SERVER_PID=$!

echo "✅ Dashboard deployed successfully!"
echo ""
echo "🌐 Access your enhanced dashboard at:"
echo "   Local:    http://localhost:8081"
echo "   Network:  http://$(hostname -I | awk '{print $1}'):8081"
echo ""
echo "🔍 New Features:"
echo "   • Real-time safety status (SAFE/CAUTION/DANGER)"
echo "   • Critical threat alerts"
echo "   • Risky application monitoring"
echo "   • Actionable security recommendations"
echo "   • Auto-refresh every 30 seconds"
echo ""
echo "📊 Safety Assessment API:"
echo "   GET /api/safety-assessment"
echo ""
echo "📝 Server PID: $SERVER_PID"
echo "📝 Logs: tail -f server.log"
echo ""
echo "🎯 Your DataSentry system is now fully operational!"
