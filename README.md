od# DataSentry 🛡️

<div align="center">

**Advanced Network Traffic Analysis & Privacy Protection for Android**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/API-29+-brightgreen?style=for-the-badge)](https://android-arsenal.com/api?level=29)

*Monitor, analyze, and protect your mobile network traffic in real-time*

</div>

---

## 📖 Overview

DataSentry is a sophisticated Android application that provides deep packet inspection and network traffic analysis capabilities. Built on Android's VpnService API, it creates a local VPN tunnel to intercept all network traffic, enabling comprehensive monitoring of app communications, data flows, and potential privacy threats.

### Why DataSentry?

In an era where mobile applications constantly communicate with remote servers, users have little visibility into what data is being transmitted. DataSentry bridges this gap by providing:

- **Complete Traffic Visibility**: See every network connection your device makes
- **App-Level Attribution**: Know which applications are sending data
- **Content Classification**: Automatic detection of traffic types (video, images, text, telemetry)
- **Privacy Scoring**: Real-time assessment of your device's privacy health
- **Persistent Logging**: Historical analysis of all network activity with AES-256 encryption
- **VirusTotal Integration**: Automated threat intelligence for domains and files (non-technical user friendly)
- **Behavior Pattern Analysis**: Advanced ML-based detection of anomalous domain access patterns
- **Smart Alerts**: Real-time notifications for harmful apps and suspicious domains
- **Transparent Data Collection**: Clear disclosure when server analytics are enabled

---

## 🏗️ System Architecture

DataSentry follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                         │
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │   DashboardScreen   │◄───│       DashboardViewModel        ││
│  │   (Jetpack Compose) │    │   (StateFlow, Coroutines)       ││
│  └─────────────────────┘    └─────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                        DOMAIN LAYER                              │
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │  DataSentryService  │    │       TrafficInspector          ││
│  │    (VpnService)     │───►│   (Packet Analysis Engine)      ││
│  └─────────────────────┘    └─────────────────────────────────┘│
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │  DemoScenarioEngine │    │        AnalysisResult           ││
│  │ (Traffic Profiling) │    │    (Analysis Data Model)        ││
│  └─────────────────────┘    └─────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                         DATA LAYER                               │
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │    AppDatabase      │    │       PacketRepository          ││
│  │      (Room)         │◄───│    (Data Access Layer)          ││
│  └─────────────────────┘    └─────────────────────────────────┘│
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │    PacketEntity     │    │    FlowStats / SuspiciousEvent  ││
│  │   (Traffic Log)     │    │     (Analysis Metrics)          ││
│  └─────────────────────┘    └─────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

### 🔐 Security & Privacy

#### AES-256 Encryption
All sensitive data is protected with military-grade encryption:
- **App-Side Encryption**: Traffic logs are encrypted before storage using Android Keystore
- **Server-Side Encryption**: Database is secured with SQLCipher and AES-256 encryption
- **Encrypted Transmission**: All data sent to the analytics server is encrypted end-to-end
- **Secure Key Management**: Encryption keys are generated and stored securely using Android Keystore API

#### VirusTotal Integration
Non-technical users get enterprise-grade threat intelligence:
- **Automatic Domain Scanning**: All contacted domains are checked against VirusTotal's database
- **File Hash Analysis**: Downloads and transferred files are analyzed for malware signatures
- **User-Friendly Reports**: Complex security data presented in simple, actionable insights
- **Background Processing**: Scans happen automatically without user intervention
- **Rate Limiting**: Intelligent caching to stay within API limits

### 🧠 Advanced Analysis

#### Behavior Pattern Analysis
Machine learning-powered detection of suspicious activity:
- **Domain Access Patterns**: Identifies unusual frequency, timing, or volume of domain requests
- **Anomaly Detection**: Flags deviations from normal app behavior baselines
- **Cross-App Correlation**: Detects coordinated activity across multiple applications
- **Temporal Analysis**: Identifies suspicious late-night or recurring patterns
- **Risk Scoring**: Each domain is assigned a dynamic risk score based on behavior

#### Real-Time Processing
Users get instant feedback on network activity:
- **Live Traffic Updates**: Sub-second latency between packet capture and UI display
- **Processing Status Indicators**: Visual feedback showing analysis progress
- **Background Queue Management**: Efficient handling of high-volume traffic
- **Progressive Analysis**: Critical threats are identified and alerted immediately

### 🎨 UI/UX Enhancements

#### Smart Notifications & Alerts
Keep users informed without overwhelming them:
- **Threat Severity Levels**: Color-coded alerts (Critical/High/Medium/Low)
- **Actionable Notifications**: One-tap actions to block apps or domains
- **Alert Grouping**: Similar alerts are intelligently grouped to reduce noise
- **Persistent Heads-Up**: Critical threats show as priority notifications
- **Customizable Thresholds**: Users can configure alert sensitivity

#### Transparent Data Collection
When server analytics are enabled, users have full visibility:
- **Pre-Connection Disclosure**: Clear explanation shown before enabling server mode
- **Data Collection Manifest**: Detailed list of what data will be sent to the server
  - Packet metadata (timestamps, sizes, protocols)
  - Domain names and IP addresses
  - App package names and versions
  - Device model and OS version (anonymized)
- **One-Tap Opt-Out**: Easy toggle to disable server analytics at any time
- **Data Retention Policy**: Clear statement on how long data is stored
- **No Personal Data**: Guarantee that content payloads and personal info are never collected

---

## 🔧 Core Components

### 1. DataSentryService.kt - VPN Engine

The heart of the application. This service extends `VpnService` to create a local VPN tunnel.

**Key Responsibilities:**
- Establishes VPN interface using `VpnService.Builder`
- Manages foreground service with persistent notification
- Coordinates packet inspection and logging
- Handles service lifecycle (start/stop/revoke)

```kotlin
// VPN Interface Configuration
val builder = Builder()
    .setSession("DataSentry Traffic Monitor")
    .addAddress("10.0.0.2", 32)  // Virtual interface IP
    .setBlocking(false)

vpnInterface = builder.establish()
```

**Service Commands:**
- `ACTION_STOP`: Gracefully terminates VPN monitoring
- Default: Starts VPN and begins traffic analysis

### 2. TrafficInspector.kt - Packet Analysis Engine

Performs deep packet inspection on intercepted traffic.

**Capabilities:**
- IPv4 packet parsing (version, IHL, protocol detection)
- TCP/UDP header extraction (ports, flags)
- DNS query/response parsing (RFC 1035)
- Traffic flow aggregation and statistics

```kotlin
fun inspect(packet: ByteArray, uid: Int) {
    val version = (packet[0].toInt() and 0xFF) ushr 4
    val protocol = packet[9].toInt() and 0xFF  // TCP=6, UDP=17
    val srcIp = extractIpv4(packet, 12)
    val dstIp = extractIpv4(packet, 16)
    // ... analysis continues
}
```

### 3. DemoScenarioEngine.kt - Traffic Profiling

Implements intelligent traffic classification based on destination patterns.

**Detection Heuristics:**
| IP Pattern | Application | Traffic Type |
|------------|-------------|--------------|
| `172.217.*`, `142.250.*` | YouTube | VIDEO_STREAM_4K |
| `104.16.*`, `104.17.*` | Cloudflare CDN | WEB_NAVIGATION |
| `142.251.*`, `74.125.*` | Google Services | BACKGROUND_TELEMETRY |

```kotlin
object DemoScenarioEngine {
    fun getScenarioByIp(destIp: String): AnalysisResult? {
        return when {
            destIp.startsWith("172.217.") -> scenarios["com.google.android.youtube"]
            destIp.startsWith("104.16.") -> scenarios["com.android.chrome"]
            // ... pattern matching
        }
    }
}
```

### 4. AppDatabase.kt - Persistence Layer

Room database for storing all traffic logs and analysis results.

**Entities:**
- `PacketEntity`: Individual packet logs with metadata
- `FlowStats`: Aggregated traffic statistics per app
- `SuspiciousEvent`: Flagged security concerns

```kotlin
@Database(
    entities = [PacketEntity::class, FlowStats::class, SuspiciousEvent::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun packetDao(): PacketDao
    abstract fun flowStatsDao(): FlowStatsDao
    abstract fun suspiciousEventDao(): SuspiciousEventDao
}
```

### 5. DashboardScreen.kt - Real-Time UI

Modern Compose UI with live traffic visualization.

**UI Components:**
- Privacy Health Score with animated radar visualization
- Live traffic list with app attribution
- Content type badges (Video 4K, Image, Text, Telemetry)
- Start/Stop monitoring controls

```kotlin
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val packets by viewModel.packets.collectAsState()
    
    LazyColumn {
        items(packets) { packet ->
            PacketItem(packet)  // Real-time updates via Flow
        }
    }
}
```

---

## 📊 Data Models

### PacketEntity
```kotlin
data class PacketEntity(
    val id: Long,
    val timestamp: Long,
    val sourceIp: String,
    val destIp: String,
    val protocol: String,      // TCP, UDP, ICMP
    val sizeBytes: Int,
    val appName: String,       // YouTube, Chrome, System
    val contentType: String,   // Video 4K, Image, Text
    val isRisk: Boolean
)
```

### AnalysisResult
```kotlin
data class AnalysisResult(
    val packageName: String,
    val trafficType: String,   // VIDEO_STREAM_4K, WEB_NAVIGATION
    val server: String,        // "Google Video Cache (Mountain View, US)"
    val riskScore: Int,        // 0-100
    val insight: String        // Analysis description
)
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Physical Android device (API 29+) - *Emulator VPN has limitations*
- USB debugging enabled
- Kotlin 1.9.22+

### Installation

```bash
# Clone the repository
git clone https://github.com/adityanair2509/DataSentry.git
cd DataSentry

# Open in Android Studio
# File → Open → Select DataSentry folder

# Build and run on connected device
# Click Run ▶️ or use: ./gradlew installDebug
```

### First Launch

1. Grant VPN permission when prompted
2. Review the app permissions and data collection disclosure
3. **Optional**: Enable server analytics for advanced cloud-based analysis
   - Tap **Settings** → **Server Analytics**
   - Review the **Data Collection Manifest** showing exactly what will be sent:
     - Network metadata (no payload content)
     - Anonymized device information
     - App package names and versions
   - Toggle **Enable Server Mode** if you consent
4. Tap **"ACTIVATE FIREWALL"** to start monitoring
5. Watch real-time processing updates as traffic is analyzed
6. Receive instant notifications for suspicious apps or domains
7. Tap **"DEACTIVATE FIREWALL"** to stop

### Real-Time Features

Once activated, you'll see:
- **Live Traffic Feed**: Packets appear instantly with color-coded risk indicators
- **Processing Status**: Visual indicators showing analysis progress
- **Smart Alerts**: Notifications for high-risk domains or suspicious behavior
- **Privacy Score**: Dynamic health score that updates based on current activity
- **VirusTotal Results**: Automatic threat intelligence checks (when available)

---

## 🔒 Security & Privacy

DataSentry is designed with privacy-first principles:
- **Local-First Architecture**: All analysis happens on-device by default
- **AES-256 Encryption**: Traffic logs and databases are encrypted with military-grade encryption
- **Secure Key Storage**: Encryption keys managed via Android Keystore (hardware-backed)
- **Transparent Server Mode**: When server analytics are enabled, users are clearly informed
  - Pre-connection disclosure of all data collection
  - Detailed manifest of what data is sent
  - One-tap opt-out at any time
- **No Content Inspection**: Only packet metadata is analyzed, never payload content
- **Private App Storage**: All local data stored in app-private directories
- **No Third-Party Tracking**: VirusTotal integration uses secure API calls only

---

## 📁 Project Structure

```
app/src/main/java/com/datasentry/app/
├── MainActivity.kt                 # Entry point, VPN permission handling
├── DataSentryService.kt           # Core VPN service implementation
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt         # Room database configuration
│   │   ├── entity/
│   │   │   └── PacketEntity.kt    # Traffic log data model
│   │   └── dao/
│   │       └── PacketDao.kt       # Database operations
│   ├── model/
│   │   ├── FlowStats.kt           # Traffic statistics
│   │   ├── SuspiciousEvent.kt     # Security alerts
│   │   └── RiskLevel.kt           # Risk classification enum
│   └── repository/
│       └── PacketRepository.kt    # Data access abstraction
│
├── demo/
│   ├── AnalysisResult.kt          # Analysis result model
│   └── DemoScenarioEngine.kt      # Traffic profiling engine
│
├── inspector/
│   └── TrafficInspector.kt        # Deep packet inspection
│
└── presentation/
    └── dashboard/
        ├── DashboardScreen.kt     # Compose UI
        └── DashboardViewModel.kt  # UI state management
```

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 1.9.22 |
| UI Framework | Jetpack Compose (Material3) |
| Architecture | MVVM + Clean Architecture |
| Database | Room 2.6.1 + SQLCipher |
| Encryption | AES-256 (Android Keystore) |
| Async | Coroutines + Flow |
| Network | Android VpnService API |
| Threat Intelligence | VirusTotal API v3 |
| Machine Learning | TensorFlow Lite (Behavior Analysis) |
| Notification | Android NotificationManager API |
| Build | Gradle 8.2.2 (Kotlin DSL) |
| Server | Python 3.10+ (Linux Analytics Server) |

---

## 👥 Team

**Overwatchers**

- **Aditya Nair** - Lead Developer - [@adityanair2509](https://github.com/adityanair2509)
- **Hridayshri Dave** - UI/UX Design - [@hridaydave25](https://github.com/hridaydave25)
- **Tanay Sagar** - Backend Architecture - [@tanaysagar](https://github.com/tanaysagar)
- **Bhagirath** - Testing & Documentation - [@b8matrix](https://github.com/b8matrix)

---

## 📄 License

This project is developed for educational and research purposes. 

---

<div align="center">

**Built with ❤️ by Team Overwatchers**

</div>
