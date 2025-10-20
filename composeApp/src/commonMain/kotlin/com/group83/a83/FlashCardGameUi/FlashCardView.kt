package com.group83.a83.FlashCardGameUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import com.group83.a83.model.FlashCardDeck
import com.group83.a83.model.FlashCard
import com.group83.a83.model.Result


import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val CARD_HEIGHT = 0.4f
private const val CARD_WIDTH = 0.3f

@Composable
fun flashCardText(cardResult: Result<FlashCard, String>, showBack: Boolean): String {
    return when (cardResult) {
        is Result.Ok -> if (showBack) cardResult.value.back else cardResult.value.front
        is Result.Err -> "No card"
    }
}

@Preview
@Composable
fun FlashcardScreen(deck: FlashCardDeck) {
    var showBack by remember { mutableStateOf(false) }
    var cardResult by remember { mutableStateOf(deck.getCurrentCard()) }
    if (cardResult is Result.Err){
        return
    }


    Row(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Button(onClick = {
            val res = deck.prevCard()
            if (res is Result.Ok){
                cardResult = res
                showBack = false
            }

        },
            modifier = Modifier.fillMaxHeight(CARD_HEIGHT)
        ) {
            Text("<-")
        }
        Card(
            onClick = { showBack = !showBack },
            modifier = Modifier
                .fillMaxWidth(CARD_WIDTH)
                .fillMaxHeight(CARD_HEIGHT),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    flashCardText(cardResult, showBack),
                    textAlign = TextAlign.Center
                )
            }

        }
        Button(onClick = {
            val res = deck.nextCard()
            if (res is Result.Ok){
                cardResult = res
                showBack = false
            }
        },
            modifier = Modifier.fillMaxHeight(CARD_HEIGHT)
        ) {
            Text("->")
        }
    }

}
