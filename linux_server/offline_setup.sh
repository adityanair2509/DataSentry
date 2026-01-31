#!/bin/bash

# DataSentry Offline-Friendly Setup Script
# Handles network issues and provides alternatives

echo "🚀 DataSentry Offline-Friendly Setup"
echo "==================================="

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

# Test network connectivity
echo "🌐 Testing network connectivity..."
if ping -c 1 google.com &> /dev/null; then
    echo "✅ Internet connection available"
    NETWORK_AVAILABLE=true
else
    echo "⚠️  No internet connection - using offline mode"
    NETWORK_AVAILABLE=false
fi

# Check if we can reach PyPI
if [ "$NETWORK_AVAILABLE" = true ]; then
    if curl -s --connect-timeout 5 https://pypi.org > /dev/null; then
        echo "✅ PyPI accessible"
        PIP_AVAILABLE=true
    else
        echo "⚠️  PyPI not accessible - using offline mode"
        PIP_AVAILABLE=false
        NETWORK_AVAILABLE=false
    fi
else
    PIP_AVAILABLE=false
fi

# Function to install package with fallback
install_package() {
    local package=$1
    local fallback_cmd=$2
    
    if [ "$PIP_AVAILABLE" = true ]; then
        echo "📦 Installing $package via pip..."
        if pip install "$package" 2>/dev/null; then
            echo "✅ $package installed successfully"
            return 0
        else
            echo "⚠️  Failed to install $package via pip"
        fi
    fi
    
    # Fallback to system package or skip
    if [ -n "$fallback_cmd" ]; then
        echo "💡 Trying fallback: $fallback_cmd"
        if eval "$fallback_cmd" 2>/dev/null; then
            echo "✅ Fallback successful for $package"
            return 0
        else
            echo "⚠️  Fallback failed for $package"
        fi
    fi
    
    echo "⚠️  $package not available - functionality may be limited"
    return 1
}

# Create virtual environment
echo "🐍 Setting up Python environment..."
if [ ! -d "venv" ]; then
    python3 -m venv venv 2>/dev/null || {
        echo "❌ Failed to create virtual environment"
        echo "💡 Try: sudo apt install python3-venv"
        exit 1
    }
    echo "✅ Virtual environment created"
else
    echo "✅ Virtual environment already exists"
fi

# Activate virtual environment
source venv/bin/activate

# Upgrade pip (if network available)
if [ "$PIP_AVAILABLE" = true ]; then
    echo "📦 Upgrading pip..."
    pip install --upgrade pip --quiet 2>/dev/null || echo "⚠️  Pip upgrade failed, continuing..."
fi

# Install essential packages with fallbacks
echo "📦 Installing essential packages..."

# Core web framework
install_package "Flask==2.3.3" "sudo apt install python3-flask -y"

# CORS support
install_package "Flask-CORS==4.0.0" "sudo apt install python3-flask-cors -y"

# HTTP requests
install_package "requests==2.31.0" "sudo apt install python3-requests -y"

# DNS analysis
install_package "dnspython==2.4.2" "sudo apt install python3-dnspython -y"

# Optional packages (install if possible, skip if not)
echo "📦 Installing optional packages..."

# WHOIS analysis
install_package "python-whois==0.8.0" ""

# Network analysis
install_package "scapy==2.5.0" "sudo apt install python3-scapy -y"

# System monitoring
install_package "psutil==5.9.5" "sudo apt install python3-psutil -y"

# Scheduling
install_package "schedule==1.2.0" ""

# Date utilities
install_package "python-dateutil==2.8.2" "sudo apt install python3-dateutil -y"

# Create essential directories
echo "📁 Creating directories..."
mkdir -p /var/log/datasentry 2>/dev/null || sudo mkdir -p /var/log/datasentry
mkdir -p /tmp/datasentry
mkdir -p data

# Set permissions
[ -d "/var/log/datasentry" ] && sudo chmod 755 /var/log/datasentry 2>/dev/null
chmod 755 /tmp/datasentry 2>/dev/null
chmod 755 data 2>/dev/null

# Generate SSL certificate
echo "🔐 Generating SSL certificate..."
if [ ! -f "cert.pem" ] || [ ! -f "key.pem" ]; then
    if command -v openssl &> /dev/null; then
        openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days 365 -nodes \
            -subj "/C=US/ST=State/L=City/O=DataSentry/OU=Analysis/CN=localhost" 2>/dev/null
        echo "✅ SSL certificate generated"
    else
        echo "⚠️  OpenSSL not found - generating self-signed certificate with Python..."
        python3 -c "
import ssl
from cryptography import x509
from cryptography.x509.oid import NameOID
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives import serialization
import datetime

# Generate private key
private_key = rsa.generate_private_key(public_exponent=65537, key_size=2048)

