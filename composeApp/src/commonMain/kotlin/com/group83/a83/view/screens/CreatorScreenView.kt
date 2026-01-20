package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.group83.a83.model.GameQuestion
import com.group83.a83.view.ScreenEnum
import com.group83.a83.view.component.CreateQuestionView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "App preview", showBackground = true)
@Composable
fun CreatorScreenView(
    uiState: UiState = UiState(),
    onAddSubject: (String, String) -> Unit = { _, _ -> },
    createdQuestions: List<GameQuestion> = emptyList(),
    onOpenForm: (ScreenEnum) -> Unit = {}
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
        if (uiState.isLoading) {
            Text(
                "Loading...",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                "Ustvari Q/A karte!",
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
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CreateQuestionView("Več izb.", "https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png", onClick = { onOpenForm(ScreenEnum.MultipleForm) })
                CreateQuestionView("Flashcard", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F008%2F854%2F548%2Foriginal%2Fpoker-card-casino-3d-design-elements-free-png.png&f=1&nofb=1&ipt=dc7153b83d6a29256e18f2371cb24da4ca04dff1f772dfeb2925197d717819d4", onClick = { onOpenForm(ScreenEnum.FlashcardForm) })
                CreateQuestionView("Vnosa", "https://cdn4.iconfinder.com/data/icons/remixicon-media/24/image-edit-line-512.png", onClick = { onOpenForm(ScreenEnum.InputForm) })
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onOpenForm(ScreenEnum.valueOf("PreviewAllQuestionsView")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Preglej vprašanja")
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (createdQuestions.isNotEmpty()) {
                Text("Ustvaril: ${createdQuestions.size} vprašanj", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))

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
                onValueChange = { newSubject = it.take(60) },
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