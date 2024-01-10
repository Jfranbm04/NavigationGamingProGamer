package com.example.navegasionkotlin

sealed class Screen(val route: String) {
    object SportList : Screen("sportList")
    object SportDetails : Screen("sportDetails/{sportId}") // Utilizamos un argumento para el ID del deporte
}