# Create certificate
subject = issuer = x509.Name([
    x509.NameAttribute(NameOID.COUNTRY_NAME, 'US'),
    x509.NameAttribute(NameOID.STATE_OR_PROVINCE_NAME, 'State'),
    x509.NameAttribute(NameOID.LOCALITY_NAME, 'City'),
    x509.NameAttribute(NameOID.ORGANIZATION_NAME, 'DataSentry'),
    x509.NameAttribute(NameOID.COMMON_NAME, 'localhost'),
])

cert = x509.CertificateBuilder().subject_name(subject).issuer_name(issuer).public_key(
    private_key.public_key()
).serial_number(x509.random_serial_number()).not_valid_before(
    datetime.datetime.utcnow()
).not_valid_after(datetime.datetime.utcnow() + datetime.timedelta(days=365)).add_extension(
    x509.SubjectAlternativeName([x509.DNSName('localhost')]), critical=False
).sign(private_key, hashes.SHA256())

# Write certificate and key
with open('cert.pem', 'wb') as f:
    f.write(cert.public_bytes(serialization.Encoding.PEM))

with open('key.pem', 'wb') as f:
    f.write(private_key.private_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PrivateFormat.TraditionalOpenSSL,
        encryption_algorithm=serialization.NoEncryption()
    ))
print('✅ SSL certificate generated with Python')
" 2>/dev/null || echo "⚠️  Could not generate SSL certificate"
    fi
else
    echo "✅ SSL certificate already exists"
fi

# Create configuration
echo "⚙️ Creating configuration..."
cat > config.py <<'EOF'
# DataSentry Server Configuration - Offline Setup
import os

class Config:
    # Server settings
    SECRET_KEY = 'datasentry-offline-setup-key-change-in-production'
    DEBUG = False
    
    # SSL settings
    SSL_CERT = 'cert.pem'
    SSL_KEY = 'key.pem'
    
    # Database settings
    DATABASE_PATH = 'datasentry.db'
    
    # API settings - CHANGE THIS!
    API_KEY = 'datasentry-offline-api-key-12345'
    
    # Analysis settings
    MAX_DOMAINS_PER_DEVICE = 1000
    ANALYSIS_INTERVAL = 300  # 5 minutes
    
    # Network settings
    BIND_HOST = '0.0.0.0'
    PORT = 8080
    
    # Logging settings
    LOG_LEVEL = 'INFO'
    LOG_FILE = '/var/log/datasentry/server.log'
    
    # Tool paths (check if available)
    import shutil
    TCPDUMP_PATH = shutil.which('tcpdump') or '/usr/bin/tcpdump'
    TSHARK_PATH = shutil.which('tshark') or '/usr/bin/tshark'
    WHOIS_PATH = shutil.which('whois') or '/usr/bin/whois'
    DIG_PATH = shutil.which('dig') or '/usr/bin/dig'
    
    # Offline mode settings
    OFFLINE_MODE = not PIP_AVAILABLE
    ENABLE_ADVANCED_ANALYSIS = shutil.which('tcpdump') is not None
EOF

# Create database
echo "🗄️ Creating database..."
python3 -c "
import sqlite3
import os

db_path = 'datasentry.db'

# Remove existing database for clean start
if os.path.exists(db_path):
    os.remove(db_path)
    print('🗑️  Removed existing database')

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
print('✅ Database initialized successfully')
"

# Create startup script
echo "🚀 Creating startup script..."
cat > start_server.sh <<'EOF'
#!/bin/bash
cd "$(dirname "$0")"
echo "🚀 Starting DataSentry Server..."
echo "📁 Directory: $(pwd)"

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo "❌ Virtual environment not found. Run ./offline_setup.sh first"
    exit 1
fi

# Activate virtual environment
source venv/bin/activate

# Check if required packages are available
python3 -c "import flask" 2>/dev/null || {
    echo "❌ Flask not available. Please check installation."
    exit 1
}

echo "🌐 Dashboard will be available at: https://localhost:8080"
echo "🔑 API Key: datasentry-offline-api-key-12345"
echo "📱 Find your IP with: hostname -I"
echo ""

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

# Check if server is running
if pgrep -f "python3 app.py" > /dev/null; then
    echo "✅ Server process is running"
    
    # Test API endpoint
    echo "📊 Testing dashboard API..."
    if command -v curl &> /dev/null; then
        curl -k -s https://localhost:8080/api/dashboard-data | python3 -m json.tool 2>/dev/null || echo "❌ API response error"
    else
        echo "⚠️  curl not available - cannot test API"
    fi
    
    echo ""
    echo "🌐 Open in browser: https://localhost:8080"
    echo "🔑 API Key for Android app: datasentry-offline-api-key-12345"
else
    echo "❌ Server is not running"
    echo "💡 Start with: ./start_server.sh"
fi
EOF
chmod +x test_server.sh

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
if command -v netstat &> /dev/null; then
    if netstat -tlnp 2>/dev/null | grep -q ":8080"; then
        echo "✅ Server is listening on port 8080"
    else
        echo "❌ Server is not listening on port 8080"
    fi
elif command -v ss &> /dev/null; then
    if ss -tlnp 2>/dev/null | grep -q ":8080"; then
        echo "✅ Server is listening on port 8080"
    else
        echo "❌ Server is not listening on port 8080"
    fi
