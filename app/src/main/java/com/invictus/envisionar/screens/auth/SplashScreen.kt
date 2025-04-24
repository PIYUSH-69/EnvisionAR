import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.invictus.envisionar.HomeScreen
import com.invictus.envisionar.LoginScreen
import com.invictus.envisionar.SplashScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        navController.navigate(LoginScreen) {
            popUpTo(SplashScreen) { inclusive = true } // Removes SplashScreen from back stack
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("file:///android_asset/envision.gif") // Load GIF from assets
                    .decoderFactory(ImageDecoderDecoder.Factory()) // Decode GIF
                    .build(),
                contentDescription = "Splash GIF",
                modifier = Modifier.size(10000.dp) // Adjust size as needed
            )
        }
    }


}
