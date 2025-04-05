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
        ModelItem("Ancient Vase", "https://piyush-69.github.io/3d-models/hydria_apothecary_vase.png", "https://piyush-69.github.io/3d-models/hydria_apothecary_vase.glb"),
        ModelItem("Damaged Helmet", "https://piyush-69.github.io/3d-models/DamagedHelmet.png", "https://piyush-69.github.io/3d-models/DamagedHelmet.glb"),
        ModelItem("Bed King Sized", "https://piyush-69.github.io/3d-models/bed.png", "https://piyush-69.github.io/3d-models/bed.glb"),
        ModelItem("Black Leather Chair", "https://piyush-69.github.io/3d-models/black_leather_chair.png", "https://piyush-69.github.io/3d-models/black_leather_chair.glb"),
        ModelItem("Bookcase", "https://piyush-69.github.io/3d-models/bookcase_wood_and_metal.png", "https://piyush-69.github.io/3d-models/bookcase_wood_and_metal.glb"),
        ModelItem("Drawer", "https://piyush-69.github.io/3d-models/ikea_8-drawer_dresser_white_stain.png", "https://piyush-69.github.io/3d-models/ikea_8-drawer_dresser_white_stain.glb"),
        ModelItem("Desk", "https://piyush-69.github.io/3d-models/antique_desk.png", "https://piyush-69.github.io/3d-models/antique_desk.glb"),
        ModelItem("Desk Lamp", "https://piyush-69.github.io/3d-models/ikea_desk-lamp.png", "https://piyush-69.github.io/3d-models/ikea_desk-lamp.glb"),
        ModelItem("Bed Single", "https://piyush-69.github.io/3d-models/ikea_idanas_single_bed.png", "https://piyush-69.github.io/3d-models/ikea_idanas_single_bed.glb"),
        ModelItem("Box Posing Rocking Chair", "https://piyush-69.github.io/3d-models/ikea_poang-rocking-chair.png", "https://piyush-69.github.io/3d-models/ikea_poang-rocking-chair.glb"),
        ModelItem("Box Energy Shelf", "https://piyush-69.github.io/3d-models/vittsjo_-_ikea.jpg", "https://piyush-69.github.io/3d-models/vittsjo_-_ikea.glb"),
        ModelItem("Indoor Plant", "https://piyush-69.github.io/3d-models/indoor_plant.png", "https://piyush-69.github.io/3d-models/indoor_plant.glb"),
        ModelItem("Large Corner Sectional Sofa", "https://piyush-69.github.io/3d-models/large_corner_sectional_sofa.png", "https://piyush-69.github.io/3d-models/large_corner_sectional_sofa.glb"),
        ModelItem("TV unit", "https://piyush-69.github.io/3d-models/modern_entertainment_center_free.png", "https://piyush-69.github.io/3d-models/modern_entertainment_center_free.glb"),
        ModelItem("Modern Metal Frame Sofa", "https://piyush-69.github.io/3d-models/modern_metal_frame_sofa.png", "https://piyush-69.github.io/3d-models/modern_metal_frame_sofa.glb"),
        ModelItem("Minimalistic Shelf", "https://piyush-69.github.io/3d-models/simple_tv_shelf_210cm.png", "https://piyush-69.github.io/3d-models/simple_tv_shelf_210cm.glb"),
        ModelItem("Office Chair", "https://piyush-69.github.io/3d-models/office_chair.png", "https://piyush-69.github.io/3d-models/office_chair.glb"),
        ModelItem("Simple Chair", "https://piyush-69.github.io/3d-models/simple_chair.png", "https://piyush-69.github.io/3d-models/simple_chair.glb"),
        ModelItem("Office Desk", "https://piyush-69.github.io/3d-models/office_desk_140x60.png", "https://piyush-69.github.io/3d-models/office_desk_140x60.glb"),
        ModelItem("Kitchen Cook Top", "https://piyush-69.github.io/3d-models/ikea_range_with_gas_cooktop.png", "https://piyush-69.github.io/3d-models/ikea_range_with_gas_cooktop.glb"),
        ModelItem("Statue", "https://piyush-69.github.io/3d-models/statuette_de_femme_antique.png", "https://piyush-69.github.io/3d-models/statuette_de_femme_antique.glb"),
        ModelItem("Wood Table", "https://piyush-69.github.io/3d-models/wood_table.jpg", "https://piyush-69.github.io/3d-models/wood_table.glb")
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
            .fillMaxWidth()
            .height(250.dp), // 🔥 Fixed height to ensure uniform card size
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model.imageurl),
                contentDescription = model.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp) // Adjusted to leave space for other content
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = model.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(context, ArScreen::class.java).apply {
                        putExtra("MODEL_URL", model.modelurl)
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF271DB9)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .height(36.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.vr),
                    contentDescription = "AR Icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Open in AR",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}




