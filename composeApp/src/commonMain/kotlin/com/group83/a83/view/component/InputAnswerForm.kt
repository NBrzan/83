package com.group83.a83.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
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
import com.group83.a83.model.ImageInputModel
import com.group83.a83.model.GameQuestion
import com.group83.a83.model.AnswerModel
import androidx.compose.material3.Checkbox
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.coil3.CoilImage

@Preview
@Composable
fun InputAnswerForm(
    onAdd: (GameQuestion) -> Unit = {}
) {
    var question by remember { mutableStateOf("") }
    var newAnswer by remember { mutableStateOf("") }
    var answers by remember { mutableStateOf(listOf<String>()) }
    var includeImage by remember { mutableStateOf(false) }
    var imageSrc by remember { mutableStateOf("") }
    var showPreview by remember { mutableStateOf(false) }

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
        answers.forEach { a ->
            Text("- $a", modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = includeImage, onCheckedChange = { includeImage = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Include image")
        }

        if (includeImage) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = imageSrc, onValueChange = { imageSrc = it }, label = { Text("Image URL (or leave blank to pick from gallery)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            ImagePickerButton(onImagePicked = { uri -> imageSrc = uri })

            if (imageSrc.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                CoilImage(
                    imageModel = { imageSrc },
                    modifier = Modifier
                        .size(200.dp)
                        .clickable { showPreview = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            if (question.isNotBlank() && answers.isNotEmpty() && (!includeImage || imageSrc.isNotBlank())) {
                val id = Random.nextInt(1, Int.MAX_VALUE)
                val answerModels = answers.mapIndexed { i, a -> AnswerModel(i, a) }
                if (includeImage && imageSrc.isNotBlank()) {
                    onAdd(ImageInputModel(id = id, imageSrc = imageSrc.trim(), question = question.trim(), answers = answerModels))
                } else {
                    onAdd(InputModel(id = id, question = question.trim(), answers = answerModels))
                }

                question = ""
                answers = emptyList()
                includeImage = false
                imageSrc = ""
            }
        }, enabled = question.isNotBlank() && answers.isNotEmpty() && (!includeImage || imageSrc.isNotBlank())) {
            Text("Add Input Question")
        }
    }

    if (showPreview) {
        FullScreenImagePreview(imageSrc = imageSrc) { showPreview = false }
    }
}
