#!/bin/bash
echo "🔍 DataSentry Network Diagnosis"
echo "=============================="

echo "🌐 Kali VM Network Configuration:"
echo "---------------------------------"

# Check network interfaces
echo "1. Network Interfaces:"
ip addr show | grep -E "inet.*scope global" | while read line; do
    echo "   $line"
done

echo ""
echo "2. Default Route:"
ip route | grep default

echo ""
echo "3. DNS Configuration:"
cat /etc/resolv.conf 2>/dev/null || echo "   Cannot read resolv.conf"

echo ""
echo "4. Listening Ports:"
ss -tlnp | grep 8081 || echo "   Port 8081 not listening"

echo ""
echo "📱 Testing Connectivity:"
echo "------------------------"

# Test basic connectivity
echo "5. Internet Connectivity:"
if ping -c 1 8.8.8.8 &> /dev/null; then
    echo "   ✅ Internet reachable"
else
    echo "   ❌ Internet not reachable"
fi

# Test DNS
echo "6. DNS Resolution:"
if nslookup google.com &> /dev/null; then
    echo "   ✅ DNS working"
else
    echo "   ❌ DNS not working"
fi

echo ""
echo "🔧 VM Network Diagnosis:"
echo "------------------------"

# Check if we're in a VM
echo "7. Virtualization Detection:"
if dmesg | grep -i "hypervisor" &> /dev/null; then
    echo "   ✅ Running in VM"
else
    echo "   ⚠️  VM detection unclear"
fi

# Check VM tools
echo "8. VM Tools:"
if command -v vmtoolsd &> /dev/null; then
    echo "   ✅ VMware tools detected"
elif command -v VBoxService &> /dev/null; then
    echo "   ✅ VirtualBox tools detected"
else
    echo "   ⚠️  No VM tools detected"
fi

echo ""
echo "🌍 Network Bridge Test:"
echo "----------------------"

# Check for bridge
echo "9. Bridge Interfaces:"
brctl show 2>/dev/null || echo "   No bridges found"

echo ""
echo "📊 Summary:"
echo "----------"
echo "   Kali IP: $(hostname -I | awk '{print $1}')"
echo "   Server should be: http://$(hostname -I | awk '{print $1}'):8081"
echo "   Test from host: http://$(hostname -I | awk '{print $1}'):8081"

echo ""
echo "💡 VM Networking Tips:"
echo "-------------------"
echo "1. VM Settings → Network Adapter → Bridged Mode (NOT NAT)"
echo "2. Ensure phone and VM are on same WiFi network"
echo "3. Check Windows Firewall doesn't block port 8081"
echo "4. Try: ping <kali_ip> from Windows Command Prompt"
echo "5. Try: telnet <kali_ip> 8081 from Windows Command Prompt"
