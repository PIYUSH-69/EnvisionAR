package com.invictus.envisionar.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictus.envisionar.HomeScreen
import com.invictus.envisionar.R
import com.invictus.envisionar.SignUpScreen

@Composable
fun LoginScreen(navController: NavController) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6A1B9A))) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            navController.navigate(HomeScreen)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {navController.navigate(SignUpScreen)}) {
                        Text("Don't have an account? Sign Up", color = Color(0xFF6A1B9A))
                    }
                }
            }
        }


}