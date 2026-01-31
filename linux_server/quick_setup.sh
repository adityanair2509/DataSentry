#!/bin/bash

# DataSentry Quick Setup Script - Fast Version
# Skips system updates, installs only essentials

echo "🚀 DataSentry Quick Setup (Fast Version)"
echo "======================================="

# Check if running as root
if [[ $EUID -eq 0 ]]; then
   echo "❌ This script should not be run as root for security reasons."
   echo "   Run as a regular user with sudo privileges."
   exit 1
fi

# Set working directory
WORK_DIR="/home/kali/Desktop/DataSentry-main/linux_server"
cd "$WORK_DIR" || {
    echo "❌ Cannot find DataSentry directory at $WORK_DIR"
    echo "   Please ensure the project is in the correct location"
    exit 1
}

echo "📁 Working directory: $WORK_DIR"

# Install only essential Python packages (skip system updates)
echo "🐍 Installing Python packages..."
python3 -m venv venv 2>/dev/null || {
    echo "⚠️  Virtual environment already exists, recreating..."
    rm -rf venv
    python3 -m venv venv
}

source venv/bin/activate
pip install --upgrade pip

# Install core dependencies only
echo "📦 Installing core dependencies..."
pip install Flask==2.3.3 Flask-CORS==4.0.0 requests==2.31.0 python-whois==0.8.0 dnspython==2.4.2 2>/dev/null
pip install scapy==2.5.0 psutil==5.9.5 schedule==1.2.0 python-dateutil==2.8.2 2>/dev/null

# Create essential directories
echo "📁 Creating directories..."
mkdir -p /var/log/datasentry 2>/dev/null || sudo mkdir -p /var/log/datasentry
mkdir -p /tmp/datasentry
mkdir -p data

# Set permissions (only if directories exist)
[ -d "/var/log/datasentry" ] && sudo chmod 755 /var/log/datasentry 2>/dev/null
chmod 755 /tmp/datasentry 2>/dev/null
chmod 755 data 2>/dev/null

# Generate simple SSL certificate
echo "🔐 Generating SSL certificate..."
if [ ! -f "cert.pem" ] || [ ! -f "key.pem" ]; then
    openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 365 -nodes \
        -subj "/C=US/ST=State/L=City/O=DataSentry/OU=Analysis/CN=localhost" 2>/dev/null
    echo "✅ SSL certificate generated"
else
    echo "✅ SSL certificate already exists"
fi

# Create simple configuration
echo "⚙️ Creating configuration..."
cat > config.py <<'EOF'
# DataSentry Server Configuration - Quick Setup
import os

class Config:
    # Server settings
    SECRET_KEY = 'datasentry-quick-setup-key-change-in-production'
    DEBUG = False
    
    # SSL settings
    SSL_CERT = 'cert.pem'
    SSL_KEY = 'key.pem'
    
    # Database settings
    DATABASE_PATH = 'datasentry.db'
    
    # API settings - CHANGE THIS!
    API_KEY = 'datasentry-quick-api-key-12345'
    
    # Analysis settings
    MAX_DOMAINS_PER_DEVICE = 1000
    ANALYSIS_INTERVAL = 300  # 5 minutes
    
    # Network settings
    BIND_HOST = '0.0.0.0'
    PORT = 8080
    
    # Logging settings
    LOG_LEVEL = 'INFO'
    LOG_FILE = '/var/log/datasentry/server.log'
    
    # Tool paths (Kali defaults)
    TCPDUMP_PATH = '/usr/bin/tcpdump'
    TSHARK_PATH = '/usr/bin/tshark'
    WHOIS_PATH = '/usr/bin/whois'
    DIG_PATH = '/usr/bin/dig'
EOF

# Create database initialization script
echo "🗄️ Creating database..."
cat > init_db.py <<'EOF'
#!/usr/bin/env python3
import sqlite3
import os

