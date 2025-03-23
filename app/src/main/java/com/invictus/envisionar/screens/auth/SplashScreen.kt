import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.invictus.envisionar.LoginScreen
import com.invictus.envisionar.SplashScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(navController: NavController) {

    val repository = MongoDBAuthRepository.getInstance()

// Register a user
    LaunchedEffect(Unit) {
        val isRegistered = repository.registerUser("John Doe", "john@example.com", "password123")
        if (isRegistered) {
            println("User registered successfully")
        } else {
            println("Failed to register user")
        }
    }

    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        navController.navigate(LoginScreen){
            popUpTo(SplashScreen) { inclusive = true } // Removes SplashScreen from back stack
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White // Set background color here
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ENVISION AR",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}