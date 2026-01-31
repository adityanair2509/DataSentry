#!/bin/bash

echo "🚀 DataSentry ngrok Setup"
echo "========================="

# Check if ngrok already exists
if [ -f "ngrok" ]; then
    echo "✅ ngrok already exists"
else
    echo "📥 Downloading ngrok..."
    wget https://bin.equinox.io/c/bNyj1mQVY4c/ngrok-v3-stable-linux-amd64.tgz
    
    echo "📦 Extracting ngrok..."
    tar -xzf ngrok-v3-stable-linux-amd64.tgz
    
    echo "🔧 Setting permissions..."
    chmod +x ngrok
    
    echo "🧹 Cleaning up..."
    rm ngrok-v3-stable-linux-amd64.tgz
fi

echo ""
echo "🌐 Starting ngrok tunnel..."
echo "=========================="
echo "This will expose your DataSentry server to the internet"
echo "Your ngrok URL will be shown below"
echo ""

# Check if server is running
if ! pgrep -f "python3 app.py" > /dev/null; then
    echo "⚠️  DataSentry server is not running!"
    echo "💡 Start it first with: ./start_server.sh"
    echo ""
    echo "Start server now? (y/n)"
    read -r response
    if [[ "$response" =~ ^([yY][eE][sS]|[yY])$ ]]; then
        echo "🚀 Starting DataSentry server..."
        ./start_server.sh &
        sleep 3
    fi
fi

echo "🔗 Starting ngrok on port 8081..."
echo "=================================="
echo "Copy the https://....ngrok.io URL below"
echo "Update your Android app with this URL"
echo ""

# Start ngrok
./ngrok http 8081
