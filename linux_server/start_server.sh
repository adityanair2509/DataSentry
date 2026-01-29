#!/bin/bash
cd "$(dirname "$0")"
echo "🚀 Starting DataSentry Server..."
echo "📁 Directory: $(pwd)"

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo "❌ Virtual environment not found. Run ./quick_setup.sh first"
    exit 1
fi

# Activate virtual environment
source venv/bin/activate

# Check if required packages are available
python3 -c "import flask" 2>/dev/null || {
    echo "❌ Flask not available. Please check installation."
    exit 1
}

echo "🌐 Dashboard will be available at: https://localhost:8081"
echo "🔑 API Key: datasentry-quick-api-key-12345"
echo "📱 Find your IP with: hostname -I"
echo ""

# Start server
python3 app.py
