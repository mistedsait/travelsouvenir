package com.example.travelsouvenir.data

import com.example.travelsouvenir.data.api.WikipediaApiService
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeDao: PlaceDao,
    private val wikipediaApiService: WikipediaApiService
) : PlaceRepository {

    override suspend fun insertPlace(place: PlaceEntity) {
        val existingPlace = placeDao.getPlaceByName(place.name)
        if (existingPlace == null) {
            placeDao.insertPlace(place)
        }
    }

    override suspend fun fetchPlaceInfo(title: String): PlaceEntity {
        val response = wikipediaApiService.getPlaceInfo(title)
        return response.toPlaceEntity()
       //return WikipediaMapper.mapToPlaceEntity(response)
    }

    override suspend fun getAllPlaces(): List<PlaceEntity> {
        return placeDao.getAllPlaces()
    }

    override suspend fun getDetailedDescription(placeName: String): String? {
        return placeDao.getDetailedDescription(placeName)
    }
}

