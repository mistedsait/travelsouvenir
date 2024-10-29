package com.example.travelsouvenir.pages

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int,
    val name: String,
    val description: String,
    val originalImage: String,
    val detailedDescription: String
)

@Serializable
data class Places(
    val places: List<Place>
)
