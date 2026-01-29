# DataSentry Linux Analysis Server

Advanced threat hunting and network analysis server that receives DNS data from the DataSentry Android app and performs comprehensive security analysis using Kali Linux tools.

## 🎯 Features

### Core Analysis Capabilities
- **DNS Analysis**: Deep packet inspection with `dig` and `dns.resolver`
- **Network Traffic**: Real-time analysis with `tcpdump` and `tshark`
- **Connection Patterns**: Behavioral analysis with `netsniff-ng`
- **Domain Intelligence**: WHOIS analysis for domain age and reputation
- **Threat Detection**: Fast-flux DNS, beaconing, and burst behavior detection
- **Geographic Analysis**: IP geolocation and regional traffic patterns

### Security Analysis
- **DGA Detection**: Domain Generation Algorithm pattern recognition
- **C2 Detection**: Command and control server identification
- **Beaconing Analysis**: Regular communication pattern detection
- **Burst Behavior**: Anomalous traffic spike detection
- **Fast-Flux Detection**: Rapid IP change detection
- **Threat Intelligence**: Integration with known malware feeds

### Dashboard Features
- **Real-time Monitoring**: Live visualization of DNS queries and threats
- **Risk Scoring**: Comprehensive risk assessment for domains
- **Timeline Analysis**: Traffic patterns over time
- **Geographic Mapping**: Regional traffic distribution
- **App Attribution**: Per-application traffic analysis
- **Threat Alerts**: Real-time security notifications

## 🏗️ Architecture

```
DataSentry (Android) → HTTPS API → Analysis Engine → Threat Intel → Dashboard
                                    ↓
                            tcpdump/tshark/netsniff-ng
                                    ↓
                            whois/dig/geoip/mitmproxy
```

## 📋 Prerequisites

### System Requirements
- **OS**: Kali Linux / Ubuntu 20.04+ / Debian 11+
- **RAM**: Minimum 4GB, Recommended 8GB+
- **Storage**: Minimum 20GB, Recommended 50GB+
- **Network**: Stable internet connection

### Required Tools
- `tcpdump` - Packet capture
- `tshark` - Network protocol analyzer
- `netsniff-ng` - High-performance network analyzer
- `whois` - Domain registration information
- `dig` - DNS lookup utility
- `mitmproxy` - HTTP/HTTPS proxy (optional)
- `geoip-bin` - Geographic IP database

## 🚀 Quick Start

### 1. Clone and Setup
```bash
git clone <repository-url>
cd DataSentry/linux_server
chmod +x setup.sh
./setup.sh
```

### 2. Configure
```bash
# Edit configuration
nano config.py

# Update API key
API_KEY = 'your-secure-api-key-here'

# Update server URL in Android app
SERVER_URL = "https://YOUR_LINUX_SERVER:8080/api/dns-data"
```

### 3. Start Server
```bash
# Using systemd (recommended)
sudo systemctl start datasentry

# Or manually
./start_server.sh
```

### 4. Access Dashboard
```
https://localhost:8080
```

## 🔧 Configuration

### Server Configuration (`config.py`)
```python
class Config:
    # Server settings
    SECRET_KEY = os.urandom(32)
    DEBUG = False
    
    # API settings
    API_KEY = 'YOUR_SECURE_API_KEY_HERE'
    
    # Analysis settings
    MAX_DOMAINS_PER_DEVICE = 1000
    ANALYSIS_INTERVAL = 300  # 5 minutes
    
    # Threat intelligence
    THREAT_FEED_UPDATE_INTERVAL = 3600  # 1 hour
```

### Android App Configuration
Update `AnalyticsClient.kt` in the Android app:
```kotlin
private const val SERVER_URL = "https://YOUR_LINUX_SERVER:8080/api/dns-data"
private const val API_KEY = "your-secure-api-key-here"
```

## 📊 Analysis Modules

### 1. DNS Analysis Engine
```python
# Comprehensive domain analysis
analysis = analysis_engine.analyze_domain("example.com")

# Results include:
# - WHOIS information
# - DNS records (A, AAAA, MX, NS, TXT, CNAME)
# - IP resolution history
# - Fast-flux detection
# - DGA patterns
```

### 2. Threat Intelligence
```python
# Check domain against threat feeds
threat_info = threat_intel.check_domain_threat("suspicious-domain.com")

# Check IP reputation
ip_threat = threat_intel.check_ip_threat("192.168.1.1")
```

### 3. Network Traffic Analysis
```python
# Analyze traffic patterns with tshark
network_analysis = analysis_engine._analyze_network_traffic(domain)

# Detect beaconing patterns
beaconing = analysis_engine._detect_beaconing(domain)
```

## 🛡️ Security Features

### Threat Detection
- **Malware Domains**: Known malicious domain detection
- **DGA Detection**: Algorithmically generated domain identification
- **Fast-Flux DNS**: Rapid IP change detection
- **C2 Servers**: Command and control identification
- **Beaconing**: Regular communication patterns
- **Burst Behavior**: Anomalous traffic spikes

### Risk Scoring
- **Domain Age**: New domains are higher risk
- **Registrar Reputation**: Suspicious registrars flagged
- **IP Reputation**: Known malicious IP ranges
- **Traffic Patterns**: Unusual query patterns
- **Geographic Anomalies**: Unexpected geographic locations

### Alerting
- **Real-time Notifications**: Immediate threat alerts
- **Risk Thresholds**: Configurable alert levels
- **Dashboard Integration**: Visual threat indicators
- **Log Aggregation**: Comprehensive audit trails

## 📈 Dashboard Features

