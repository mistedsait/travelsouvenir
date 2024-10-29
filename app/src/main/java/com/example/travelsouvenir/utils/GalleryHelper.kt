package com.example.travelsouvenir.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import javax.inject.Inject

class GalleryHelper @Inject constructor(private val context: Context) {

    fun fetchPhotosFromAlbum(cityName: String): List<Uri> {
        val albumName = cityName.replace(" ", "_")
        val albumDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName)

        val photoUris = mutableListOf<Uri>()

        if (albumDir.exists() && albumDir.isDirectory) {
            val photos = albumDir.listFiles { file -> file.extension.lowercase() == "jpg" }
            photos?.forEach { file ->
                photoUris.add(Uri.fromFile(file))
            }
        } else {
            Log.d("Album", "Album directory does not exist: $albumDir")
        }

        return photoUris
    }
}