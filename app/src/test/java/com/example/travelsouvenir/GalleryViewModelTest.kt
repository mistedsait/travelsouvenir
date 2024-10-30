package com.example.travelsouvenir

import android.net.Uri
import com.example.travelsouvenir.utils.GalleryHelper
import com.example.travelsouvenir.viewmodels.GalleryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {
    private lateinit var viewModel: GalleryViewModel
    private lateinit var galleryHelper: GalleryHelper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Initialize the mock GalleryHelper
        galleryHelper = mockk()

        // Initialize the ViewModel with the mocked GalleryHelper
        viewModel = GalleryViewModel(galleryHelper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGalleryData should update photos, detailedDescription, and placeName`() = runTest {
        // Mock data
        val photos = listOf(mockk<Uri>(), mockk<Uri>())  // Mock Uri instances
        val detailedDescription = "A beautiful place to visit."
        val placeName = "New York"

        // Mock the fetchPhotosFromAlbum call
        coEvery { galleryHelper.fetchPhotosFromAlbum(placeName) } returns photos

        // Call the function to test
        viewModel.loadGalleryData(photos, detailedDescription, placeName)

        // Run the coroutine until idle to complete background operations
        advanceUntilIdle()

        // Verify that the state has been updated as expected
        assertEquals(detailedDescription, viewModel.detailedDescription.first())
        assertEquals(placeName, viewModel.placeName.first())
        assertEquals(photos, viewModel.photos.first())
    }
}

