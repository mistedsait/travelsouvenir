package com.example.travelsouvenir.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PhotoHelper(private val context: Context) {

    fun createAlbumAndSavePhoto(cityName: String, imageUri: Uri) {
        val albumName = cityName.replace(" ", "_")
        val albumDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName)

        if (!albumDir.exists()) {
            if (albumDir.mkdirs()) {
                Log.d("Album", "Album directory created: $albumDir")
            } else {
                Log.d("Album", "Failed to create album directory: $albumDir")
                return
            }
        }

        val newFile = File(albumDir, "photo_${System.currentTimeMillis()}.jpg")
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val outputStream = FileOutputStream(newFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("Album", "Photo saved to: ${newFile.absolutePath}")
        } catch (e: Exception) {
            Log.e("Album", "Error saving photo: ${e.message}")
        }
    }
}