else
    echo "⚠️  Cannot check port status (netstat/ss not available)"
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

# Check virtual environment
if [ -d "venv" ]; then
    echo "✅ Virtual environment exists"
else
    echo "❌ Virtual environment not found"
fi

# Show IP addresses
echo ""
echo "🌐 Network Information:"
if command -v hostname &> /dev/null; then
    echo "   Local IP: $(hostname -I | awk '{print $1}')"
    echo "   Dashboard: https://$(hostname -I | awk '{print $1}'):8080"
else
    echo "   Cannot determine IP address (hostname not available)"
fi

# Show installed packages
echo ""
echo "📦 Python Packages Status:"
source venv/bin/activate 2>/dev/null
for pkg in flask requests; do
    if python3 -c "import $pkg" 2>/dev/null; then
        echo "   ✅ $pkg"
    else
        echo "   ❌ $pkg (not installed)"
    fi
done

# Show available tools
echo ""
echo "🛡️  Security Tools Status:"
for tool in tcpdump tshark whois dig; do
    if command -v "$tool" &> /dev/null; then
        echo "   ✅ $tool"
    else
        echo "   ⚠️  $tool (not available)"
    fi
done
EOF
chmod +x status.sh

# Network troubleshooting helper
echo "🔧 Creating network troubleshooter..."
cat > troubleshoot.sh <<'EOF'
#!/bin/bash
echo "🔧 DataSentry Network Troubleshooter"
echo "=================================="

echo "🌐 Testing Network Connectivity..."
echo ""

# Test basic connectivity
echo "1. Testing local network..."
if command -v ping &> /dev/null; then
    if ping -c 1 8.8.8.8 &> /dev/null; then
        echo "   ✅ Basic internet connectivity OK"
    else
        echo "   ❌ No internet connectivity"
        echo "   💡 Check your network connection"
    fi
else
    echo "   ⚠️  ping not available - cannot test connectivity"
fi

# Test DNS resolution
echo ""
echo "2. Testing DNS resolution..."
if command -v nslookup &> /dev/null; then
    if nslookup google.com &> /dev/null; then
        echo "   ✅ DNS resolution working"
    else
        echo "   ❌ DNS resolution failed"
        echo "   💡 Try: sudo systemctl restart systemd-resolved"
    fi
elif command -v dig &> /dev/null; then
    if dig google.com &> /dev/null; then
        echo "   ✅ DNS resolution working"
    else
        echo "   ❌ DNS resolution failed"
    fi
else
    echo "   ⚠️  DNS tools not available"
fi

# Test PyPI access
echo ""
echo "3. Testing PyPI access..."
if command -v curl &> /dev/null; then
    if curl -s --connect-timeout 5 https://pypi.org > /dev/null; then
        echo "   ✅ PyPI accessible"
    else
        echo "   ❌ PyPI not accessible"
        echo "   💡 This may cause pip installation failures"
    fi
else
    echo "   ⚠️  curl not available - cannot test PyPI"
fi

# Show network interfaces
echo ""
echo "4. Network interfaces:"
if command -v ip &> /dev/null; then
    ip addr show | grep -E "inet.*scope global" | awk '{print "   " $2 " (" $NF ")"}'
elif command -v ifconfig &> /dev/null; then
    ifconfig | grep -E "inet.*broadcast" | awk '{print "   " $2}'
else
    echo "   ⚠️  Network interface tools not available"
fi

# Show routing
echo ""
echo "5. Default route:"
if command -v ip &> /dev/null; then
    ip route | grep default
elif command -v route &> /dev/null; then
    route -n | grep "^0.0.0.0"
else
    echo "   ⚠️  Routing tools not available"
fi

echo ""
echo "💡 Suggestions:"
echo "   - If no internet: check VM network settings (bridged mode)"
echo "   - If DNS fails: try sudo systemctl restart systemd-resolved"
echo "   - If PyPI fails: use offline mode or check proxy settings"
echo "   - Make sure VM is in bridged network mode"
EOF
chmod +x troubleshoot.sh

# Final status
echo ""
echo "🎉 Offline-Friendly Setup Complete!"
echo "================================="
echo ""

if [ "$NETWORK_AVAILABLE" = true ]; then
    echo "✅ Network available - Full functionality"
else
    echo "⚠️  Network unavailable - Limited functionality"
    echo "   💡 Run ./troubleshoot.sh to diagnose network issues"
fi

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
echo "🔧 Troubleshoot network:"
echo "   ./troubleshoot.sh"
echo ""
echo "🌐 Dashboard:"
echo "   https://localhost:8080"
echo ""
echo "🔑 API Key:"
echo "   datasentry-offline-api-key-12345"
echo ""
echo "📱 Android Configuration:"
echo "   1. Find your IP: hostname -I"
echo "   2. Update AnalyticsClient.kt with your IP"
echo "   3. Use API key: datasentry-offline-api-key-12345"
echo ""
echo "⚡ Setup completed with network error handling!"
