package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.MainViewModel

@Composable
fun DailyForecast(mainViewModel: MainViewModel) {

    val weather by mainViewModel.weather.collectAsState()
    val forecast = weather?.forecast

    if (forecast != null)
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(
                forecast
            )
            {
                forecastDay ->
                Text(
                    text = forecastDay.date
                )

                Image(
                    painter = rememberAsyncImagePainter(model = forecastDay.image),
                    contentDescription = "A forecast Image"
                )

                Text(
                    text = forecastDay.condition
                )

                Text(
                    text = "High: " + forecastDay.highTemp + "° Low: " + forecastDay.lowTemp + "°"
                )

                Text(
                    text = forecastDay.precipitationAmount.toString() + "mm/cm " + forecastDay.precipitationProbability + "% Chance"
                )

                Text(
                    text = "Wind: " + forecastDay.windSpeed + " kmh "
                )

                Text(
                    text = "Humidity: " + forecastDay.humidity
                )
            }
        }
    }
}