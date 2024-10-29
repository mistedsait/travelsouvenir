package com.example.travelsouvenir.pages

import MyPlaceScreen
import android.Manifest
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import com.example.travelsouvenir.R
import com.example.travelsouvenir.utils.LocationHelper
import com.example.travelsouvenir.utils.PhotoHelper
import com.example.travelsouvenir.viewmodels.GalleryViewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.io.File

enum class TravelScreen(val route: String) {
    LandingPage("LandingPage"),
    MyPlaceScreen("MyPlaceScreen"),
    GalleryScreen("GalleryScreen/{placeName}")
}

@Composable
fun MyScreenContent(viewModel: PlacesViewModel, deepLinkPlaceName: String? = null, ) {
    val context = LocalContext.current
    val locationHelper = remember { LocationHelper(context, viewModel) }
    val logo = painterResource(R.drawable.paper_plane)
    val home = painterResource(R.drawable.homeor)
    val profile = painterResource(R.drawable.profile)
    val book = painterResource(R.drawable.book)
    val camera = painterResource(R.drawable.camera)

    val navController: NavHostController = rememberNavController()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationState = remember { mutableStateOf<Location?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    // Log for debugging deep link navigation
    LaunchedEffect(deepLinkPlaceName) {
        Log.d("DeepLinkNavigation", "Attempting to navigate to GalleryScreen with placeName: $deepLinkPlaceName") // Debug log
        deepLinkPlaceName?.let { name ->
            navController.navigate(TravelScreen.GalleryScreen.route.replace("{placeName}", name))
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadPlacesFromDatabase()
        isLoading.value = false
    }

    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000L
        ).apply { setMinUpdateIntervalMillis(5000L) }.build()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

            if (cameraGranted && locationGranted) {
                locationHelper.startLocationUpdates(fusedLocationClient, locationRequest, locationState)
            } else {
                Log.d("Permission", "Camera or location permission denied")
            }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Log.d("Camera", "Image saved to: ${imageUri.value}")
            locationHelper.getCityNameFromLocation(fusedLocationClient, imageUri.value!!)
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        val imageFile = File(context.filesDir, "temp_image.jpg")
        imageUri.value = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
    }

    // Navigate to GalleryScreen if deepLinkPlaceName is provided
    LaunchedEffect(deepLinkPlaceName) {
        deepLinkPlaceName?.let { name ->
            navController.navigate(TravelScreen.GalleryScreen.route.replace("{placeName}", name))
        }
    }

    Scaffold(
        topBar = { Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) { Image(logo, null, Modifier.size(88.dp).padding(top = 30.dp)) } },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(106.dp)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    Modifier.fillMaxWidth().fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(home, "Home", Modifier.size(55.dp).clickable { navController.navigate(TravelScreen.LandingPage.route) })
                    Image(camera, "Camera", Modifier.size(55.dp).clickable { cameraLauncher.launch(imageUri.value) })
                    Image(book, "Book", Modifier.size(50.dp).clickable { navController.navigate(TravelScreen.MyPlaceScreen.route) })
                    Image(profile, "Profile", Modifier.size(55.dp))
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TravelScreen.LandingPage.route,
            Modifier.fillMaxSize().padding(paddingValues)
        ) {
            composable(
                TravelScreen.LandingPage.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { LandingPage() }

            composable(
                TravelScreen.MyPlaceScreen.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedVisibility(visible = isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = Color.Blue
                        )
                    }

                    AnimatedVisibility(visible = !isLoading.value) {
                        MyPlaceScreen(viewModel, navController)
                    }
                }
            }

            composable(
                route = TravelScreen.GalleryScreen.route,
                arguments = listOf(navArgument("placeName") { type = NavType.StringType }),
                deepLinks = listOf(navDeepLink { uriPattern = "myapp://gallery/{placeName}" }),
                enterTransition = { slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) },
                exitTransition = { slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) }
            ) { backStackEntry ->
                val placeName = backStackEntry.arguments?.getString("placeName") ?: ""
                val detailedDescription = viewModel.places.value?.find { it.name == placeName }?.detailedDescription ?: ""
                val galleryViewModel: GalleryViewModel = hiltViewModel()

                LaunchedEffect(placeName, detailedDescription) {
                    galleryViewModel.loadGalleryData(emptyList(), detailedDescription, placeName)
                }

                GalleryScreen(galleryViewModel)
            }
        }
    }

    locationState.value?.let { Log.d("Location", "Lat: ${it.latitude}, Lng: ${it.longitude}") }
}
