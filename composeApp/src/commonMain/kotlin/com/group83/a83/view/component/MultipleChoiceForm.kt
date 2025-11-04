package com.group83.a83.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.ImageABCDModel
import com.group83.a83.model.GameQuestion
import com.group83.a83.model.AnswerModel
import androidx.compose.material3.Checkbox
import androidx.compose.ui.Alignment

@Composable
fun MultipleChoiceForm(
    onAdd: (GameQuestion) -> Unit = {}
) {
    var question by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf("", "", "", "")) }
    var correct by remember { mutableStateOf(listOf(false, false, false, false)) }
    var includeImage by remember { mutableStateOf(false) }
    var imageSrc by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Question") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        answers.forEachIndexed { index, answer ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = answer,
                    onValueChange = { v -> answers = answers.toMutableList().also { it[index] = v } },
                    label = { Text("Answer ${index + 1}") },
                    modifier = Modifier.fillMaxWidth(0.85f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = correct[index],
                    onCheckedChange = { checked -> correct = correct.toMutableList().also { it[index] = checked } }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = includeImage, onCheckedChange = { includeImage = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Include image")
        }

        if (includeImage) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = imageSrc, onValueChange = { imageSrc = it }, label = { Text("Image URL or path") }, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            val nonEmpty = answers.mapIndexed { i, a -> i to a.trim() }.filter { it.second.isNotEmpty() }
            val hasCorrect = nonEmpty.any { (i, _) -> correct[i] }
            if (question.isNotBlank() && nonEmpty.isNotEmpty() && hasCorrect && (!includeImage || imageSrc.isNotBlank())) {
                val answerModels = answers.mapIndexed { i, a -> AnswerModel(i, a.trim()) }
                val id = Random.nextInt(1, Int.MAX_VALUE)
                val correctAnswers = correct.mapIndexedNotNull { i, c -> if (c && answers[i].isNotBlank()) i.toLong() else null }
                if (includeImage && imageSrc.isNotBlank()) {
                    onAdd(ImageABCDModel(id = id, imageSrc = imageSrc.trim(), question = question.trim(), answers = answerModels, correctAnswers = correctAnswers))
                } else {
                    val model = ABCDModel(id = id, question = question.trim(), answers = answerModels, correctAnswers = correctAnswers)
                    onAdd(model)
                }

                question = ""
                answers = listOf("", "", "", "")
                correct = listOf(false, false, false, false)
                includeImage = false
                imageSrc = ""
            }
        }, enabled = question.isNotBlank() && (!includeImage || imageSrc.isNotBlank())) {
            Text("Add Multiple Choice")
        }
    }
}
