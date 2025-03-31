package com.invictus.envisionar.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictus.envisionar.HomeScreen
import com.invictus.envisionar.LoginScreen
import com.invictus.envisionar.R
import com.invictus.envisionar.SignUpScreen
import com.invictus.envisionar.SplashScreen
import com.invictus.envisionar.firebase.FirebaseAuthManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(navController: NavController) {

    val authManager = FirebaseAuthManager()
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(250.dp)  // Set width
                    .height(100.dp) // Set height (adjust for desired aspect ratio)
            )

            Text(
                "Welcome Back",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { email = it },
                label = { Text("Email") },
                maxLines = 1,       // Limits input to one line
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { password = it },
                label = { Text("Password") },
                maxLines = 1,       // Limits input to one line
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (validate2(context, email.text, password.text)) {
                        GlobalScope.launch(Dispatchers.IO) {
                            val isAuthenticated = authManager.login(email.text, password.text)

                            withContext(Dispatchers.Main) { // Ensure UI updates run on the Main thread
                                if (isAuthenticated) {
                                    navController.navigate(HomeScreen){
                                        popUpTo(LoginScreen) { inclusive = true } // Removes SplashScreen from back stack
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Invalid email or password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In", fontSize = 16.sp)
            }


            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick =

                    {
                        navController.navigate(SignUpScreen)

                    }) {
                Text("Don't have an account? Sign Up", color = Color(0xFF6A1B9A))
            }
        }
    }


}




fun validate2(context: Context, email: String, password: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    if (!email.matches(emailPattern)) {
        Toast.makeText(context, "Please enter valid Email ID", Toast.LENGTH_SHORT).show()
        return false
    }

    if (password.length < 6) {
        Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
        return false
    }

    return true // If all checks pass
}


@Composable
@Preview
fun preview(){

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(250.dp)  // Set width
                    .height(100.dp) // Set height (adjust for desired aspect ratio)
            )

            Text("Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.Monospace)

            Spacer(modifier = Modifier.height(24.dp))

//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                maxLines = 1,       // Limits input to one line
//                modifier = Modifier.fillMaxWidth()
//            )

            Spacer(modifier = Modifier.height(16.dp))

//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                maxLines = 1,       // Limits input to one line
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth()
//            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
//                    if (validate2(context, email.text, password.text)) {
//                        GlobalScope.launch(Dispatchers.IO) {
//                            val isAuthenticated = authManager.login(email.text, password.text)
//
//                            withContext(Dispatchers.Main) { // Ensure UI updates run on the Main thread
//                                if (isAuthenticated) {
//                                    //navController.navigate(HomeScreen)
//                                } else {
//                                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        }
//                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In", fontSize = 16.sp)
            }


            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick =

                {
                   // navController.navigate(SignUpScreen)

                }) {
                Text("Don't have an account? Sign Up", color = Color(0xFF6A1B9A))
            }
        }
    }


}
