package com.example.travelsouvenir.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places WHERE name = :placeName LIMIT 1")
    suspend fun getPlaceByName(placeName: String): PlaceEntity?

    @Query("SELECT detailed_description FROM places WHERE name = :placeName LIMIT 1")
    suspend fun getDetailedDescription(placeName: String): String?

    @Insert
    suspend fun insertPlace(place: PlaceEntity)

    @Query("SELECT * FROM places")
    suspend fun getAllPlaces(): List<PlaceEntity>
}
