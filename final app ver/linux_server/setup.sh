#!/bin/bash

# DataSentry Linux Analysis Server Setup Script
# For Kali Linux / Ubuntu 20.04+

echo "🛡️ DataSentry Linux Analysis Server Setup"
echo "=========================================="

# Check if running as root
if [[ $EUID -eq 0 ]]; then
   echo "❌ This script should not be run as root for security reasons."
   echo "   Run as a regular user with sudo privileges."
   exit 1
fi

# Update system
echo "📦 Updating system packages..."
sudo apt update && sudo apt upgrade -y

# Install system dependencies
echo "🔧 Installing system dependencies..."
sudo apt install -y \
    python3 \
    python3-pip \
    python3-venv \
    sqlite3 \
    tcpdump \
    tshark \
    netsniff-ng \
    whois \
    dig \
    dnsutils \
    curl \
    wget \
    git \
    net-tools \
    nmap \
    geoip-bin \
    geoip-database \
    mitmproxy

# Install Python dependencies
echo "🐍 Installing Python dependencies..."
python3 -m venv venv
source venv/bin/activate
pip install --upgrade pip
pip install -r requirements.txt

# Create necessary directories
echo "📁 Creating directories..."
mkdir -p /var/log/datasentry
mkdir -p /tmp/datasentry
mkdir -p data

# Set permissions
echo "🔒 Setting permissions..."
chmod 755 /var/log/datasentry
chmod 755 /tmp/datasentry
chmod 755 data

# Create systemd service
echo "⚙️ Creating systemd service..."
sudo tee /etc/systemd/system/datasentry.service > /dev/null <<EOF
[Unit]
Description=DataSentry Analysis Server
After=network.target

[Service]
Type=simple
User=$USER
WorkingDirectory=$(pwd)
Environment=PATH=$(pwd)/venv/bin
ExecStart=$(pwd)/venv/bin/python app.py
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
EOF

# Create log rotation
echo "📋 Setting up log rotation..."
sudo tee /etc/logrotate.d/datasentry > /dev/null <<EOF
/var/log/datasentry/*.log {
    daily
    missingok
    rotate 30
    compress
    delaycompress
    notifempty
    create 644 $USER $USER
    postrotate
        systemctl reload datasentry
    endscript
}
EOF

# Generate SSL certificate for HTTPS
echo "🔐 Generating SSL certificate..."
openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes \
    -subj "/C=US/ST=State/L=City/O=DataSentry/OU=Analysis/CN=localhost"

# Create configuration file
echo "⚙️ Creating configuration..."
cat > config.py <<EOF
# DataSentry Server Configuration
import os

class Config:
    # Server settings
    SECRET_KEY = os.urandom(32)
    DEBUG = False
    
    # SSL settings
    SSL_CERT = 'cert.pem'
    SSL_KEY = 'key.pem'
    
    # Database settings
    DATABASE_PATH = 'datasentry.db'
    
    # API settings
    API_KEY = 'YOUR_SECURE_API_KEY_HERE'
    
    # Analysis settings
    MAX_DOMAINS_PER_DEVICE = 1000
    ANALYSIS_INTERVAL = 300  # 5 minutes
    
    # Threat intelligence settings
    THREAT_FEED_UPDATE_INTERVAL = 3600  # 1 hour
    
    # Logging settings
    LOG_LEVEL = 'INFO'
    LOG_FILE = '/var/log/datasentry/server.log'
    
    # Network capture settings
    CAPTURE_INTERFACE = 'any'  # or specific interface like 'eth0'
    CAPTURE_TIMEOUT = 30
    
    # External tool paths
    TCPDUMP_PATH = '/usr/bin/tcpdump'
    TSHARK_PATH = '/usr/bin/tshark'
    WHOIS_PATH = '/usr/bin/whois'
    DIG_PATH = '/usr/bin/dig'
    NETSNIFF_NG_PATH = '/usr/sbin/netsniff-ng'

class DevelopmentConfig(Config):
    DEBUG = True
    LOG_LEVEL = 'DEBUG'

class ProductionConfig(Config):
    DEBUG = False
    LOG_LEVEL = 'WARNING'
EOF

# Update app.py to use configuration
echo "🔄 Updating app.py for configuration..."
sed -i '4i import config' app.py
sed -i "s/'YOUR_SECURE_API_KEY_HERE'/config.Config.API_KEY/g" app.py

# Create startup script
echo "🚀 Creating startup script..."
cat > start_server.sh <<'EOF'
#!/bin/bash
cd "$(dirname "$0")"
source venv/bin/activate
python app.py
EOF
chmod +x start_server.sh

# Create monitoring script
echo "📊 Creating monitoring script..."
cat > monitor.sh <<'EOF'
#!/bin/bash

echo "📊 DataSentry Server Monitor"
echo "=========================="

# Check if service is running
if systemctl is-active --quiet datasentry; then
    echo "✅ DataSentry service is running"
else
    echo "❌ DataSentry service is not running"
    echo "   Start with: sudo systemctl start datasentry"
fi

# Check port
if netstat -tlnp | grep -q ":8080"; then
    echo "✅ Server is listening on port 8080"
else
    echo "❌ Server is not listening on port 8080"
fi

# Check database
if [ -f "datasentry.db" ]; then
    echo "✅ Database exists"
    echo "   Size: $(du -h datasentry.db | cut -f1)"
else
    echo "❌ Database not found"
fi

# Show recent logs
echo ""
echo "📋 Recent logs:"
tail -n 10 /var/log/datasentry/server.log 2>/dev/null || echo "No logs found"

# Show system resources
echo ""
echo "💻 System Resources:"
echo "   CPU: $(top -bn1 | grep "Cpu(s)" | awk '{print $2}' | cut -d'%' -f1)%"
echo "   Memory: $(free -h | awk '/^Mem:/ {print $3 "/" $2}')"
echo "   Disk: $(df -h . | awk 'NR==2 {print $3 "/" $2 " (" $5 ")"}')"
EOF
chmod +x monitor.sh

# Enable and start service
echo "🔄 Enabling and starting service..."
sudo systemctl daemon-reload
sudo systemctl enable datasentry
sudo systemctl start datasentry

# Wait a moment for service to start
sleep 3

# Check if service started successfully
if systemctl is-active --quiet datasentry; then
    echo ""
    echo "✅ DataSentry Analysis Server setup complete!"
    echo ""
    echo "🌐 Access the dashboard at: https://localhost:8080"
    echo "📊 Monitor with: ./monitor.sh"
    echo "📋 View logs: journalctl -u datasentry -f"
    echo "🔧 Stop service: sudo systemctl stop datasentry"
    echo ""
    echo "⚠️  Don't forget to:"
    echo "   1. Update YOUR_SECURE_API_KEY_HERE in config.py"
    echo "   2. Configure your Android app with the server IP"
    echo "   3. Set up proper firewall rules"
    echo ""
else
    echo ""
    echo "❌ Service failed to start. Check logs with:"
    echo "   journalctl -u datasentry -f"
    echo ""
    echo "   Try starting manually: ./start_server.sh"
fi

echo "🎯 Setup script completed!"
