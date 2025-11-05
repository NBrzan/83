package com.group83.a83.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.group83.a83.cache.FullDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "Preview Questions Preview", showBackground = true)
@Composable
fun PreviewAllQuestionsView(
    createdQuestions: List<GameQuestion> = emptyList(),
    db: FullDatabase? = null,
    subjectId: Long? = null,
    onBack: () -> Unit = {}
) {
    // Build initial list from DB or provided list
    val initialQuestions: List<GameQuestion> = if (db != null) {
        val all = mutableListOf<GameQuestion>()
        if (subjectId != null) {
            all.addAll(db.getCardsForSubject(subjectId))
            all.addAll(db.getABCDQuestionsForSubject(subjectId))
            all.addAll(db.getABCDImageQuestionsForSubject(subjectId))
            all.addAll(db.getInputQuestionsForSubject(subjectId, 0))
            all.addAll(db.getInputQuestionsForSubject(subjectId, 1))
        } else {
            // collect across all subjects
            db.getAllSubjects().forEach { subj ->
                all.addAll(db.getCardsForSubject(subj.id))
                all.addAll(db.getABCDQuestionsForSubject(subj.id))
                all.addAll(db.getABCDImageQuestionsForSubject(subj.id))
                all.addAll(db.getInputQuestionsForSubject(subj.id, 0))
                all.addAll(db.getInputQuestionsForSubject(subj.id, 1))
            }
        }
        all
    } else {
        createdQuestions
    }

    // Remember a mutable state list so deletes update the UI immediately
    val displayQuestions = remember { mutableStateListOf<GameQuestion>().apply { addAll(initialQuestions) } }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            "Pregled vseh vprašanj",
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

        if (displayQuestions.isEmpty()) {
            Text("Ni ustvarjenih vprašanj za ta predmet.", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            return@Column
        }

        Text("Created: ${displayQuestions.size} questions", modifier = Modifier.padding(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(displayQuestions) { q ->
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
                                if (db != null) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            db.deleteAbcdQuestionById(q.id.toLong())
                                            displayQuestions.remove(q)
                                        }) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                            is InputModel -> {
                                Text("Input: ${q.question}")
                                q.answers.forEach { a -> Text("- ${a.answer}") }
                                if (db != null) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            db.deleteInputQuestionById(q.id.toLong())
                                            displayQuestions.remove(q)
                                        }) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                            is FlashcardModel -> {
                                Text("Flashcard: ${q.front}")
                                Text("-> ${q.back}")
                                if (db != null) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            db.deleteFlashcardById(q.id.toLong())
                                            displayQuestions.remove(q)
                                        }) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                            is ImageABCDModel -> {
                                Text("Image MC: ${q.question}")
                                q.answers.forEachIndexed { idx, a ->
                                    val prefix = if (q.correctAnswers.contains(idx.toLong())) "*" else " "
                                    Text("$prefix ${a.answer}")
                                }
                                if (db != null) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            db.deleteAbcdImageQuestionById(q.id.toLong())
                                            displayQuestions.remove(q)
                                        }) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                            is ImageInputModel -> {
                                Text("Image Input: ${q.question}")
                                q.answers.forEach { a -> Text("- ${a.answer}") }
                                if (db != null) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Button(onClick = {
                                            db.deleteInputQuestionById(q.id.toLong())
                                            displayQuestions.remove(q)
                                        }) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}