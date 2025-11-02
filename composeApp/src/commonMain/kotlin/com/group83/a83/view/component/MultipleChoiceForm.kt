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

@Composable
fun MultipleChoiceForm(
    onAdd: (QuestionModel.MultipleChoice) -> Unit = {}
) {
    var question by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf("", "", "", "")) }
    var correct by remember { mutableStateOf(listOf(false, false, false, false)) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Question") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // four answer inputs with checkbox
        answers.forEachIndexed { index, answer ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
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

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            // Validate: at least one non-empty answer and at least one correct among non-empty
            val nonEmpty = answers.mapIndexed { i, a -> i to a.trim() }.filter { it.second.isNotEmpty() }
            val hasCorrect = nonEmpty.any { (i, _) -> correct[i] }
            if (question.isNotBlank() && nonEmpty.isNotEmpty() && hasCorrect) {
                onAdd(QuestionModel.MultipleChoice(question.trim(), answers.map { it.trim() }, correct))
                // reset
                question = ""
                answers = listOf("", "", "", "")
                correct = listOf(false, false, false, false)
            }
        }, enabled = question.isNotBlank()) {
            Text("Add Multiple Choice")
        }
    }
}
