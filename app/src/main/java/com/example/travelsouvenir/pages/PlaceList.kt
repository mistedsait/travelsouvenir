package com.example.travelsouvenir.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.travelsouvenir.R


@Composable
fun DisplayPlace(place: Place, onClick: () -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    // Animate scale on click
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                isSelected = !isSelected
                onClick()
            }
            .scale(scale)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = place.originalImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    error = painterResource(R.drawable.paris),
                    fallback = painterResource(R.drawable.paris)
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = place.name,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 40.dp, start = 20.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(4.dp)),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = place.description,
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 20.dp, end = 14.dp, start = 20.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp)),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}




@Composable
fun DisplayAllPlaces(places: List<Place>, onPlaceClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(places) { place ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                DisplayPlace(place) {
                    onPlaceClick(place.name)
                }
            }
        }
    }
}

