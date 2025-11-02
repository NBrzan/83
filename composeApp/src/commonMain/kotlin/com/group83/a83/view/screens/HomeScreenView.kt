package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.group83.a83.view.screens.UiState

@Composable
fun HomeScreenView(
    uiState: UiState = UiState()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            Text("Loading...", style = MaterialTheme.typography.bodyLarge)
        } else {
            Text("Subject: ${uiState.subjectName}", style = MaterialTheme.typography.headlineSmall)
            Text("Flashcards: ${uiState.flashcardCount}", modifier = Modifier.padding(top = 8.dp))
        }
    }
}
