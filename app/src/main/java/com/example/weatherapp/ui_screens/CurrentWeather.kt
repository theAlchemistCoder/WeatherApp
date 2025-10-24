package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.weatherapp.MainViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R

//@Preview
@Composable
fun CurrentWeather(mainViewModel: MainViewModel) { //  rid of main view model to return to previous code

    val weather by mainViewModel.weather.collectAsState() //get rid of this line to return to previous code
    val current = weather?.current

    if (current != null)
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {

//            val painter = rememberAsyncImagePainter(
//                model = current.image)

            Image(
                painter = painterResource(id = current.image),
                //painter = painter,
                contentDescription = "Weather Image"
            )

            Text( // Current Condition
                text = current.condition,
                style = MaterialTheme.typography.titleSmall
            )

            Text( // Current Temperature
                text = current.temperature.toString() + "°",
                style = MaterialTheme.typography.titleSmall
            )

            Text( // Current Precipitation
                text = current.precipitationAmount.toString() + "mm/cm " + current.precipitationAmount,
                style = MaterialTheme.typography.titleSmall
            )

            Text( // Current Wind
                text = current.windSpeed.toString() + " " + current.windDirection,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }

}