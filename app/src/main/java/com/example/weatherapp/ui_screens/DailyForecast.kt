package com.example.weatherapp.ui_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.models.ForecastDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyForecast(mainViewModel: MainViewModel) {

    val weather by mainViewModel.weather.collectAsState()
    val forecast = weather?.forecast

    if (forecast != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(forecast) { index, forecastDay ->
                ForecastRow(forecastDay = forecastDay, index = index)
            }
        }
    }
}

@Composable
private fun ForecastRow(forecastDay: ForecastDay, index: Int) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    val date = LocalDate.parse(forecastDay.date, DateTimeFormatter.ISO_LOCAL_DATE)

    val dayDisplayName = when (index) {
        0 -> "Today"
        1 -> "Tomorrow"
        else -> date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    text = dayDisplayName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Image(
                painter = rememberAsyncImagePainter(model = forecastDay.image.replace("64x64", "128x128")),
                contentDescription = forecastDay.condition,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = "Low: ${forecastDay.lowTemp.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "High: ${forecastDay.highTemp.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = rememberAsyncImagePainter(android.R.drawable.arrow_down_float),
                contentDescription = "Expand/Collapse",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Condition: ${forecastDay.condition}")
                Spacer(Modifier.height(4.dp))
                Text("Precipitation: ${forecastDay.precipitationAmount}mm with a ${forecastDay.precipitationProbability}% chance")
                Spacer(Modifier.height(4.dp))
                Text("Wind: ${forecastDay.windSpeed.toInt()} km/h")
                Spacer(Modifier.height(4.dp))
                Text("Humidity: ${forecastDay.humidity}%")
            }
        }
    }
}
