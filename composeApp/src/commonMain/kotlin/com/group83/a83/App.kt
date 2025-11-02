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
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.group83.a83.view.NavAndSideBarView
import com.group83.a83.view.ScreenEnum
import com.group83.a83.view.screens.UiState
import com.group83.a83.view.screens.Subject

@Preview(name = "App preview", showBackground = true)
@Composable
fun App() {
    MaterialTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selected by remember { mutableStateOf(ScreenEnum.Home) }
        val scope = rememberCoroutineScope()

        // Remember UiState at the top level so selection persists across the UI
        var uiState by remember { mutableStateOf(UiState()) }

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

        // Call NavAndSideBarView with positional arguments matching its signature
        NavAndSideBarView(
            drawerState,
            selected,
            { selected = it },
            scope,
            uiState,
            { uiState = it },
            onAddSubject
        )
    }
}
