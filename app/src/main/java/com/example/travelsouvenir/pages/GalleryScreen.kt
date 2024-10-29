package com.example.travelsouvenir.pages

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.travelsouvenir.viewmodels.GalleryViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GalleryScreen(  //DESCRIPTION I PLACENAME U VIEWMODEL, gallery helper treba u viewmodel, i sve sto se pojavljuje na screenu preko helpera ide u viewmodel, ili jetpack ili view
    galleryViewModel: GalleryViewModel
) {
    val photos by galleryViewModel.photos.collectAsState()
    val detailedDescription by galleryViewModel.detailedDescription.collectAsState()
    val placeName by galleryViewModel.placeName.collectAsState()

    LazyColumn {
        item {
            HorizontalPagerWithOffsetTransition(photos)
        }
        item {
            DescriptionText(detailedDescription)
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun HorizontalPagerWithOffsetTransition(
    photos: List<Uri>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val openDialog = remember { androidx.compose.runtime.mutableStateOf(false) }
    val selectedPhotoIndex = remember { androidx.compose.runtime.mutableStateOf(0) }

    HorizontalPager(
        state = pagerState,
        count = photos.size,
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = modifier.fillMaxSize()
    ) { page ->
        Column {
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable {
                        selectedPhotoIndex.value = page
                        openDialog.value = true
                    }
            ) {
                Box {
                    Image(
                        painter = rememberImagePainter(photos[page]),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    if (openDialog.value) {
        FullScreenImageDialog(
            photos = photos,
            initialIndex = selectedPhotoIndex.value,
            onDismiss = { openDialog.value = false }
        )
    }
}

@Composable
fun FullScreenImageDialog(photos: List<Uri>, initialIndex: Int, onDismiss: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = initialIndex) // Fixing pagerState

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            HorizontalPager(
                count = photos.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    val scaleState = remember { androidx.compose.runtime.mutableStateOf(1f) }
                    val offsetState = remember { androidx.compose.runtime.mutableStateOf(Offset(0f, 0f)) }

                    Modifier.pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoomChange, _ ->
                            if (scaleState.value > 1f) {
                                scaleState.value *= zoomChange
                                scaleState.value = scaleState.value.coerceIn(1f, 3f)

                                offsetState.value = Offset(
                                    x = offsetState.value.x + pan.x,
                                    y = offsetState.value.y + pan.y
                                )
                            }
                        }
                    }

                    Image(
                        painter = rememberImagePainter(photos[page]),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scaleState.value,
                                scaleY = scaleState.value,
                                translationX = offsetState.value.x,
                                translationY = offsetState.value.y
                            )
                    )

                    Modifier.clickable {
                        scaleState.value = 1f
                        offsetState.value = Offset(0f, 0f)
                    }
                }
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun DescriptionText(detailedDescription: String) {
    Text(
        text = detailedDescription,
        textAlign = TextAlign.Justify,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
}
