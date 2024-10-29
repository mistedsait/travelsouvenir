package com.example.travelsouvenir.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelsouvenir.data.PlaceRepository
import com.example.travelsouvenir.pages.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places



    fun loadPlaces(titles: List<String>) {
        viewModelScope.launch {
            val currentPlaces = mutableListOf<Place>()
            for (title in titles) {
                try {
                    // Fetching place info from repository
                    val placeEntity = placeRepository.fetchPlaceInfo(title)
                    placeRepository.insertPlace(placeEntity)

                    val place = Place(
                        id = placeEntity.id.toInt(),  // Use actual ID if available
                        name = placeEntity.name,
                        description = placeEntity.description,
                        originalImage = placeEntity.originalImage ?: "",
                        detailedDescription = placeEntity.detailedDescription
                    )
                    currentPlaces.add(place)

                    Log.d("API_SUCCESS", "Successfully fetched data for $title")
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error fetching data for $title", e)
                }
            }
            _places.value = currentPlaces
        }
    }

    fun loadPlacesFromDatabase() {
        viewModelScope.launch {
            val placeEntities = placeRepository.getAllPlaces()
            val places = placeEntities.map { entity ->
                Place(
                    id = entity.id.toInt(),
                    name = entity.name,
                    description = entity.description,
                    originalImage = entity.originalImage ?: "",
                    detailedDescription = entity.detailedDescription
                )
            }
            _places.value = places
        }
    }
}
