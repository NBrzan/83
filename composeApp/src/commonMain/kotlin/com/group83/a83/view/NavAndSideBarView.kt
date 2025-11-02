package com.group83.a83.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.view.screens.HomeScreenView
import com.group83.a83.view.screens.CreatorScreenView
import com.group83.a83.view.screens.StatisticsScreenView
import com.group83.a83.view.screens.UiState
import com.group83.a83.view.screens.Subject
import com.group83.a83.view.component.QuestionModel
import com.group83.a83.view.component.MultipleChoiceForm
import com.group83.a83.view.component.InputAnswerForm
import com.group83.a83.view.component.FlashcardForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavAndSideBarView(
    drawerState: androidx.compose.material3.DrawerState,
    selected: ScreenEnum,
    onSelectedChange: (ScreenEnum) -> Unit,
    scope: CoroutineScope,
    uiState: UiState,
    onUiStateChange: (UiState) -> Unit,
    onAddSubject: (String, String) -> Unit,
    createdQuestions: List<QuestionModel>,
    onAddQuestion: (QuestionModel) -> Unit
) {
    // Use uiState.currentSelectedSubject as the source of truth for which subject is selected.

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .safeContentPadding()
                    .padding(vertical = 8.dp)) {

                    // Render subjects dynamically from uiState.subjects
                    uiState.subjects.forEach { subject: Subject ->
                        DrawerItem(
                            iconText = subject.emoji,
                            label = subject.name,
                            isSelected = uiState.currentSelectedSubject == subject.name
                        ) {
                            onUiStateChange(uiState.copy(currentSelectedSubject = subject.name))
                            scope.launch { drawerState.close() }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Navigate to Creator screen to add a new subject
                    DrawerItem(iconText = "➕", label = "Add new subject", isSelected = false) {
                        onSelectedChange(ScreenEnum.Creator)
                        scope.launch { drawerState.close() }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    DrawerItem(iconText = "⚙️", label = "Settings", isSelected = false) {
                        // settings — non-selectable
                        scope.launch { drawerState.close() }
                    }

                    DrawerItem(iconText = "ℹ️", label = "About us", isSelected = false) {
                        // about — non-selectable
                        scope.launch { drawerState.close() }
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
                    ScreenEnum.Home -> HomeScreenView(uiState = uiState)
                    ScreenEnum.Creator -> CreatorScreenView(uiState = uiState, onAddSubject = onAddSubject, createdQuestions = createdQuestions, onOpenForm = { form -> onSelectedChange(form) })
                    ScreenEnum.Statistics -> StatisticsScreenView(uiState = uiState)

                    ScreenEnum.MultipleForm -> {
                        MultipleChoiceForm(onAdd = { q -> onAddQuestion(q) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }
                    ScreenEnum.InputForm -> {
                        InputAnswerForm(onAdd = { q -> onAddQuestion(q) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }
                    ScreenEnum.FlashcardForm -> {
                        FlashcardForm(onAdd = { q -> onAddQuestion(q) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerItem(iconText: String, label: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(iconText, fontSize = 20.sp)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = label,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}