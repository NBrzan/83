package com.group83.a83.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun CreateQuestionView(
    text: String,
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier
        .padding(8.dp)
        .clickable { onClick() }
    ) {
        Text(text, modifier = Modifier.padding(4.dp))
        CoilImage(
            imageModel = { imagePath },
            modifier = Modifier
                .size(100.dp)
                .defaultMinSize(120.dp, 120.dp)
                .padding(15.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}