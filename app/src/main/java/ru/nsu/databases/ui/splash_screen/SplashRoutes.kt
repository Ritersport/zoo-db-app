package ru.nsu.databases.ui.splash_screen

sealed interface SplashRoutes {

    object ToMainScreen : SplashRoutes

    object ToLogin : SplashRoutes
}