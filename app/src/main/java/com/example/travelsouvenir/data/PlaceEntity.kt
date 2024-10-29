package com.example.travelsouvenir.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travelsouvenir.data.api.WikipediaPlaceResponse

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    @ColumnInfo(name = "original_image") val originalImage: String? = null,
    @ColumnInfo(name= "detailed_description") val detailedDescription: String = "",
) {
    companion object {
        fun fromWikipediaResponse(response: WikipediaPlaceResponse): PlaceEntity {
            return PlaceEntity(
                name = response.title,
                description = response.description,
                originalImage = response.originalImage?.source,
                detailedDescription = response.detailedDescription
            )
        }
    }
}
