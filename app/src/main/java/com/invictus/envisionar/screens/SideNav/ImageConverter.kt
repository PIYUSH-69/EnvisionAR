package com.invictus.envisionar.screens.SideNav

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.invictus.envisionar.VrScreen
import com.invictus.envisionar.screens.ArScreen
import kotlinx.coroutines.delay

@Composable
fun ImageConverter(navController: NavController) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Add scroll here
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "2D Image to 3D Model Convertor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A3FC1)
            )

            Spacer(modifier = Modifier.height(16.dp))

            UploadImageCard(selectedImageUri, selectedBitmap) { uri, bitmap ->
                selectedImageUri = uri
                selectedBitmap = bitmap
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedImageUri != null) {
                ProcessingOptionsCard { newValue -> boolean = newValue } // Pass setter
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (boolean){
                Spacer(modifier = Modifier.height(16.dp))
                ThreeDModelPreviewCard(context, navController = navController)
            }

        }
    }
}

@Composable
fun UploadImageCard(selectedImageUri: Uri?, selectedBitmap: Bitmap?, onImageSelected: (Uri?, Bitmap?) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f), // Adjust width for better centering
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp) // Increased Image Size
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (selectedBitmap != null) {
                    Image(
                        bitmap = selectedBitmap.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Upload",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp) // Increased Icon Size
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Upload 2D Image", fontWeight = FontWeight.Bold)
            Text("Upload your image to convert to 3D", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            UploadImageButton(onImageSelected)
        }
    }
}

@Composable
fun ProcessingOptionsCard(setBoolean: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Processing Options", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            var selectedQuality by remember { mutableStateOf("High") }
            DropdownMenuField("Quality", listOf("Low", "Medium", "High")) {
                selectedQuality = it
            }

            Spacer(modifier = Modifier.height(8.dp))

            var selectedFormat by remember { mutableStateOf("GLB") }
            DropdownMenuField("Format", listOf("GLB", "OBJ", "STL")) {
                selectedFormat = it
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { setBoolean(true) }, // Update boolean state
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF5A3FC1))
            ) {
                Text("Convert to 3D", color = Color.White)
            }
        }
    }
}

@Composable
fun ThreeDModelPreviewCard(context: android.content.Context, navController: NavController) {
    val modelUrl = "https://modelviewer.dev/shared-assets/models/Astronaut.glb" // Replace with your model URL
    var isLoading by remember { mutableStateOf(true) }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                text = "Output 3D Model",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center// Ensures text alignment within the column


            )

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "3D Preview",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


@Composable
fun UploadImageButton(onImageSelected: (Uri?, Bitmap?) -> Unit) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> onImageSelected(uri, null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? -> onImageSelected(null, bitmap) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text(text = "Choose from Gallery")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { cameraLauncher.launch(null) }) {
            Text(text = "Capture from Camera")
        }
    }
}

@Composable
fun DropdownMenuField(label: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(12.dp)
        ) {
            Text(text = selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOption = option
                    expanded = false
                    onSelected(option)
                }, text = { Text(option) })
            }
        }
    }
}
