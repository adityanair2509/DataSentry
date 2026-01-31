# DataSentry Quick Setup Guide

## ⚡ Super Fast Setup (2 minutes)

This quick setup skips system updates and installs only the essential components to get DataSentry running immediately.

## 🚀 One-Command Setup

```bash
cd /home/kali/Desktop/DataSentry-main/linux_server
chmod +x quick_setup.sh
./quick_setup.sh
```

## 📋 What Quick Setup Does

### ✅ **Included (Essential)**
- Python virtual environment
- Core Python packages (Flask, requests, etc.)
- SSL certificate generation
- Database initialization
- Configuration files
- Startup scripts

### ⚠️ **Skipped (Optional)**
- System package updates (saves 10+ minutes)
- Security tools installation (tcpdump, tshark, etc.)
- Systemd service setup
- Log rotation configuration

## 🔧 After Quick Setup

### 1. Start Server
```bash
./start_server.sh
```

### 2. Test Server
```bash
./test_server.sh
```

### 3. Check Status
```bash
./status.sh
```

### 4. Access Dashboard
```
https://localhost:8080
```

## 📱 Configure Android App

### Find Your Kali IP
```bash
hostname -I
# Example output: 192.168.1.100
```

### Update AnalyticsClient.kt
```kotlin
private const val SERVER_URL = "https://192.168.1.100:8080/api/dns-data"
private const val API_KEY = "datasentry-quick-api-key-12345"
```

## 🛡️ Install Security Tools (Optional)

For full analysis capabilities, install these later:

```bash
sudo apt install tcpdump tshark whois dig netsniff-ng
```

## 📊 Quick Setup vs Full Setup

| Feature | Quick Setup | Full Setup |
|---------|-------------|------------|
| Time | ~2 minutes | ~15 minutes |
| System Updates | ❌ | ✅ |
| Security Tools | ❌ | ✅ |
| Core Functionality | ✅ | ✅ |
| Dashboard | ✅ | ✅ |
| DNS Analysis | ✅ | ✅ |
| Advanced Analysis | ⚠️ | ✅ |

## 🔍 What Works Immediately

### ✅ **Core Features**
- DNS query collection from Android
- Basic domain analysis
- Dashboard visualization
- Real-time updates
- Database storage
- SSL encryption

### ⚠️ **Limited Without Security Tools**
- Deep packet analysis
- WHOIS lookups
- Advanced threat detection
- Network traffic patterns

## 🚨 Troubleshooting

### Server Won't Start
```bash
# Check Python
python3 --version

# Check virtual environment
ls -la venv/

# Start manually
source venv/bin/activate
python3 app.py
```

### Can't Access Dashboard
```bash
# Check if server is running
pgrep -f "python3 app.py"

# Check port
netstat -tlnp | grep 8080

# Test locally
curl -k https://localhost:8080
```

### Android App Can't Connect
1. **Check IP**: `hostname -I`
2. **Update SERVER_URL** with correct IP
3. **Check API key** matches
4. **Test from phone browser**: `https://YOUR_IP:8080`

## 🎯 Quick Start Checklist

- [ ] Run `./quick_setup.sh`
- [ ] Run `./start_server.sh`
- [ ] Run `./test_server.sh`
- [ ] Find your IP: `hostname -I`
- [ ] Update Android app with IP
- [ ] Install and open Android app
- [ ] Activate VPN on phone
- [ ] Check dashboard for DNS queries

## 🔄 Migrating to Full Setup

If you want full functionality later:

```bash
# Install security tools
sudo apt update
sudo apt install tcpdump tshark whois dig netsniff-ng geoip-bin

# Run full setup (optional)
./setup.sh
```

## 💡 Pro Tips

### **For Testing**
- Use `ngrok` for easy HTTPS tunneling
- Test with phone browser first
- Check logs with `./status.sh`

### **For Production**
- Change default API key
- Install security tools
- Set up proper firewall
- Use systemd service

### **Network Issues**
- Ensure phone and Kali are on same network
- Use bridged network mode for VM
- Check firewall settings

This quick setup gets you running in minutes with full core functionality! 🚀
