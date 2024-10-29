package com.example.travelsouvenir

import com.example.travelsouvenir.data.PlaceDao
import com.example.travelsouvenir.data.PlaceEntity
import com.example.travelsouvenir.data.PlaceRepositoryImpl
import com.example.travelsouvenir.data.api.OriginalImage
import com.example.travelsouvenir.data.api.WikipediaApiService
import com.example.travelsouvenir.data.api.WikipediaPlaceResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*

class PlaceRepositoryImplTest {

    private val mockPlaceDao = mock(PlaceDao::class.java)
    private val mockApiService = mock(WikipediaApiService::class.java)
    private val repository = PlaceRepositoryImpl(mockPlaceDao, mockApiService)

    @Test
    fun testInsertPlace() = runBlocking {
        val place = PlaceEntity(name = "Test Place", description = "Description", detailedDescription = "Detailed", originalImage = "image_url")

        `when`(mockPlaceDao.getPlaceByName(place.name)).thenReturn(null)

        repository.insertPlace(place)

        verify(mockPlaceDao).insertPlace(place)
    }

    @Test
    fun testFetchPlaceInfo() = runBlocking {
        val title = "Test Title"
        val response = WikipediaPlaceResponse("Title", OriginalImage("image_url"), "Description", "Detailed")

        `when`(mockApiService.getPlaceInfo(title)).thenReturn(response)

        val result = repository.fetchPlaceInfo(title)

        assertEquals(response.toPlaceEntity(), result)
    }

    @Test
    fun testGetAllPlaces() = runBlocking {
        val places = listOf(PlaceEntity(name = "Place1"), PlaceEntity(name = "Place2"))
        `when`(mockPlaceDao.getAllPlaces()).thenReturn(places)

        val result = repository.getAllPlaces()

        assertEquals(places, result)
    }
}
