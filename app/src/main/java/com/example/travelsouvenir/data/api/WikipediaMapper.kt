package com.example.travelsouvenir.data.api

import com.example.travelsouvenir.data.PlaceEntity

object WikipediaMapper {
    fun mapToPlaceEntity(response: WikipediaPlaceResponse): PlaceEntity {
        return PlaceEntity(
            name = response.title,
            description = response.description,
            originalImage = response.originalImage?.source,
            detailedDescription = response.detailedDescription
        )
    }
}