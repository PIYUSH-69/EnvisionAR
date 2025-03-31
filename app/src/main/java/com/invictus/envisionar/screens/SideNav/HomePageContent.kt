package com.invictus.envisionar.screens.SideNav

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePageContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Recent Models Section
        Text("Recent Models", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(2) { index ->
                ModelCard(
                    title = if (index == 0) "Modern Chair" else "Table Lamp",
                    date = if (index == 0) "Created 2 days ago" else "Created 3 days ago"
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Actions Section
        Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        QuickActionCard(title = "Convert 2D to 3D", subtitle = "Transform your images into 3D models", backgroundColor = Color(0xFF6A5ACD))
        QuickActionCard(title = "View in AR", subtitle = "Experience your models in AR", backgroundColor = Color(0xFF8A2BE2))
        QuickActionCard(title = "VR Mode", subtitle = "Immerse yourself in VR", backgroundColor = Color(0xFF4169E1))
    }
}

@Composable
fun ModelCard(title: String, date: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(120.dp)
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun QuickActionCard(title: String, subtitle: String, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(80.dp)
            .clickable { /* Handle click */ },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}
