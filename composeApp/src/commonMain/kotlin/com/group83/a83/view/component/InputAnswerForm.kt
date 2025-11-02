package com.group83.a83.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.group83.a83.model.InputModel
import com.group83.a83.model.AnswerModel

@Composable
fun InputAnswerForm(
    onAdd: (InputModel) -> Unit = {}
) {
    var question by remember { mutableStateOf("") }
    var newAnswer by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        OutlinedTextField(value = question, onValueChange = { question = it }, label = { Text("Question") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = newAnswer, onValueChange = { newAnswer = it }, label = { Text("Answer") }, modifier = Modifier.fillMaxWidth(0.75f))
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                val t = newAnswer.trim()
                if (t.isNotEmpty()) {
                    answers = answers + t
                    newAnswer = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        // show answers
        answers.forEach { a ->
            Text("- $a", modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            if (question.isNotBlank() && answers.isNotEmpty()) {
                val id = Random.nextInt(1, Int.MAX_VALUE)
                val answerModels = answers.mapIndexed { i, a -> AnswerModel(i, a) }
                onAdd(InputModel(id = id, question = question.trim(), answers = answerModels))
                question = ""
                answers = emptyList()
            }
        }, enabled = question.isNotBlank() && answers.isNotEmpty()) {
            Text("Add Input Question")
        }
    }
}
