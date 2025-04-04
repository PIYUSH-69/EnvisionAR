package com.invictus.envisionar.screens.SideNav

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.invictus.envisionar.screens.ArScreen
import com.invictus.envisionar.R

data class ModelItem(
    val name: String,
    val imageurl: String ,
    val modelurl : String// Resource ID of the model image
)

@Composable
fun ThreeDmodelScreen() {
    val context = LocalContext.current
    val models = listOf(
        ModelItem("Ancient Vase", "https://piyush-69.github.io/3d-models/AncientVase.JPG", "https://piyush-69.github.io/3d-models/AncientVase.glb"),
        ModelItem("Damaged Helmet", "https://piyush-69.github.io/3d-models/DamagedHelmet (1).JPG", "https://piyush-69.github.io/3d-models/DamagedHelmet (1).glb"),
        ModelItem("Bed", "https://piyush-69.github.io/3d-models/bed.JPG", "https://piyush-69.github.io/3d-models/bed.glb"),
        ModelItem("Black Leather Chair", "https://piyush-69.github.io/3d-models/black_leather_chair.JPG", "https://piyush-69.github.io/3d-models/black_leather_chair.glb"),
        ModelItem("Bookcase", "https://piyush-69.github.io/3d-models/bookcase_wood_and_metal.JPG", "https://piyush-69.github.io/3d-models/bookcase_wood_and_metal.glb"),
        ModelItem("Brown Drawer", "https://piyush-69.github.io/3d-models/brown_drawer_drawer_white_stains.JPG", "https://piyush-69.github.io/3d-models/brown_drawer_drawer_white_stains.glb"),
        ModelItem("Box Desk", "https://piyush-69.github.io/3d-models/box_desk-large.JPG", "https://piyush-69.github.io/3d-models/box_desk-large.glb"),
        ModelItem("Box Dresser", "https://piyush-69.github.io/3d-models/box_dresser_single_large.JPG", "https://piyush-69.github.io/3d-models/box_dresser_single_large.glb"),
        ModelItem("Box Framework", "https://piyush-69.github.io/3d-models/box_framework-desk.JPG", "https://piyush-69.github.io/3d-models/box_framework-desk.glb"),
        ModelItem("Box Posing Rocking Chair", "https://piyush-69.github.io/3d-models/box_posing_rocking_chair.JPG", "https://piyush-69.github.io/3d-models/box_posing_rocking_chair.glb"),
        ModelItem("Box Energy Shelf", "https://piyush-69.github.io/3d-models/box_energy_shelf_glb_cooking.JPG", "https://piyush-69.github.io/3d-models/box_energy_shelf_glb_cooking.glb"),
        ModelItem("Indoor Plant", "https://piyush-69.github.io/3d-models/indoor_plant.JPG", "https://piyush-69.github.io/3d-models/indoor_plant.glb"),
        ModelItem("Large Corner Sectional Sofa", "https://piyush-69.github.io/3d-models/large_corner_sectional.JPG", "https://piyush-69.github.io/3d-models/large_corner_sectional.glb"),
        ModelItem("Modern Entertainment Center", "https://piyush-69.github.io/3d-models/modern_entertainment_center_frame.JPG", "https://piyush-69.github.io/3d-models/modern_entertainment_center_frame.glb"),
        ModelItem("Modern Metal Frame Sofa", "https://piyush-69.github.io/3d-models/modern_metal_frame_sofa.JPG", "https://piyush-69.github.io/3d-models/modern_metal_frame_sofa.glb"),
        ModelItem("Minimalistic Shelf", "https://piyush-69.github.io/3d-models/minim_fidelity_s-2024-07-10.JPG", "https://piyush-69.github.io/3d-models/minim_fidelity_s-2024-07-10.glb"),
        ModelItem("Office Chair", "https://piyush-69.github.io/3d-models/office_chair.JPG", "https://piyush-69.github.io/3d-models/office_chair.glb"),
        ModelItem("Simple Chair", "https://piyush-69.github.io/3d-models/simple_chair.JPG", "https://piyush-69.github.io/3d-models/simple_chair.glb"),
        ModelItem("Simple Jacket", "https://piyush-69.github.io/3d-models/simple_jacket_210cm.JPG", "https://piyush-69.github.io/3d-models/simple_jacket_210cm.glb"),
        ModelItem("Office Frame Work", "https://piyush-69.github.io/3d-models/office_frame_working.JPG", "https://piyush-69.github.io/3d-models/office_frame_working.glb"),
        ModelItem("Vintage Desk Lamp", "https://piyush-69.github.io/3d-models/vintage_desk_lamp.JPG", "https://piyush-69.github.io/3d-models/vintage_desk_lamp.glb"),
        ModelItem("Wood Table", "https://piyush-69.github.io/3d-models/wood_table.JPG", "https://piyush-69.github.io/3d-models/wood_table.glb")
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 items per row
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(models.size) { index ->
            ModelCard(models[index], context)
        }
    }
}

@Composable
fun ModelCard(model: ModelItem, context: android.content.Context) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp), // Explicitly remove extra bottom padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model.imageurl),
                contentDescription = model.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = model.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    val intent = Intent(context, ArScreen::class.java).apply {
                        putExtra("MODEL_URL", model.imageurl)
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF271DB9)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(1.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.vr), // Vector drawable
                    contentDescription = "AR Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Open in AR", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}