def init_database():
    db_path = 'datasentry.db'
    
    # Remove existing database for clean start
    if os.path.exists(db_path):
        os.remove(db_path)
        print("🗑️  Removed existing database")
    
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()
    
    # DNS Queries table
    cursor.execute('''
        CREATE TABLE dns_queries (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            timestamp INTEGER,
            domain TEXT,
            app_name TEXT,
            source_ip TEXT,
            dest_ip TEXT,
            protocol TEXT,
            size_bytes INTEGER,
            is_risk BOOLEAN,
            device_id TEXT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    # Analysis Results table
    cursor.execute('''
        CREATE TABLE analysis_results (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            domain TEXT,
            analysis_type TEXT,
            result TEXT,
            risk_score INTEGER,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    # Threat Detections table
    cursor.execute('''
        CREATE TABLE threat_detections (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            domain TEXT,
            threat_type TEXT,
            risk_score INTEGER,
            details TEXT,
            detected_at DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    conn.commit()
    conn.close()
    print("✅ Database initialized successfully")

if __name__ == '__main__':
    init_database()
EOF

# Initialize database
python3 init_db.py
rm init_db.py

# Create simple startup script
echo "🚀 Creating startup script..."
cat > start_server.sh <<'EOF'
#!/bin/bash
cd "$(dirname "$0")"
echo "🚀 Starting DataSentry Server..."
echo "📁 Directory: $(pwd)"
echo "🌐 Dashboard will be available at: https://localhost:8080"
echo "🔑 API Key: datasentry-quick-api-key-12345"
echo ""

# Activate virtual environment
source venv/bin/activate

# Start server
python3 app.py
EOF
chmod +x start_server.sh

# Create test script
echo "🧪 Creating test script..."
cat > test_server.sh <<'EOF'
#!/bin/bash
echo "🧪 Testing DataSentry Server..."
echo ""

# Test if server is running
if curl -k -s https://localhost:8080/api/dashboard/data > /dev/null; then
    echo "✅ Server is running and responding"
    
    # Test API endpoint
    echo "📊 Testing dashboard API..."
    curl -k -s https://localhost:8080/api/dashboard/data | python3 -m json.tool 2>/dev/null || echo "❌ API response error"
    
    echo ""
    echo "🌐 Open in browser: https://localhost:8080"
    echo "🔑 API Key for Android app: datasentry-quick-api-key-12345"
    echo "📱 Update SERVER_URL in AnalyticsClient.kt with your Kali IP"
else
    echo "❌ Server is not running"
    echo "💡 Start with: ./start_server.sh"
fi
EOF
chmod +x test_server.sh

# Create Android app configuration helper
echo "📱 Creating Android configuration helper..."
cat > android_config.txt <<'EOF'
DataSentry Android App Configuration
===================================

1. Update AnalyticsClient.kt in your Android project:

   private const val SERVER_URL = "https://YOUR_KALI_IP:8080/api/dns-data"
   private const val API_KEY = "datasentry-quick-api-key-12345"

2. Find your Kali IP address:
   hostname -I
   # Example: 192.168.1.100

3. Update SERVER_URL with your actual Kali IP:
   private const val SERVER_URL = "https://192.168.1.100:8080/api/dns-data"

4. Build and install the Android app

5. Start DataSentry server:
   ./start_server.sh

6. Test connection:
   ./test_server.sh

7. Access dashboard:
   https://localhost:8080
EOF

# Check essential tools
echo "🔧 Checking essential tools..."
missing_tools=()

for tool in python3 pip openssl sqlite3 curl; do
    if ! command -v "$tool" &> /dev/null; then
        missing_tools+=("$tool")
    fi
done

if [ ${#missing_tools[@]} -gt 0 ]; then
    echo ""
    echo "⚠️  Missing essential tools: ${missing_tools[*]}"
    echo "💡 Install with: sudo apt install ${missing_tools[*]}"
    echo ""
else
    echo "✅ All essential tools are available"
fi

# Check optional security tools
echo ""
echo "🛡️  Checking optional security tools..."
optional_tools=(tcpdump tshark whois dig)
available_tools=()
missing_optional=()

for tool in "${optional_tools[@]}"; do
    if command -v "$tool" &> /dev/null; then
        available_tools+=("$tool")
    else
        missing_optional+=("$tool")
    fi
done

if [ ${#available_tools[@]} -gt 0 ]; then
    echo "✅ Available security tools: ${available_tools[*]}"
fi

if [ ${#missing_optional[@]} -gt 0 ]; then
    echo "⚠️  Missing security tools: ${missing_optional[*]}"
    echo "💡 Install later with: sudo apt install ${missing_optional[*]}"
    echo "   (Server will work without them, but with limited analysis)"
fi

# Create status script
echo "📊 Creating status script..."
cat > status.sh <<'EOF'
#!/bin/bash
echo "📊 DataSentry Server Status"
echo "=========================="

# Check if server process is running
if pgrep -f "python3 app.py" > /dev/null; then
    echo "✅ Server process is running"
    PID=$(pgrep -f "python3 app.py")
    echo "   PID: $PID"
else
    echo "❌ Server process is not running"
fi

# Check if port is listening
if netstat -tlnp 2>/dev/null | grep -q ":8080"; then
    echo "✅ Server is listening on port 8080"
else
    echo "❌ Server is not listening on port 8080"
fi

# Check database
if [ -f "datasentry.db" ]; then
    echo "✅ Database exists"
    SIZE=$(du -h datasentry.db 2>/dev/null | cut -f1)
    echo "   Size: $SIZE"
else
    echo "❌ Database not found"
fi

# Check SSL certificates
if [ -f "cert.pem" ] && [ -f "key.pem" ]; then
    echo "✅ SSL certificates exist"
else
    echo "❌ SSL certificates missing"
fi

# Show IP addresses
echo ""
echo "🌐 Network Information:"
echo "   Local IP: $(hostname -I | awk '{print $1}')"
echo "   Dashboard: https://$(hostname -I | awk '{print $1}'):8080"

# Show recent logs if they exist
if [ -f "/var/log/datasentry/server.log" ]; then
    echo ""
    echo "📋 Recent logs:"
    tail -n 5 /var/log/datasentry/server.log 2>/dev/null || echo "   No logs available"
fi
EOF
chmod +x status.sh

# Final instructions
echo ""
echo "🎉 Quick Setup Complete!"
echo "======================="
echo ""
echo "🚀 Start server:"
echo "   ./start_server.sh"
echo ""
echo "🧪 Test server:"
echo "   ./test_server.sh"
echo ""
echo "📊 Check status:"
echo "   ./status.sh"
echo ""
echo "🌐 Dashboard:"
echo "   https://localhost:8080"
echo ""
echo "🔑 API Key:"
echo "   datasentry-quick-api-key-12345"
echo ""
echo "📱 Android Configuration:"
echo "   See android_config.txt for setup instructions"
echo ""
echo "🛡️  Security Tools Status:"
if [ ${#available_tools[@]} -gt 0 ]; then
    echo "   ✅ Available: ${available_tools[*]}"
else
    echo "   ⚠️  None installed - install with: sudo apt install tcpdump tshark whois dig"
fi
echo ""
echo "💡 Next Steps:"
echo "   1. Find your Kali IP: hostname -I"
echo "   2. Update Android app with your IP"
echo "   3. Start the server: ./start_server.sh"
echo "   4. Test connection: ./test_server.sh"
echo "   5. Access dashboard: https://YOUR_IP:8080"
echo ""
echo "⚡ Quick setup completed in under 2 minutes!"
