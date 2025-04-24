package com.invictus.envisionar.screens.SideNav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.invictus.envisionar.R

@Composable
fun HomePageContent(onNavigate: (String) -> Unit) {
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.logo),
            contentDescription = "Selected Image",
            modifier = Modifier.fillMaxWidth().height(80.dp)
        )

        Text("Recent Models", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))


        LazyRow {
            items(2) { index ->
                ModelCard(
                    title = if (index == 0) "Modern Chair" else "Table Lamp",
                    date = if (index == 0) "Created 2 days ago" else "Created 3 days ago",
                    model = if (index == 0) "https://piyush-69.github.io/3d-models/office_chair.glb" else "https://piyush-69.github.io/3d-models/ikea_desk-lamp.glb",
                    imageurl = if (index == 0) "https://piyush-69.github.io/3d-models/office_chair.png" else "https://piyush-69.github.io/3d-models/ikea_desk-lamp.png"
                )
                Spacer(modifier = Modifier.width(0.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Actions Section
        Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        QuickActionCard(title = "Convert 2D to 3D", subtitle = "Transform your images into 3D models", backgroundColor = Color(0xFF6A5ACD), imageVector = Icons.Filled.Image, onNavigate =    { onNavigate("Image to 3D") } // ✅ Pass it as a lambda
        )
        QuickActionCard(
            title = "View in AR", subtitle = "Experience your models in AR", backgroundColor = Color(0xFF8A2BE2), drawableRes = R.drawable.vr,
            onNavigate = { onNavigate("3D Models") } // ✅ Pass it as a lambda
        )
        QuickActionCard(
            title = "Room Redesigner", subtitle = "Redesign your room with our AI", backgroundColor = Color(0xFF4169E1),R.drawable.cube,
            onNavigate = { onNavigate("Room Redesigner") } // ✅ Pass it as a lambda
        )
    }
}

@Composable
fun ModelCard(title: String, date: String, model: String , imageurl : String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(140.dp)
            .height(200.dp) // Ensures enough space for text
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), // Light gray background
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Adds a shadow effect
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Image section height
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Color.LightGray), // Placeholder background in case image fails to load
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageurl),
                    contentDescription = "Model Image",
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer(
                            scaleX = 1.5f, // 2x zoom
                            scaleY = 1.5f,
                ),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start=8.dp).fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date,
                fontSize = 10.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start=8.dp, bottom = 8.dp).fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }
    }
}


@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    drawableRes: Int? = null, // Drawable resource (R.drawable.your_icon)
    imageVector: ImageVector? = null,
    onNavigate: () -> Unit // ✅ Correct function type
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(80.dp)
            .clickable { onNavigate() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Show Drawable Resource if provided
            if (drawableRes != null) {
                Image(
                    painter = painterResource(id = drawableRes),
                    contentDescription = "Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White) // Tint icon white
                )
            }
            // Show ImageVector if provided
            else if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subtitle, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Arrow",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


