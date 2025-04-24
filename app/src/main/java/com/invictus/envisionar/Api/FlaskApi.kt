package com.invictus.roomdesign.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.util.concurrent.TimeUnit

class FlaskApi {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.MINUTES)
        .build()

    suspend fun redesignRoom(imageFile: File, positivePrompt: String): Bitmap? {
        return try {
            withContext(Dispatchers.IO) {
                val url = "http://525a-34-19-0-205.ngrok-free.app/redesign"  // Replace with your Flask API URL

                val negativePrompt = "(((Ugly))), low-resolution, morbid, blurry, cropped, deformed, dehydrated, text, disfigured, duplicate, error, extra arms, extra fingers, extra legs, extra limbs, fused fingers, gross proportions, jpeg artifacts, long neck, low resolution, tiling, poorly drawn feet, extra limbs, disfigured, body out of frame, cut off, low contrast, underexposed, overexposed, bad art, beginner, amateur, distorted face, low quality, lowres, low saturation, deformed body features, watermark, water mark"

                val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image", imageFile.name, RequestBody.create("image/*".toMediaTypeOrNull(), imageFile))
                    .addFormDataPart("positive_prompt", positivePrompt)
                    .addFormDataPart("negative_prompt", negativePrompt)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Log.e("FlaskApi", "Error: ${response.code} ${response.message}")
                    return@withContext null
                }

                response.body?.byteStream()?.let { inputStream ->
                    return@withContext BitmapFactory.decodeStream(inputStream)
                }
            }
        } catch (e: Exception) {
            Log.e("FlaskApi", "Exception: ${e.message}")
            null
        }
    }
}
