package com.example.jayasales

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.debduttapanda.j3lib.MyScreen
import com.example.jayasales.presentation.screens.LoginScreen
import com.example.jayasales.presentation.screens.SplashScreen
import com.example.jayasales.presentation.viewmodels.LoginViewModel
import com.example.jayasales.presentation.viewmodels.SplashViewModel


@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Routes.splash.full
    ) {
        MyScreen(
            navController, Routes.splash,
            wirelessViewModel = { hiltViewModel<SplashViewModel>() }
        ) {
            SplashScreen()
        }
        MyScreen(
            navController, Routes.login,
            wirelessViewModel = { hiltViewModel<LoginViewModel>() }
        ) {
            LoginScreen()
        }
    }
}