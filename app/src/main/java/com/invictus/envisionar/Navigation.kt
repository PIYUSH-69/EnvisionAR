package com.invictus.envisionar

import HomeScreen
import SplashScreen
import VrScreeen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.invictus.envisionar.screens.auth.LoginScreen
import com.invictus.envisionar.screens.auth.SignUpScreen
import com.invictus.envisionar.ui.theme.EnvisionARTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val window = LocalView.current.context as ComponentActivity
            val statusBarColor =  Color.White.toArgb() // Change this to any color you want

            SideEffect {
                window.window?.statusBarColor = statusBarColor
            }
                App()

        }
    }
}




@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun App() {
    val navcontroller=rememberNavController()
    NavHost(navController = navcontroller, startDestination = SplashScreen) {

        composable<SplashScreen>{
            SplashScreen(navcontroller)
        }

        composable<LoginScreen>{
            LoginScreen(navcontroller)
        }

        composable<SignUpScreen>{
            SignUpScreen(navcontroller)
        }

        composable<HomeScreen>{
            HomeScreen(navcontroller)
        }

        composable<VrScreen> {
            val args=it.toRoute<VrScreen>()
            VrScreeen(args)
        }

    }
}



@Serializable
object SplashScreen

@Serializable
object LoginScreen

@Serializable
object SignUpScreen

@Serializable
 object HomeScreen

@Serializable
data class VrScreen(
    val modelurl: String?,
)
