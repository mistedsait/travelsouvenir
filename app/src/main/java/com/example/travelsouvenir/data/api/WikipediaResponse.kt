package com.example.travelsouvenir.data.api

import com.example.travelsouvenir.data.PlaceEntity
import com.google.gson.annotations.SerializedName

data class WikipediaPlaceResponse (
    @SerializedName("title") val title: String,
    @SerializedName("originalimage") val originalImage: OriginalImage?,
    @SerializedName("description") val description: String,
    @SerializedName("extract") val detailedDescription: String) {

    fun toPlaceEntity(): PlaceEntity {
        return PlaceEntity(
            name = title,
            description = description,
            originalImage = originalImage?.source,
            detailedDescription = detailedDescription
        )
    }
}

data class OriginalImage(
    @SerializedName("source") val source: String
)
//companion metoda, iz place respoinsa u place entity

//POZIVI SA ACCESS TOKEN, REFRESH TOKEN, ANIMACIJE UBACITI,