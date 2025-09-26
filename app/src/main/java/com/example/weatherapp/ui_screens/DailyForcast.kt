package com.example.weatherapp.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun DailyForcast() {

    var textDate = ""
    var _image = painterResource(id = R.drawable.sunny)
    var _contentDescription = ""
    var _temp = ""
    var _accum = ""
    var _wind = ""
    var _humdidity = ""

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    )
    {
        for(i in 1..3)
        {
            if (i == 1)
            {
                textDate = "Today:\n Sep 26, 2025"
                _image = painterResource(id = R.drawable.sunny)
                _contentDescription = "Sunny Day"
                _temp = "High: 21°C Low: 16°C"
                _accum = "0mm Rain 100%"
                _wind = "Wind: 5 kmh E"
                _humdidity = "Humidity: 65%"
            }
            else if (i == 2)
            {
                textDate = "Tomorrow:\n Sep 27, 2025"
                _image = painterResource(id = R.drawable.sunny_cloudy)
                _contentDescription = "Partially Sunny"
                _temp = "High: 18°C Low: 10°C"
                _accum = "~0mm Rain 80%"
                _wind = "Wind: 4 kmh NW"
                _humdidity = "Humidity: 76%"
            }
            else
            {
                textDate = "Day After Tomorrow:\n Sep 28, 2025"
                _image = painterResource(id = R.drawable.snowy)
                _contentDescription = "A little Snowy"
                _temp = "High: -72°C Low: -128°C"
                _accum = "12303cm Snow 50%"
                _wind = "Wind: 121 kmh W"
                _humdidity = "Humidity: 10%"
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f) // Co-Pilot
                    .padding(8.dp) // Co-Pilot
            ) {
                Text(

                    text = textDate

                )

                Image(
                    painter = _image,
                    contentDescription = _contentDescription
                )

                Text(
                    text = _contentDescription
                )

                Text(
                    text = _temp
                )

                Text(
                    text = _accum
                )

                Text(
                    text = _wind
                )

                Text(
                    text = _humdidity
                )
            }
        }

    }
}

