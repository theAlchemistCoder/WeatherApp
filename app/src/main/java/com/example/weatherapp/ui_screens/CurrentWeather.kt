package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.MainViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R

//@Preview
@Composable
fun CurrentWeather(mainViewModel: MainViewModel) { //  rid of main view model to return to previous code

    val weather by mainViewModel.weather.collectAsState() //get rid of this line to return to previous code

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            // painter = painterResource(id = R.drawable.sunny),
            painter = rememberAsyncImagePainter(weather?.current?.image),
            contentDescription = "Weather Image"
        )

        Text( // Current Condition
            text = weather?.current?.condition.toString(),
            style = MaterialTheme.typography.titleSmall
        )

        Text( // Current Temperature
            text = weather?.current?.temperature.toString(),
            style = MaterialTheme.typography.titleSmall
        )

        Text( // Current Precipitation
            text = weather?.current?.precipitationAmount.toString() + " " + weather?.current?.precipitationType.toString(),
            style = MaterialTheme.typography.titleSmall
        )

        Text( // Current Wind
            text = weather?.current?.windSpeed.toString() + " " + weather?.current?.windDirection.toString(),
            style = MaterialTheme.typography.titleSmall
        )

    }
}