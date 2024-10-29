package com.example.travelsouvenir.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelsouvenir.utils.GalleryHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryHelper: GalleryHelper
) : ViewModel() {

    private val _photos = MutableStateFlow<List<Uri>>(emptyList())
    val photos: StateFlow<List<Uri>> get() = _photos

    private val _detailedDescription = MutableStateFlow("")
    val detailedDescription: StateFlow<String> get() = _detailedDescription

    private val _placeName = MutableStateFlow("")
    val placeName: StateFlow<String> get() = _placeName

    // Function to load gallery data
    fun loadGalleryData(photos: List<Uri>, detailedDescription: String, placeName: String) {
        _detailedDescription.value = detailedDescription
        _placeName.value = placeName

        viewModelScope.launch {
            // Fetch photos from the album based on the place name (city name)
            val photoUris = galleryHelper.fetchPhotosFromAlbum(placeName)
            _photos.value = photoUris
        }
    }
}
