package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.view.component.CreateQuestionView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "App preview", showBackground = true)
@Composable
fun CreatorScreenView(
    uiState: UiState = UiState(),
    onAddSubject: (String, String) -> Unit = { _, _ -> }
) {
    var newSubject by remember { mutableStateOf("") }
    var newEmoji by remember { mutableStateOf("📚") }

    val emojiPicks = listOf("📚", "🌐", "🧪", "🧮", "🔬", "🎨")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {
        // Use uiState so the parameter is not unused and show centered info
        if (uiState.isLoading) {
            Text(
                "Loading...",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                "Ustvari karte!",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Predmet: ${uiState.currentSelectedSubject}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CreateQuestionView("Multi Q/A", "/path")
                CreateQuestionView("Input Q/A", "/path")
            }
            CreateQuestionView("Flashcard Q/A", "/path")

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                "Ustvari nove predmete:",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newSubject,
                onValueChange = { newSubject = it },
                label = { Text("Ime predmeta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newEmoji,
                    onValueChange = { newEmoji = it.take(2) },
                    label = { Text("Emoji") },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    emojiPicks.forEach { e ->
                        IconButton(onClick = { newEmoji = e }, modifier = Modifier.size(40.dp)) {
                            Text(e, fontSize = 20.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val trimmed = newSubject.trim()
                    val emoji = newEmoji.ifBlank { "📚" }
                    if (trimmed.isNotEmpty()) {
                        onAddSubject(trimmed, emoji)
                        newSubject = ""
                        newEmoji = "📚"
                    }
                },
                enabled = newSubject.trim().isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dodaj predmet")
            }
        }
    }
}