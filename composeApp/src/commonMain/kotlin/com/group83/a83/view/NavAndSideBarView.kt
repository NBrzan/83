package com.group83.a83.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.view.screens.HomeScreenView
import com.group83.a83.view.screens.CreatorScreenView
import com.group83.a83.view.screens.GameContainerView
import com.group83.a83.view.screens.GameSelectView
import com.group83.a83.view.screens.StatisticsScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.group83.a83.controller.GameContainerController
import com.group83.a83.cache.FullDatabase
import com.group83.a83.model.GameType

@Composable
fun NavAndSideBarView(
    drawerState: androidx.compose.material3.DrawerState,
    selected: ScreenEnum,
    onSelectedChange: (ScreenEnum) -> Unit,
    scope: CoroutineScope,
    controller: GameContainerController,
    db: FullDatabase
) {
    var subjects by remember { mutableStateOf(db.getAllSubjects()) }
    var selectedSubjectId by remember { mutableStateOf(subjects.firstOrNull()?.id ?: 1) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .safeContentPadding()
                    .padding(vertical = 8.dp)) {

                    // List subjects dynamically
                    subjects.forEach { subject ->
                        DrawerItem(iconText = "•", label = subject.name) {
                            selectedSubjectId = subject.id
                            // Close drawer
                            scope.launch { drawerState.close() }
                        }
                    }

                    // Add new subject (simple quick action)
                    DrawerItem(iconText = "➕", label = "Add new subject") {
                        val newName = "Subject ${subjects.size + 1}"
                        db.insertSubject(newName)
                        subjects = db.getAllSubjects()
                        selectedSubjectId = subjects.firstOrNull()?.id ?: selectedSubjectId
                        scope.launch { drawerState.close() }
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
                        selected = selected == ScreenEnum.Home,
                        onClick = { onSelectedChange(ScreenEnum.Home) },
                        icon = { Text("🏠", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = selected == ScreenEnum.Creator,
                        onClick = { onSelectedChange(ScreenEnum.Creator) },
                        icon = { Text("✏️", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Creator") }
                    )

                    NavigationBarItem(
                        selected = selected == ScreenEnum.Statistics,
                        onClick = { onSelectedChange(ScreenEnum.Statistics) },
                        icon = { Text("📊", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Stats") }
                    )
                    NavigationBarItem(
                        selected = selected == ScreenEnum.GameSelect,
                        onClick = { onSelectedChange(ScreenEnum.GameSelect) },
                        icon = { Text(":)", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Select Game") }
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
                    ScreenEnum.Home -> HomeScreenView()
                    ScreenEnum.Creator -> CreatorScreenView()
                    ScreenEnum.Statistics -> StatisticsScreenView()
                    ScreenEnum.GameSelect -> GameSelectView(onGameSelected = { gameType: GameType ->
                        scope.launch {
                            when (gameType) {
                                GameType.flashcards -> {
                                    val cards = db.getCardsForSubject(selectedSubjectId)
                                    controller.setQuestions(cards)
                                }
                                GameType.abcd -> {
                                    val questions = db.getABCDQuestionsForSubject(selectedSubjectId)
                                    controller.setQuestions(questions)
                                }
                                else -> {
                                    // TODO: load other game types
                                }
                            }
                            onSelectedChange(ScreenEnum.Game)
                        }
                    })
                    ScreenEnum.Game -> GameContainerView(controller)
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
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(12.dp))
        Text(label)
    }
}