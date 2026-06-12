package com.qyupaww.jetpackcomposedigidex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qyupaww.jetpackcomposedigidex.digimondetail.DigimonDetailScreen
import com.qyupaww.jetpackcomposedigidex.digimonlist.DigimonListScreen
import com.qyupaww.jetpackcomposedigidex.ui.theme.JetpackComposeDigidexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDigidexTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "digimon_list_screen"
                ) {
                    composable("digimon_list_screen") {
                        DigimonListScreen(navController = navController)
                    }
                    composable(
                        route = "digimon_detail_screen/{dominantColor}/{digimonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("digimonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val digimonName = remember {
                            it.arguments?.getString("digimonName")
                        }
                        
                        DigimonDetailScreen(
                            dominantColor = dominantColor,
                            digimonName = digimonName?.lowercase(java.util.Locale.ROOT) ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}