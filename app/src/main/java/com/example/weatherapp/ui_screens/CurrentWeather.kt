package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.models.AirQuality
import com.example.weatherapp.models.Alert
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CurrentWeather(mainViewModel: MainViewModel) {

    val weatherData by mainViewModel.weather.collectAsState()
    val current = weatherData?.current
    val location = weatherData?.location
    val alerts = weatherData?.alerts?.alert

    // The main column that holds all content
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Conditionally display the Alert Banner at the top
        if (!alerts.isNullOrEmpty()) {
            AlertBanner(alert = alerts.first())
        }

        if (current != null && location != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Parse and format the date using SimpleDateFormat for API 24 compatibility
                val inputFormatter = SimpleDateFormat("yyyy-MM-dd H:mm", Locale.ENGLISH)
                val date = inputFormatter.parse(location.localtime)!!
                val outputFormatter = SimpleDateFormat("EEEE, MMMM d", Locale.ENGLISH)
                val formattedDate = outputFormatter.format(date)

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                val largerIconUrl = current.image.replace("64x64", "128x128")
                val painter = rememberAsyncImagePainter(model = largerIconUrl)

                Image(
                    painter = painter,
                    contentDescription = "Weather Image",
                    modifier = Modifier.size(128.dp)
                )

                Text(text = current.condition, style = MaterialTheme.typography.titleLarge)

                Text(text = "${current.temperature.toInt()}°", style = MaterialTheme.typography.displayLarge)

                Text(text = "Precipitation: ${current.precipitationAmount}mm", style = MaterialTheme.typography.bodyLarge)

                Text(text = "Wind: ${current.windSpeed.toInt()} km/h ${current.windDirection}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                // Conditionally display Air Quality Info
                current.airQuality?.let {
                    AirQualityInfo(airQuality = it)
                }
            }
        }
    }
}

@Composable
private fun AirQualityInfo(airQuality: AirQuality) {
    val airQualityLabel = when (airQuality.usEpaIndex) {
        1 -> "Good"
        2 -> "Moderate"
        3 -> "Unhealthy for Sensitive Groups"
        4 -> "Unhealthy"
        5 -> "Very Unhealthy"
        6 -> "Hazardous"
        else -> "Unknown"
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Air Quality", style = MaterialTheme.typography.titleMedium)
        Text("$airQualityLabel (AQI: ${airQuality.usEpaIndex})", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun AlertBanner(alert: Alert) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red.copy(alpha = 0.8f))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = alert.headline,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
