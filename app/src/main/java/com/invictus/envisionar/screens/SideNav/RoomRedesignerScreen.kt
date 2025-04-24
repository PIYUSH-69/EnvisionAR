package com.invictus.envisionar.screens.SideNav

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.invictus.roomdesign.repository.FlaskApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomRedesignerScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var redesignedImage by remember { mutableStateOf<Bitmap?>(null) }
    val flaskApi = FlaskApi()
    var isLoading by remember { mutableStateOf(false) }
    val houseParts = listOf("Living Room", "Bedroom", "Kitchen", "Bathroom")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(houseParts[0]) }
    val scrollState = rememberScrollState()


    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val tempUri = saveBitmapToCache(context, it)
            imageUri = tempUri
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Enable scrolling
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Upload a Empty Room Image to redesign it", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))


            if (imageUri != null) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.padding(8.dp)
                        .fillMaxWidth() // Ensure it fills the width
                        .heightIn(max = 300.dp) // Set a maximum height
                ) {
                    Image(
                        painter = rememberImagePainter(imageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentScale = ContentScale.Crop
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0C4B1)), // Pastel Green
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(2.dp), // Low Elevation for a Flat Look
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.Image, contentDescription = "Gallery", tint = Color(0xFF4A4A4A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select from Gallery", color = Color(0xFF4A4A4A)) // Dark Gray Text
            }

            // Camera Button
            Button(
                onClick = { cameraLauncher.launch(null) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9FA8DA)), // Pastel Purple
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.PhotoCamera, contentDescription = "Camera", tint = Color(0xFF4A4A4A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Capture from Camera", color = Color(0xFF4A4A4A))
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Room") },
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    houseParts.forEach { part ->
                        DropdownMenuItem(
                            text = { Text(part) },
                            onClick = {
                                selectedText = part
                                expanded = false
                            }
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (
                        imageUri == null
                    ){
                        Toast.makeText(context, "Please add a Image", Toast.LENGTH_SHORT).show()
                        return@Button
                    }


                    val roomDescriptions = mapOf(
                        "Living Room" to "a minimalist interior design of a cozy (((living room)))",
                        "Bedroom" to "a minimalist interior design of a (((stylish bedroom)))",
                        "Kitchen" to "a minimalist interior design of a (((modern kitchen)))",
                        "Bathroom" to "a minimalist interior design of an (((elegant bathroom)))"
                    )

                    val roomFurnitures = mapOf(
                        "Living Room" to "TV, couch, coffee table, floor lamp, wall art, pillows",
                        "Bedroom" to "bed, side tables, reading lamp, wardrobe, curtains, cozy rug",
                        "Kitchen" to "modular cabinets, island counter, overhead lights, bar stools, dining table",
                        "Bathroom" to "bathtub, vanity mirror, warm lighting, towel rack, minimal shelves"
                    )

                    val selectedRoomDescription = roomDescriptions[selectedText] ?: "a minimalist interior design of a modern space"
                    val selectedFurnitures = roomFurnitures[selectedText] ?: "TV, couch, table, lamp"



                    val prompt = "minimalist interior design ((($selectedText))) with full furnitures: TV, living room Couch, table, lamp, "
                    "Wall art, pillow, ((dark and moody atmosphere)), (((dim lighting))), warm incandescent lights, optical fiber, "
                    "capricious lighting, ray tracing reflections, (((black and yellow color palette))), dark brown walls and ceiling, "
                    "high contrast shadows, deep ambient lighting, cinematic low-key lighting, cozy and intimate environment --ar 16:9 --v 5.2"



                    val prompt2 = "$selectedRoomDescription with full furnitures: $selectedFurnitures, " +
                            "((dark and moody atmosphere)), (((dim lighting))), warm incandescent lights, optical fiber, " +
                            "capricious lighting, ray tracing reflections, (((black and yellow color palette))), dark brown walls and ceiling, " +
                            "high contrast shadows, deep ambient lighting, cinematic low-key lighting, cozy and intimate environment --ar 16:9 --v 5.2"

                    isLoading = true
                    redesignedImage= null// Show loading indicator
                    imageUri?.let { uri ->
                        val file = uriToFile(uri, context)
                        file?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                val result = flaskApi.redesignRoom(it, prompt2)
                                if (result == null) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(context, "Error in processing. Please check is Server is live", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    redesignedImage = result
                                }
                                isLoading = false  // Hide loading indicator

                            }
                        }
                    } ?: Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                } ,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000991)), // Muted Blue-Gray
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Redesign", color = Color(0xFFFFFFFF)) // Darker Text for Contrast
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            }

            redesignedImage?.let { bitmap ->
                Text("Redesigned Image", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.padding(8.dp) // Adds spacing around the card
                ) {

                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Redesigned Image",
                            modifier = Modifier
                                .fillMaxWidth() // Ensure it fills the width
                                .heightIn(max = 300.dp) ,// Set a maximum height
                            contentScale = ContentScale.Crop
                        )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        saveImageToGallery(context, bitmap, "redesigned_image_${System.currentTimeMillis()}.png")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // Pastel Purple
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                ) {
                    Icon(imageVector = Icons.Filled.Download, contentDescription = "Download", tint = Color(0xFF4A4A4A))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download Image", color = Color(0xFF4A4A4A))
                }

            }
        }

    }

}

fun saveImageToGallery(context: Context, bitmap: Bitmap, fileName: String) {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/RedesignedImages")
    }

    val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        val outputStream = resolver.openOutputStream(uri)
        if (outputStream != null) {
            outputStream.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to open output stream", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Failed to create image URI", Toast.LENGTH_SHORT).show()
    }
}



fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.cacheDir, "captured_image.png")
    return try {
        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }

}


private fun uriToFile(uri: Uri, context: Context): File? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "temp_image.jpg")

    return try {
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