### Overview Metrics
- Total DNS queries
- Unique domains discovered
- High-risk domains identified
- Recent threat detections
- Privacy health score
- Active monitoring devices

### Visualizations
- **Timeline Charts**: Traffic patterns over time
- **Risk Distribution**: Risk level breakdown
- **Geographic Maps**: Regional traffic analysis
- **App Attribution**: Per-app traffic breakdown
- **Threat Timeline**: Security events chronology

### Real-time Updates
- Auto-refresh every 30 seconds
- Live threat notifications
- Interactive domain details
- Export capabilities

## 🔍 Advanced Analysis

### Command Line Tools Integration

#### tcpdump/tshark Analysis
```bash
# Capture DNS traffic
tshark -i any -Y "dns" -T fields -e dns.qry.name

# Analyze specific domain
tshark -r capture.pcap -Y "dns.qry.name contains example.com"
```

#### netsniff-ng Pattern Analysis
```bash
# High-performance packet capture
netsniff-ng --eth eth0 --filter "port 53" --output capture.pcap

# Traffic pattern analysis
netsniff-ng --in capture.pcap --analyze
```

#### WHOIS Intelligence
```bash
# Domain registration analysis
whois example.com

# Batch analysis
for domain in $(cat domains.txt); do
    whois $domain >> whois_results.txt
done
```

#### DNS Deep Dive
```bash
# Comprehensive DNS analysis
dig +trace example.com
dig +short ANY example.com
dig +dnssec example.com
```

### MITMProxy Integration (Optional)
```bash
# Start HTTPS proxy
mitmproxy -p 8080 --set confdir=~/.mitmproxy

# SSL certificate setup
mitmproxy --certs *=cert.pem
```

## 📊 Database Schema

### DNS Queries Table
```sql
CREATE TABLE dns_queries (
    id INTEGER PRIMARY KEY,
    timestamp INTEGER,
    domain TEXT,
    app_name TEXT,
    source_ip TEXT,
    dest_ip TEXT,
    protocol TEXT,
    size_bytes INTEGER,
    is_risk BOOLEAN,
    device_id TEXT,
    created_at DATETIME
);
```

### Analysis Results Table
```sql
CREATE TABLE analysis_results (
    id INTEGER PRIMARY KEY,
    domain TEXT,
    analysis_type TEXT,
    result TEXT,
    risk_score INTEGER,
    created_at DATETIME
);
```

### Threat Detections Table
```sql
CREATE TABLE threat_detections (
    id INTEGER PRIMARY KEY,
    domain TEXT,
    threat_type TEXT,
    risk_score INTEGER,
    details TEXT,
    detected_at DATETIME
);
```

## 🚨 Alerting Rules

### High-Risk Indicators
- Domain age < 30 days
- Fast-flux DNS detected
- Known C2 servers
- DGA patterns detected
- Suspicious registrar
- High-frequency beaconing

### Medium-Risk Indicators
- Domain age < 1 year
- Multiple IP addresses
- Geographic anomalies
- Burst traffic patterns
- Unusual query patterns

### Response Actions
- Immediate notification
- Dashboard highlighting
- Log aggregation
- Automated blocking (optional)
- Report generation

## 📱 Android Integration

### Data Flow
1. **DNS Query Intercepted**: DataSentry app captures DNS queries
2. **Batch Processing**: Queries are batched and sent via HTTPS
3. **Server Analysis**: Linux server performs comprehensive analysis
4. **Threat Detection**: Real-time threat intelligence checking
5. **Dashboard Update**: Results displayed in web dashboard

### API Endpoints
```
POST /api/dns-data          # Receive DNS data from Android
GET  /api/dashboard/data     # Get dashboard analytics
GET  /api/analysis/domain/<domain>  # Get domain analysis
GET  /api/threats/recent     # Get recent threats
```

## 🔧 Maintenance

### System Monitoring
```bash
# Check service status
systemctl status datasentry

# View logs
journalctl -u datasentry -f

# Monitor system resources
./monitor.sh
```

### Database Maintenance
```bash
# Backup database
sqlite3 datasentry.db ".backup backup_$(date +%Y%m%d).db"

# Clean old data
sqlite3 datasentry.db "DELETE FROM dns_queries WHERE created_at < datetime('now', '-30 days');"

# Optimize database
sqlite3 datasentry.db "VACUUM;"
```

### Updates
```bash
# Update threat feeds
sudo systemctl restart datasentry

# Update system packages
sudo apt update && sudo apt upgrade

# Update Python dependencies
source venv/bin/activate
pip install -r requirements.txt --upgrade
```

## 🛡️ Security Considerations

### Network Security
- HTTPS encryption for all communications
- API key authentication
- SSL certificate validation
- Firewall configuration

### Data Protection
- Local database encryption
- Secure API key storage
- Log file rotation
- Access control

### Privacy
- On-premise processing
- No data transmission to third parties
- Configurable data retention
- GDPR compliance considerations

## 📞 Support

### Troubleshooting
```bash
# Check service logs
journalctl -u datasentry -n 50

# Test API endpoint
curl -k https://localhost:8080/api/dashboard/data

# Check database
sqlite3 datasentry.db ".tables"

# Verify tools installation
which tcpdump tshark whois dig netsniff-ng
```

### Common Issues
1. **Service won't start**: Check logs and dependencies
2. **No data received**: Verify Android app configuration
3. **Analysis errors**: Check tool installations and permissions
4. **Dashboard not loading**: Check SSL certificates and firewall

## 📄 License

This project is developed for educational and security research purposes. Use responsibly and in accordance with applicable laws and regulations.

---

**Built with ❤️ for advanced network security analysis**
