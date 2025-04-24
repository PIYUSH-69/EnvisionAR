
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.invictus.envisionar.LoginScreen
import com.invictus.envisionar.R
import com.invictus.envisionar.screens.SideNav.HomePageContent
import com.invictus.envisionar.screens.SideNav.ImageConverter
import com.invictus.envisionar.screens.SideNav.RoomRedesignerScreen
import com.invictus.envisionar.screens.SideNav.ThreeDmodelScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("Home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
            ) {
                DrawerContent(
                    currentScreen = currentScreen,
                    onNavigate = { screen ->
                        currentScreen = screen
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    navController = navController
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp) // Height of the TopAppBar
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF000000), Color(0xFF673AB7)) // Green gradient
                            )
                        )
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentScreen,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Open drawer",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent, // Transparent to let gradient show
                            titleContentColor = Color.White
                        ),
                        modifier = Modifier.background(Color.Transparent)
                    )
                }
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (currentScreen) {
                        "Home" -> HomePageContent { screen -> currentScreen = screen }
                        "Image to 3D" -> ImageConverter(navController)
                        "3D Models"-> ThreeDmodelScreen()
                        "Room Redesigner"->RoomRedesignerScreen()
                        else -> HomePageContent { screen -> currentScreen = screen }
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(currentScreen: String, onNavigate: (String) -> Unit, navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column(
            modifier = Modifier.fillMaxSize().padding(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF831BFF), Color(0xFF0046B2)) // Purple to Blue gradient
                        )
                    )
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                Text(
                    "Envision AR",
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            DrawerItem("Home", rememberVectorPainter(Icons.Filled.Home), currentScreen == "Home") { onNavigate("Home") }
            DrawerItem("Image to 3D", painterResource(id = R.drawable.model), currentScreen == "Image to 3D") { onNavigate("Image to 3D") }
            DrawerItem("3D Models", painterResource(id = R.drawable.vr), currentScreen == "3D Models") { onNavigate("3D Models") }
            DrawerItem("Room Redesigner", painterResource(id = R.drawable.plans), currentScreen == "Room Redesigner") { onNavigate("Room Redesigner") }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter // This aligns children inside Box
            ) {
                OutlinedButton(
                    onClick = {
                        val firebaseAuth = Firebase.auth
                        firebaseAuth.signOut()
                        navController.navigate(LoginScreen)
                         },
                    border = BorderStroke(1.dp, Color.Red),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Sign Out", color = Color.Red)
                }

            }
        }


    }




}


@Composable
fun DrawerItem(text: String, icon: Painter, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            tint = Color.Black, // Change color here
            modifier = Modifier.size(30.dp) // Set icon size to 20dp (adjust if needed)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

