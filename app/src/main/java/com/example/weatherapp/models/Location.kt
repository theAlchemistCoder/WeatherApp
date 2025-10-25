package com.example.weatherapp.models

//data class Location(
//    val name: String,
//    val region: String,
//    val country: String,val localtime: String
//) {
//    val locationString: String get() = "$name, $region"
//}

data class Location(
    val name: String,
    val region: String,
    val localtime: String
) {
    val locationString: String get() = "$name, $region"
}