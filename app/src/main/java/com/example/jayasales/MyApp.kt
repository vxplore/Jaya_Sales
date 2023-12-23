package com.example.jayasales

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.debduttapanda.j3lib.MyScreen
import com.example.jayasales.presentation.screens.HomeScreen
import com.example.jayasales.presentation.screens.LoginScreen
import com.example.jayasales.presentation.screens.MarkVisitScreen
import com.example.jayasales.presentation.screens.PartiesScreen
import com.example.jayasales.presentation.screens.SelectRouteScreen
import com.example.jayasales.presentation.screens.SplashScreen
import com.example.jayasales.presentation.screens.StoreDetailsScreen
import com.example.jayasales.presentation.viewmodels.HomeViewModel
import com.example.jayasales.presentation.viewmodels.LoginViewModel
import com.example.jayasales.presentation.viewmodels.MarkVisitViewModel
import com.example.jayasales.presentation.viewmodels.PartiesViewModel
import com.example.jayasales.presentation.viewmodels.SelectRouteViewModel
import com.example.jayasales.presentation.viewmodels.SplashViewModel
import com.example.jayasales.presentation.viewmodels.StoreDetailsViewModel


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
        MyScreen(
            navController, Routes.home,
            wirelessViewModel = { hiltViewModel<HomeViewModel>() }
        ) {
            HomeScreen()
        }
        MyScreen(
            navController, Routes.selectRoute,
            wirelessViewModel = { hiltViewModel<SelectRouteViewModel>() }
        ) {
            SelectRouteScreen()
        }
        MyScreen(
            navController, Routes.parties,
            wirelessViewModel = { hiltViewModel<PartiesViewModel>() }
        ) {
            PartiesScreen()
        }
        MyScreen(
            navController, Routes.storeDetails,
            wirelessViewModel = { hiltViewModel<StoreDetailsViewModel>() }
        ) {
            StoreDetailsScreen()
        }
        MyScreen(
            navController, Routes.markVisit,
            wirelessViewModel = { hiltViewModel<MarkVisitViewModel>() }
        ) {
            MarkVisitScreen()
        }
    }
}