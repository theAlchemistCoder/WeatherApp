package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.weatherapp.MainViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
                forecast ->
                Text(
                    text = forecast.date
                )

                Image(
                    painter = painterResource(id = forecast.image),
                    contentDescription = "A forecast Image"
                )

                Text(
                    text = forecast.condition
                )

                Text(
                    text = "High: " + forecast.highTemp + "° Low: " + forecast.lowTemp + "°"
                )

                Text(
                    text = forecast.precipitationAmount.toString() + "mm/cm " + forecast.precipitationAmount + " " + forecast.precipitationProbability + "% Chance"
                )

                Text(
                    text = "Wind: " + forecast.windSpeed + " kmh " //+ forecast.windDirection
                )

                Text(
                    text = "Humidity: " + forecast.humidity
                )
            }
        }
    }
//    var textDate = ""
//    var _image = painterResource(id = R.drawable.sunny)
//    var _contentDescription = ""
//    var _temp = ""
//    var _accum = ""
//    var _wind = ""
//    var _humdidity = ""

//    Column(
//        //horizontalArrangement = Arrangement.SpaceAround,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxWidth()
//    )
//    {
//        for(i in 1..3)
//        {
//            if (i == 1)
//            {
//                textDate = "Today:\n Sep 26, 2025"
//                _image = painterResource(id = R.drawable.sunny)
//                _contentDescription = "Sunny Day"
//                _temp = "High: 21°C Low: 16°C"
//                _accum = "0mm Rain 100%"
//                _wind = "Wind: 5 kmh E"
//                _humdidity = "Humidity: 65%"
//            }
//            else if (i == 2)
//            {
//                textDate = "Tomorrow:\n Sep 27, 2025"
//                _image = painterResource(id = R.drawable.sunny_cloudy)
//                _contentDescription = "Partially Sunny"
//                _temp = "High: 18°C Low: 10°C"
//                _accum = "~0mm Rain 80%"
//                _wind = "Wind: 4 kmh NW"
//                _humdidity = "Humidity: 76%"
//            }
//            else
//            {
//                textDate = "Day After Tomorrow:\n Sep 28, 2025"
//                _image = painterResource(id = R.drawable.snowy)
//                _contentDescription = "A little Snowy"
//                _temp = "High: -72°C Low: -128°C"
//                _accum = "12303cm Snow 50%"
//                _wind = "Wind: 121 kmh W"
//                _humdidity = "Humidity: 10%"
//            }
//            for(item in weather?.forecast!!) {
//                textDate = item.date
//                _image = rememberAsyncImagePainter(item.image) //painterResource(id = R.drawable.snowy)
//                _contentDescription = item.condition// "A little Snowy"
//                _temp = "High: " + item.highTemp + " Low: " + item.lowTemp
//                _accum = item.precipitationAmount.toString() + " " + item.precipitationType + " " + item.precipitationProbability //needs cm/mm/m logic "12303cm Snow 50%"
//                _wind = "Wind: " + item.windSpeed + " kmh " + item.windDirection //"Wind: 121 kmh W"
//                _humdidity = "Humidity: " + item.humidity //"Humidity: 10%"


//            Column(
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .weight(1f) // Co-Pilot
//                    .padding(8.dp) // Co-Pilot
//            ) {
//                Text(
//
//                    text = textDate
//
//                )
//
//                Image(
//                    painter = rememberAsyncImagePainter(_image),
//                    contentDescription = _contentDescription
//                )
//
//                Text(
//                    text = _contentDescription
//                )
//
//                Text(
//                    text = _temp
//                )
//
//                Text(
//                    text = _accum
//                )
//
//                Text(
//                    text = _wind
//                )
//
//                Text(
//                    text = _humdidity
//                )
//            }

//        }
//    }
}

