package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.renderscript.RenderScript
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.models.Weather
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui_screens.CurrentWeather
import com.example.weatherapp.ui_screens.DailyForecast
import com.example.weatherapp.ui_screens.DailyForecast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                GetLocation(mainViewModel)
                DisplayUI(mainViewModel)
                }
            }
        }
    }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetLocation(mvm: MainViewModel) {
    // Remember the permission state(asking for Fine location)
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (permissionState.status.isGranted) {
        Log.i("TESTING", "Hurray, permission granted!")

        // Get Location
        val currentContext = LocalContext.current
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(currentContext)

        if (ContextCompat.checkSelfPermission(
                currentContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        {
            val cancellationTokenSource = CancellationTokenSource()

            Log.i("TESTING", "Requesting location...")

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val lat = location.latitude.toString()
                        val lng = location.longitude.toString()

                        Log.i("TESTING", "Success: $lat $lng")

                        val coordinates = "$lat,$lng"

                        mvm.fetchWeatherDataForLocation(coordinates)
                    }
                    else {
                        Log.i("TESTING", "Problem encountered: Location returned null")
                    }
                }
        }
    }
    else {
        // Run a side-effect (coroutine) to get permission. The permission popup.
        LaunchedEffect(permissionState){
            permissionState.launchPermissionRequest()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUI(mvm: MainViewModel) {
    val navController = rememberNavController()
    val weather by mvm.weather.collectAsState()

    // Get the location name from the weather data, or show "Loading..."
    val locationName = weather?.location?.let { "${it.name}, ${it.region}" } ?: "Loading..."

    // Variable to store the selected value in Nav Bar
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(locationName)
                },


            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )
            {
                NavigationBarItem(
                    label = {
                        Text("Current")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_daily),
                            contentDescription = "Current Weather"
                        )
                    },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        navController.navigate("current")
                    }
                )
                NavigationBarItem(
                    label = {
                        Text("Daily")
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_current),
                            contentDescription = "Daily Weather"
                        )
                    },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate("daily")
                    }
                )
            }
        }
    )
    { innerPadding ->
//        Text("Hello", modifier = Modifier.padding(innerPadding))

        NavHost(
            navController = navController,
            startDestination = "current",
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = "current")
            {
                CurrentWeather(mvm)
            }

            composable(route = "daily")
            {
                DailyForecast(mvm)
            }
        }
    }
}

@Composable fun UserLocation() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    )
    {
        Text(
            text = "Halifax, Nova Scotia"
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}
@Composable
fun WeatherGreeting() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        )
        {
            Text(
                text = "Welcome"
            )

            Text(
                text = "To Weather"
            )

            Text(
                text = "Mobile App!"
            )
        }

    }
}
