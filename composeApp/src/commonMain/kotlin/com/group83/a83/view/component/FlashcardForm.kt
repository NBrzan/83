package com.group83.a83.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import com.group83.a83.model.FlashcardModel

@Composable
fun FlashcardForm(
    onAdd: (FlashcardModel) -> Unit = {}
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        OutlinedTextField(value = question, onValueChange = { question = it }, label = { Text("Question") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = answer, onValueChange = { answer = it }, label = { Text("Answer") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            if (question.isNotBlank() && answer.isNotBlank()) {
                val id = Random.nextInt(1, Int.MAX_VALUE)
                onAdd(FlashcardModel(id = id, front = question.trim(), back = answer.trim()))
                question = ""
                answer = ""
            }
        }, enabled = question.isNotBlank() && answer.isNotBlank()) {
            Text("Add Flashcard")
        }
    }
}
