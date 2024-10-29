package com.example.travelsouvenir

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.travelsouvenir.pages.MyScreenContent
import com.example.travelsouvenir.ui.theme.TravelSouvenirTheme
import com.example.travelsouvenir.viewmodels.PlacesViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelsouvenir.pages.GalleryScreen
import com.example.travelsouvenir.viewmodels.GalleryViewModel
import com.google.firebase.messaging.FirebaseMessaging

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val myViewModel: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askNotificationPermission()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                sendRegistrationToServer(token) // Use your method to send the token to your server
                Log.d("MainActivity", "FCM token: $token")
            } else {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
            }
        }

        enableEdgeToEdge()
        setContent {
            TravelSouvenirTheme {
                MyScreenContent(viewModel = myViewModel, deepLinkPlaceName = null) // Pass null initially
            }
        }
        handleDeepLink(intent)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {

            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Show rationale to the user explaining why the permission is needed
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Log.d("MainActivity", "Notification permission denied")
        }
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("MainActivity", "Token sent to server: $token")
    }

    // Handle incoming intents for deep linking
    private fun handleDeepLink(intent: Intent?) {
        intent?.let {
            val placeName = it.getStringExtra("placeName")
            val destination = it.getStringExtra("destination")
            Log.d("DeepLink", "Destination: $destination, PlaceName: $placeName") // Add this log
            if (placeName != null && destination != null) {
                // Handle navigation based on the destination and placeName
                if (destination == "GalleryScreen") {
                    navigateToDestination(destination, placeName)
                }
            }
        }
    }

    private fun navigateToDestination(destination: String, placeName: String) {
        setContent {
            TravelSouvenirTheme {

                // Load gallery data with a placeholder or fetched data if available

                // Navigate to the GalleryScreen
                Log.d("Navigation", "Navigating to $destination with placeName: $placeName") // Debug log
                MyScreenContent(viewModel = myViewModel, deepLinkPlaceName = placeName)

            }
        }
    }

    // Override onNewIntent to handle new intents if app is already running
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink(intent) // Re-handle the intent
    }
}
