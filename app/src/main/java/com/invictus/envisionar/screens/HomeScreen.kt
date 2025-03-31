
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    .background(MaterialTheme.colorScheme.surfaceVariant)
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (currentScreen) {
                        "Home" -> HomePageContent()
                        "ImageConverter" -> ImageConverter(navController)
                        "ThreeDmodelScreen"-> ThreeDmodelScreen()
                        "RoomRedesignerScreen"->RoomRedesignerScreen()
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
        ){
            Text(
                "Envision AR",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem("Home", Icons.Filled.Home, currentScreen == "Home") { onNavigate("Home") }
        DrawerItem("Image to 3D", Icons.Filled.Home, currentScreen == "Image to 3D") { onNavigate("ImageConverter") }
        DrawerItem("3D Models", Icons.Filled.Home, currentScreen == "3d Models") { onNavigate("ThreeDmodelScreen") }
        DrawerItem("Room Redesigner ", Icons.Filled.Home, currentScreen == "Room Redesigner") { onNavigate("RoomRedesignerScreen") }

    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

