package com.example.travelsouvenir

import androidx.lifecycle.Observer
import com.example.travelsouvenir.data.PlaceRepository
import com.example.travelsouvenir.pages.Place
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.travelsouvenir.data.PlaceEntity
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlacesViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Clean up the test dispatcher
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mock(PlaceRepository::class.java)
    private val viewModel = PlacesViewModel(mockRepository)

    @Test
    fun testLoadPlaces() = runBlocking {
        val titles = listOf("Place1", "Place2")
        val expectedPlaces = listOf(
            Place(1, "Place1", "Description1", "imageUrl1", "detailedDescription1"),
            Place(2, "Place2", "Description2", "imageUrl2",  "detailedDescription2")
        )

        val observer = mock(Observer::class.java) as Observer<List<Place>>

        viewModel.places.observeForever(observer)

        // Mock the PlaceEntity that the repository will return (after using the mapper)
        val placeEntity1 = PlaceEntity(1L, "Place1", "Description1", "imageUrl1", "detailedDescription1")
        val placeEntity2 = PlaceEntity(2L, "Place2", "Description2", "imageUrl2", "detailedDescription2")

        // Use mock repository to return these entities when fetchPlaceInfo is called
        `when`(mockRepository.fetchPlaceInfo("Place1")).thenReturn(placeEntity1)
        `when`(mockRepository.fetchPlaceInfo("Place2")).thenReturn(placeEntity2)

        // Call loadPlaces with the titles
        viewModel.loadPlaces(titles)

        // Verify that the observer received the correctly mapped Place objects
        verify(observer).onChanged(expectedPlaces)
    }

    @Test
    fun testLoadPlacesFromDatabase() = runBlocking {
        // Mock PlaceEntity data returned by the repository
        val placeEntities = listOf(
            PlaceEntity(1L, "Place1", "Description1", "imageUrl1", "detailedDescription1"),
            PlaceEntity(2L, "Place2", "Description2", "imageUrl2", "detailedDescription2")
        )
        val expectedPlaces = listOf(
            Place(1, "Place1", "Description1", "imageUrl1", "detailedDescription1"),
            Place(2, "Place2", "Description2", "imageUrl2", "detailedDescription2")
        )

        // Create a mock observer with the correct type
        val observer = mock(Observer::class.java) as Observer<List<Place>>

        // Attach the observer
        viewModel.places.observeForever(observer)

        // Mock the repository to return the placeEntities
        `when`(mockRepository.getAllPlaces()).thenReturn(placeEntities)

        // Call the ViewModel function to load from database
        viewModel.loadPlacesFromDatabase()

        // Verify the observer receives the mapped expected places
        verify(observer).onChanged(expectedPlaces)
    }
}
