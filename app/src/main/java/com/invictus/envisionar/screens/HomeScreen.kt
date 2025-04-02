
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open drawer", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White
                    )
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (currentScreen) {
                        "Home" -> HomePageContent()
                        "Image to 3D" -> ImageConverter(navController)
                        "3D Models"-> ThreeDmodelScreen()
                        "Room Redesigner"->RoomRedesignerScreen()
                        else -> HomePageContent()
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(currentScreen: String, onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)) // Purple to Blue gradient
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp) // Set icon size to 20dp (adjust if needed)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

