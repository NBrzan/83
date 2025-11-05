package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.model.FlashcardModel
import com.group83.a83.model.GameQuestion
import com.group83.a83.model.InputModel
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.ImageABCDModel
import com.group83.a83.model.ImageInputModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "Preview Questions Preview", showBackground = true)
@Composable
fun PreviewAllQuestionsView(
    createdQuestions: List<GameQuestion> = emptyList(),
    onBack: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            "Pregled vprašanj",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Nazaj")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (createdQuestions.isEmpty()) {
            Text("Ni ustvarjenih vprašanj za ta predmet.", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            return@Column
        }

        Text("Created: ${createdQuestions.size} questions", modifier = Modifier.padding(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(createdQuestions) { q ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        when (q) {
                            is ABCDModel -> {
                                Text("MC: ${q.question}")
                                q.answers.forEachIndexed { idx, a ->
                                    val prefix = if (q.correctAnswers.contains(idx.toLong())) "*" else " "
                                    Text("$prefix ${a.answer}")
                                }
                            }
                            is InputModel -> {
                                Text("Input: ${q.question}")
                                q.answers.forEach { a -> Text("- ${a.answer}") }
                            }
                            is FlashcardModel -> {
                                Text("Flashcard: ${q.front}")
                                Text("-> ${q.back}")
                            }
                            is ImageABCDModel -> {
                                Text("Image MC: ${q.question}")
                                q.answers.forEachIndexed { idx, a ->
                                    val prefix = if (q.correctAnswers.contains(idx.toLong())) "*" else " "
                                    Text("$prefix ${a.answer}")
                                }
                            }
                            is ImageInputModel -> {
                                Text("Image Input: ${q.question}")
                                q.answers.forEach { a -> Text("- ${a.answer}") }
                            }
                        }
                    }
                }
            }
        }
    }
}
