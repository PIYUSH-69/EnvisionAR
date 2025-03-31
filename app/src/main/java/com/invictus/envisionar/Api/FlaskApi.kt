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
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun redesignRoom(imageFile: File, positivePrompt: String, negativePrompt: String): Bitmap? {
        return try {
            withContext(Dispatchers.IO) {
                val url = "http://192.168.0.106:5000/redesign"  // Replace with your Flask API URL

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
