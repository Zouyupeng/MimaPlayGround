package com.oceanknight.mimaplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oceanknight.mimaplayground.ui.page.element.volumebar.VolumeBarScreen
import com.oceanknight.mimaplayground.ui.theme.MimaPlaygroundTheme
import com.oceanknight.mimaplayground.ui.theme.StartMenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MimaPlaygroundTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "start") {
                    composable("start") {
                        StartMenuScreen() {
                            navController.navigate(it.route)
                        }
                    }
                    NavRoute.values().forEach { navRoute ->
                        composable(navRoute.route) {
                            when(navRoute) {
                                NavRoute.VolumeBar -> VolumeBarScreen()
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
