import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.invictus.envisionar.screens.SideNav.HomePageContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("Home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
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
                    title = { Text(currentScreen) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open drawer")
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (currentScreen) {
                        "Home" -> HomePageContent()
                        "Settings" -> SettingsPageContent()
                        "Profile" -> ProfilePageContent()
                        else -> HomePageContent()
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Navigation", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

        DrawerItem("Home") { onNavigate("Home") }
        DrawerItem("Home") { onNavigate("Home") }
        DrawerItem("Settings") { onNavigate("Settings") }
        DrawerItem("Profile") { onNavigate("Profile") }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    )
}



@Composable
fun SettingsPageContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Settings Page")
    }
}

@Composable
fun ProfilePageContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Profile Page")
    }
}