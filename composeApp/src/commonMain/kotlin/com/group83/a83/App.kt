package com.group83.a83

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import com.group83.a83.cache.DatabaseDriverFactory
import com.group83.a83.cache.FullDatabase
import com.group83.a83.controller.GameContainerController
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.group83.a83.view.NavAndSideBarView
import com.group83.a83.view.ScreenEnum
import com.group83.a83.view.screens.UiState
import com.group83.a83.view.screens.Subject
import com.group83.a83.model.GameQuestion

@Preview(name = "App preview", showBackground = true)
@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    MaterialTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selected by remember { mutableStateOf(ScreenEnum.Home) }
        val scope = rememberCoroutineScope()

        // Remember UiState at the top level so selection persists across the UI
        var uiState by remember { mutableStateOf(UiState()) }

        // Remember created questions at the app level so separate form screens can add to it
        var createdQuestions by remember { mutableStateOf(listOf<GameQuestion>()) }

        // Handler to add a subject if it doesn't already exist
        val onAddSubject: (String, String) -> Unit = { newSubject, emoji ->
            val trimmed = newSubject.trim()
            val chosenEmoji = if (emoji.isBlank()) "📚" else emoji
            if (trimmed.isNotEmpty()) {
                val exists = uiState.subjects.any { it.name == trimmed }
                uiState = if (!exists) {
                    uiState.copy(subjects = uiState.subjects + Subject(trimmed, chosenEmoji), currentSelectedSubject = trimmed)
                } else {
                    // if exists, update its emoji and select it
                    val updated = uiState.subjects.map { if (it.name == trimmed) it.copy(emoji = chosenEmoji) else it }
                    uiState.copy(subjects = updated, currentSelectedSubject = trimmed)
                }
            }
        }

        val onAddQuestion: (GameQuestion) -> Unit = { q ->
            createdQuestions = createdQuestions + q
            // After adding, navigate back to Creator screen to show list
            selected = ScreenEnum.Creator
        }

        // Call NavAndSideBarView with positional arguments matching its signature
        val db = remember { FullDatabase(driverFactory) }
        val controller = remember { GameContainerController() }
        //db.ensureDefaultSubjectExists()

        // Wire up the NavBar composable so App actually uses the remembered state
        NavAndSideBarView(
            drawerState = drawerState,
            selected = selected,
            onSelectedChange = { selected = it },
            scope = scope,
            controller = controller,
            db = db
            drawerState,
            selected,
            { selected = it },
            scope,
            uiState,
            { uiState = it },
            onAddSubject,
            createdQuestions,
            onAddQuestion
        )


    }
}
