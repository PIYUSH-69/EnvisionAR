package com.invictus.envisionar.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.invictus.envisionar.LoginScreen
import com.invictus.envisionar.R
import com.invictus.envisionar.firebase.FirebaseAuthManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUpScreen(navController: NavController) {

    val authManager = FirebaseAuthManager()
    val context = LocalContext.current // Get the context in Compose


    var fullName by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), // Light Gray Background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo/Icon
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(250.dp)  // Set width
                    .height(100.dp) // Set height (adjust for desired aspect ratio)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Create Account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black ,
                fontFamily = FontFamily.Monospace

            )

            Spacer(modifier = Modifier.height(24.dp))

            // Full Name Input
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                maxLines = 1,       // Limits input to one line


                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp),
                maxLines = 1,       // Limits input to one line

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                maxLines = 1,       // Limits input to one line
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Create Account Button
            Button(
                onClick = {
                    if (validate( context,email.text, password.text)){
                        GlobalScope.launch(Dispatchers.IO) {
                            val user = authManager.signUp(email.text, password.text)
                            if (user != null) {
                                println("User signed up: ${user.email}")
                            } else {
                                println("Sign-up failed")
                            }

                        }

                    }



                          },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A4EFF)), // Purple Color
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Create Account", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign In Text
            Button(
                onClick ={
                    navController.navigate(LoginScreen)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDEDE)) // Change button color here

            ) { Row {
                Text(text = "Already have an account?", color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign In",
                    color = Color.Black, // Purple color
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }}

        }
    }
}



private fun validate(context: Context, email: String, password: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    // Check if email is valid
    if (!email.matches(emailPattern)) {
        Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
        return false
    }

    // Password validation
    if (password.length < 6) {
        Toast.makeText(context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
        return false
    }

    if (!password.any { it.isUpperCase() }) {
        Toast.makeText(context, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show()
        return false
    }

    if (!password.any { it.isDigit() }) {
        Toast.makeText(context, "Password must contain at least one digit", Toast.LENGTH_SHORT).show()
        return false
    }

    if (!password.any { "!@#$%^&*()-_=+{}[]|;:'\",.<>?/".contains(it) }) {
        Toast.makeText(context, "Password must contain at least one special character", Toast.LENGTH_SHORT).show()
        return false
    }

    return true // If all checks pass
}
