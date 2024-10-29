package com.example.travelsouvenir.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelsouvenir.R
import com.example.travelsouvenir.ui.theme.TravelSouvenirTheme

@Composable
fun LandingPage(modifier: Modifier = Modifier) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFE259),
            Color(0xFFFFA751)
        )
    )

    val planet = painterResource(R.drawable.travel_photo)

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Map Your Memories,\nUncover the Stories",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 45.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(brush = gradientBrush)
            )
            Image(
                painter = planet,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
            )
            Text(
                text = "Ready to save a memory?\n" +
                        "Tap the camera to snap a picture!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 40.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLandingPage() {
    TravelSouvenirTheme {
        LandingPage()
    }
}
