package com.invictus.envisionar.screens.SideNav

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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

@Composable
fun RoomRedesignerScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var positivePrompt by remember { mutableStateOf("") }
    var negativePrompt by remember { mutableStateOf("") }
    val context = LocalContext.current
    var redesignedImage by remember { mutableStateOf<Bitmap?>(null) }
    val flaskApi = FlaskApi()
    val loading =false


    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val tempUri = saveBitmapToCache(context, it)
            imageUri = tempUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Room Redesigner", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))


        if (imageUri != null){

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                    Image(
                        painter = rememberImagePainter(imageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize()
                    )
            }


        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Select from Gallery")
        }

        Button(onClick = { cameraLauncher.launch(null) }) {
            Text("Capture from Camera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = positivePrompt,
            onValueChange = { positivePrompt = it },
            label = { Text("Positive Prompt") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = negativePrompt,
            onValueChange = { negativePrompt = it },
            label = { Text("Negative Prompt") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                imageUri?.let { uri ->
                    val file = uriToFile(uri, context)
                    file?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            redesignedImage = flaskApi.redesignRoom(it, positivePrompt, negativePrompt)
                        }
                    }
                } ?: Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Submit")
        }


        redesignedImage?.let { bitmap ->
            Text("Redesigned Image:")
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Redesigned Room",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

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
