# DataSentry 🛡️

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

DataSentry is a sophisticated Android application that provides DNS-based network traffic analysis capabilities. Built on Android's VpnService API, it creates a local VPN tunnel to intercept DNS queries, enabling comprehensive monitoring of app communications, data flows, and potential privacy threats.

### Why DataSentry?

In an era where mobile applications constantly communicate with remote servers, users have little visibility into what data is being transmitted. DataSentry bridges this gap by providing:

- **Complete DNS Visibility**: See every domain your device connects to
- **Real App Attribution**: Know exactly which apps are sending data using TrafficStats API
- **Session-Based Analysis**: Traffic grouped into meaningful app sessions
- **Tracker Detection**: Automatic identification of analytics, ads, and telemetry domains
- **Privacy Scoring**: Real-time assessment of your device's privacy health
- **Real App Icons**: Display actual app icons via package name resolution

---

## 🆕 Recent Updates

### TrafficStats-Based App Identification
- **Real Package Detection**: Uses Android's TrafficStats API to identify which app generates each network request
- **Accurate App Icons**: Displays real app icons instead of heuristic-based guessing
- **Traffic Correlation**: Matches DNS queries to apps based on traffic deltas


### Session-Based Analysis
- Groups packets into meaningful app sessions
- Privacy impact scoring (Low/Medium/High)
- Tracker detection with known pattern matching

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
│  │  DataSentryService  │    │       DnsOnlyHandler            ││
│  │    (VpnService)     │───►│   (DNS Query Capture)           ││
│  └─────────────────────┘    └─────────────────────────────────┘│
│  ┌─────────────────────┐    ┌─────────────────────────────────┐│
│  │  TrafficStatsHelper │    │      SessionAggregator          ││
│  │ (Per-App Tracking)  │    │  (Session-Based Analysis)       ││
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
│  │    PacketEntity     │    │     AppSession Model            ││
│  │   (Traffic Log)     │    │   (Aggregated Sessions)         ││
│  └─────────────────────┘    └─────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔧 Core Components

### 1. DataSentryService.kt - VPN Engine

The heart of the application. This service extends `VpnService` to create a local VPN tunnel.

**Key Responsibilities:**
- Establishes VPN interface for DNS-only capture
- Manages foreground service with persistent notification
- Coordinates with DnsOnlyHandler for query logging
- Handles service lifecycle (start/stop/revoke)

### 2. DnsOnlyHandler.kt - DNS Query Capture

Captures and forwards DNS queries while logging domain requests.

**Capabilities:**
- DNS query interception (UDP port 53)
- Domain name extraction from DNS packets
- Real-time logging to Room database
- TrafficStats integration for app identification

### 3. TrafficStatsHelper.kt - Per-App Traffic Tracking

Uses Android's TrafficStats API for real app identification.

**Features:**
- `getActiveApps()`: Find apps with recent traffic
- `findMostActiveApp(domain)`: Correlate domains to apps
- Per-app byte counting (rx/tx)
- Package name resolution for icon loading

```kotlin
fun findMostActiveApp(domainHint: String?): AppTrafficInfo? {
    // Match domain to known packages
    // Fall back to most active app by traffic delta
}
```

### 4. SessionAggregator.kt - Session Analysis

Groups packets into meaningful app sessions with privacy scoring.

**Analysis Features:**
- Session timeout detection (2 min inactivity)
- Tracker pattern matching
- Privacy impact scoring (Low/Medium/High)
- Domain and data size aggregation

### 5. AppDatabase.kt - Persistence Layer

Room database for storing all traffic logs and analysis results.

```kotlin
@Database(
    entities = [PacketEntity::class, FlowStats::class, SuspiciousEvent::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase()
```

---

## 📊 Data Models

### PacketEntity
```kotlin
data class PacketEntity(
    val id: Long,
    val timestamp: Long,
    val sourceIp: String,
    val destIp: String,        // Domain name
    val protocol: String,       // DNS
    val sizeBytes: Int,
    val appName: String,        // From TrafficStats
    val packageName: String,    // Real package for icon loading
    val contentType: String,    // Video, Social, Web, etc.
    val isRisk: Boolean         // Tracker detected
)
```

### AppSession
```kotlin
data class AppSession(
    val appName: String,
    val packageName: String,
    val startTime: Long,
    val endTime: Long,
    val domains: Set<String>,
    val trackers: List<String>,
    val totalBytes: Long,
    val privacyImpact: PrivacyImpact  // LOW, MEDIUM, HIGH
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
2. Tap **"ACTIVATE FIREWALL"** to start monitoring
3. Use your device normally - traffic logs appear in real-time
4. Tap **"DEACTIVATE FIREWALL"** to stop

---

## 🔒 Security & Privacy

DataSentry operates with a **metadata-only approach**:
- Captures DNS queries (domain names), not encrypted content
- All analysis happens locally on-device
- Traffic logs are stored in private app storage
- VPN tunnel terminates on the device itself
- Optional backend analytics via Linux server

---

## 📁 Project Structure

```
app/src/main/java/com/datasentry/app/
├── MainActivity.kt                 # Entry point, VPN permission handling
├── DataSentryService.kt           # Core VPN service implementation
│
├── vpn/
│   ├── DnsOnlyHandler.kt          # DNS query capture and forwarding
│   ├── TrafficStatsHelper.kt      # Per-app traffic tracking
│   └── PacketParser.kt            # IP/UDP packet parsing
│
├── analysis/
│   └── SessionAggregator.kt       # Session-based analysis engine
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt         # Room database (v4)
│   │   ├── entity/
│   │   │   └── PacketEntity.kt    # Traffic log with packageName
│   │   └── dao/
│   │       └── PacketDao.kt       # Database operations
│   ├── model/
│   │   ├── AppSession.kt          # Session data model
│   │   └── RiskLevel.kt           # Risk classification
│   └── repository/
│       └── PacketRepository.kt    # Data access abstraction
│
├── network/
│   └── AnalyticsClient.kt         # Optional server upload
│
└── presentation/
    ├── dashboard/
    │   ├── DashboardScreen.kt     # Main Compose UI
    │   └── DashboardViewModel.kt  # UI state management
    └── components/
        ├── SessionCard.kt         # Session display card
        └── AppIcon.kt             # Real app icon loader
```

---

## 🖥️ Linux Server (Optional)

Backend server for advanced analytics:

```bash
cd linux_server
pip install -r requirements.txt
python app.py
```

See `linux_server/README.md` for setup details.

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 1.9.22 |
| UI Framework | Jetpack Compose (Material3) |
| Architecture | MVVM + Clean Architecture |
| Database | Room 2.6.1 |
| Async | Coroutines + Flow |
| Network | Android VpnService + TrafficStats API |
| Build | Gradle 8.2.2 (Kotlin DSL) |
| Backend | Python/Flask (optional) |

---

## 👥 Team

**Overwatchers**

- **Aditya Nair** - Lead Developer - [@adityanair2509](https://github.com/adityanair2509)
- **Hridayshri Dave** - UI/UX Design - [@hridaydave25](https://github.com/hridaydave25)
- **Tanay Sagar** - Backend Architecture - [@tanaysagar](https://github.com/tanaysagar)
- **Bhagirath** - Testing & Analysis - [@b8matrix](https://github.com/b8matrix)

---

## 📄 License

This project is developed for educational and research purposes. 

---

<div align="center">

**Built with ❤️ by Team Overwatchers**

</div>
