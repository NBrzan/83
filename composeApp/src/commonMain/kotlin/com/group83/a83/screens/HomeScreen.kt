package com.group83.a83

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Small UI state holder for the Home screen
data class HomeUiState(
    val subjectName: String = "",
    val flashcardCount: Int = 0,
    val isLoading: Boolean = false
)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    onOpenDrawer: () -> Unit = {},
    onNavigateToCreator: () -> Unit = {},
    onSelectSubject: (String) -> Unit = {}
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
