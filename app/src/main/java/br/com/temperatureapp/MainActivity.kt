package br.com.temperatureapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.temperatureapp.presentation.SplashScreen
import br.com.temperatureapp.presentation.detail.DetailScreen
import br.com.temperatureapp.presentation.detail.DetailViewModel
import br.com.temperatureapp.presentation.home.HomeScreen
import br.com.temperatureapp.presentation.home.HomeViewModel
import br.com.temperatureapp.ui.theme.TemperatureAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureAppTheme {
                val homeViewModel by viewModels<HomeViewModel>()
                val detailViewModel by viewModels<DetailViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Navigation(homeViewModel, detailViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen"){
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }
        composable("home_screen"){
            HomeScreen(
                viewModel = homeViewModel,
                nav = navController
            )
        }
        composable("detail"){
            DetailScreen(detailViewModel)
        }
    }
}

