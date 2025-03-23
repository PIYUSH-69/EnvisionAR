package com.invictus.envisionar

import HomeScreen
import SplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.invictus.envisionar.screens.auth.LoginScreen
import com.invictus.envisionar.screens.auth.SignUpScreen
import com.invictus.envisionar.ui.theme.EnvisionARTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnvisionARTheme {
                App()
            }
        }
    }
}


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




//        composable<FullBlogScreen> {
//            val args=it.toRoute<FullBlogScreen>()
//            FullBlogScreen(args)
//        }

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

//@Serializable
//data class FullBlogScreen(
//    val blog: String?,
//    val blogdesc: String?,
//    val blogcategory: String?
//)
