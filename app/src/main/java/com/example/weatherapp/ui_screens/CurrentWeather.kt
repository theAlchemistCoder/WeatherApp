package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CurrentWeather(mainViewModel: MainViewModel) {

    val weather by mainViewModel.weather.collectAsState()
    val current = weather?.current
    val location = weather?.location

    if (current != null && location != null) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Parse and format the date
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH)
            val dateTime = LocalDateTime.parse(location.localtime, inputFormatter)
            val formattedDate = dateTime.format(outputFormatter)

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // The API provides a 64x64 icon. To make it larger, we can replace '64x64' with '128x128' in the URL.
            val largerIconUrl = current.image.replace("64x64", "128x128")
            val painter = rememberAsyncImagePainter(model = largerIconUrl)

            Image(
                painter = painter,
                contentDescription = "Weather Image",
                modifier = Modifier.size(128.dp) // Also increase the size of the Image composable
            )

            Text( // Current Condition
                text = current.condition,
                style = MaterialTheme.typography.titleLarge
            )

            Text( // Current Temperature
                text = "${current.temperature.toInt()}°",
                style = MaterialTheme.typography.displayLarge
            )

            Text( // Current Precipitation
                text = "Precipitation: ${current.precipitationAmount}mm",
                style = MaterialTheme.typography.bodyLarge
            )

            Text( // Current Wind
                text = "Wind: ${current.windSpeed.toInt()} km/h ${current.windDirection}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
