import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.travelsouvenir.pages.DisplayAllPlaces
import com.example.travelsouvenir.viewmodels.PlacesViewModel


@Composable
fun MyPlaceScreen(viewModel: PlacesViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.loadPlacesFromDatabase()
    }

    val places by viewModel.places.observeAsState(emptyList())

    Log.d("DISPLAY_PLACES", "Currently displayed places: ${places.map { it.name }}")

    if (places.isEmpty()) {
        Log.d("UI_UPDATE", "No places available to display.")
        // Show a message or empty state here
    } else {
        Log.d("UI_UPDATE", "Displaying ${places.size} places.")
        // Call DisplayAllPlaces and handle click navigation there
        DisplayAllPlaces(places) { placeName ->
            // Navigate only when a place is clicked
            navController.navigate("GalleryScreen/$placeName")
        }
    }
}
