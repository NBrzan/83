package com.group83.a83

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Screen { Home, Creator, Statistics }

@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "App preview", showBackground = true)
@Composable
fun App() {
    MaterialTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selected by remember { mutableStateOf(Screen.Home) }
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .safeContentPadding()
                        .padding(vertical = 8.dp)) {

                        DrawerItem(iconText = "🌐", label = "Angleščina") {
                            // content
                        }

                        DrawerItem(iconText = "🗺️", label = "Geografija") {
                            // content
                        }

                        DrawerItem(iconText = "➕", label = "Add new subject") {
                            // content
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        DrawerItem(iconText = "⚙️", label = "Settings") {
                            // settings
                        }

                        DrawerItem(iconText = "ℹ️", label = "About us") {
                            // about
                        }
                    }
                }
            }
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selected == Screen.Home,
                            onClick = { selected = Screen.Home },
                            icon = { Text("🏠", fontSize = 18.sp, textAlign = TextAlign.Center) },
                            label = { Text("Home") }
                        )

                        NavigationBarItem(
                            selected = selected == Screen.Creator,
                            onClick = { selected = Screen.Creator },
                            icon = { Text("✏️", fontSize = 18.sp, textAlign = TextAlign.Center) },
                            label = { Text("Creator") }
                        )

                        NavigationBarItem(
                            selected = selected == Screen.Statistics,
                            onClick = { selected = Screen.Statistics },
                            icon = { Text("📊", fontSize = 18.sp, textAlign = TextAlign.Center) },
                            label = { Text("Stats") }
                        )
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .safeContentPadding(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top row with menu button to open drawer
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        IconButton(onClick = {
                            // toggle drawer using coroutine scope
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Text("☰", fontSize = 20.sp)
                        }
                    }

                    when (selected) {
                        Screen.Home -> HomeScreen(
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onNavigateToCreator = { selected = Screen.Creator }
                        )
                        Screen.Creator -> CreatorScreen()
                        Screen.Statistics -> StatisticsScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerItem(iconText: String, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(iconText, fontSize = 20.sp)
        Spacer(modifier = Modifier.size(12.dp))
        Text(label)
    }
}

@Composable
private fun CreatorScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Creator mode\nEdit/Add flashcards", modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun StatisticsScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Statistics", modifier = Modifier.padding(16.dp))
    }
}
