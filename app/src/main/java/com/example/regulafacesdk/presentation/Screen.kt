package com.example.regulafacesdk.presentation

sealed class Screen (val route: String) {
    data object Home : Screen(route = "home")
}