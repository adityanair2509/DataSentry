package com.datasentry.app.presentation.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.datasentry.app.security.ServerStats

data class AnalysisMode(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val features: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisSettingsScreen(
    currentMode: String,
    onModeChanged: (String) -> Unit,
    serverStats: ServerStats? = null,
    onNavigateBack: () -> Unit
) {
    val analysisModes = listOf(
        AnalysisMode(
            id = "on_device",
            title = "On-Device Analysis",
            description = "Quick checks using VirusTotal API",
            icon = Icons.Default.Lock,
            features = listOf(
                "✓ Real-time analysis",
                "✓ Privacy-focused",
                "✓ No server dependency",
                "✓ VirusTotal database (provided)",
                "✓ No setup required"
            )
        ),
        AnalysisMode(
            id = "server_deep",
            title = "Deep Analysis Server",
            description = "Advanced ML analysis and threat intelligence",
            icon = Icons.Default.Settings,
            features = listOf(
                "✓ Machine learning analysis",
                "✓ Pattern detection",
                "✓ Historical data analysis",
                "✓ Advanced threat intelligence",
                "✓ Real-time notifications"
            )
        )
    )

    val selectedMode = analysisModes.find { it.id == currentMode } ?: analysisModes[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Analysis Settings",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Analysis Mode Selection
        Text(
            text = "Choose Analysis Mode",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(modifier = Modifier.selectableGroup()) {
            analysisModes.forEach { mode ->
                AnalysisModeCard(
                    mode = mode,
                    isSelected = mode.id == selectedMode.id,
                    onSelected = { onModeChanged(mode.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Server URL Configuration
        ServerUrlSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Server Stats (only for server mode)
        if (selectedMode.id == "server_deep" && serverStats != null) {
            ServerStatsSection(serverStats = serverStats)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info Card
        InfoCard()
    }
}

@Composable
fun AnalysisModeCard(
    mode: AnalysisMode,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF2E7D32) else Color(0xFF1E1E1E)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .selectable(
                selected = isSelected,
                onClick = onSelected,
                role = Role.RadioButton
            ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = mode.icon,
                    contentDescription = null,
                    tint = if (isSelected) Color(0xFF4CAF50) else Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mode.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mode.description,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                RadioButton(
                    selected = isSelected,
                    onClick = onSelected,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF4CAF50),
                        unselectedColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            mode.features.forEach { feature ->
                Text(
                    text = feature,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun VirusTotalApiKeySection(
    apiKey: String,
    onKeyChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "VirusTotal API Key",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = apiKey,
                onValueChange = onKeyChanged,
                label = { Text("Enter API Key", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color.Gray
                )
            )
            
            Text(
                text = "Get your free API key from virustotal.com",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ServerStatsSection(serverStats: ServerStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Server Statistics",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Total Domains",
                    value = serverStats.totalDomains.toString(),
                    color = Color(0xFF4CAF50)
                )
                StatItem(
                    label = "Risk Domains",
                    value = serverStats.riskDomains.toString(),
                    color = Color(0xFFFF5252)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Storage Used",
                    value = serverStats.storageUsed,
                    color = Color(0xFF2196F3)
                )
                StatItem(
                    label = "Last Analysis",
                    value = serverStats.lastAnalysis,
                    color = Color(0xFF9C27B0)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ServerUrlSection() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var serverUrl by remember { mutableStateOf("") }
    
    // Load current server URL
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("datasentry_prefs", Context.MODE_PRIVATE)
        serverUrl = prefs.getString("server_url", "http://192.168.1.100:5000") ?: "http://192.168.1.100:5000"
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Server URL Configuration",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            OutlinedTextField(
                value = serverUrl,
                onValueChange = { serverUrl = it },
                label = { Text("Server URL", color = Color.Gray) },
                placeholder = { Text("http://your-ngrok-url.ngrok.io", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = {
                    context.getSharedPreferences("datasentry_prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putString("server_url", serverUrl)
                        .apply()
                    android.widget.Toast.makeText(context, "Server URL updated", android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Update Server URL", color = Color.White)
            }
        }
    }
}

@Composable
fun InfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Privacy & Security",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                text = "• On-Device: Data never leaves your phone\n• Server Mode: Encrypted transmission and storage\n• You can switch modes anytime\n• All data is encrypted end-to-end",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}
