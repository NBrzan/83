package com.group83.a83.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.view.component.SimpleGameDisplay
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.group83.a83.model.GameType

@Preview(name = "App preview", showBackground = true)
@Composable
fun GameSelectView(
    uiState: UiState = UiState(),
    onGameSelected: (GameType) -> Unit = {},
    onWikipediaSelected: () -> Unit = {}
) {

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
                "Izberi igro",
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

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.abcd) }) {
                    SimpleGameDisplay(
                        "Več možnosti",
                        "https://cdn4.iconfinder.com/data/icons/top-search-7/128/_list_bullets_points_checklist_choose--512.png",
                        modifier = Modifier
                    )
                }
                Column(modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.input) }) {
                    SimpleGameDisplay(
                        "Vnesite odgovor",
                        "https://cdn1.iconfinder.com/data/icons/radix/15/input-512.png",
                        modifier = Modifier
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.clickable { onGameSelected(GameType.imageABCD) }) {
                    SimpleGameDisplay(
                        "Več možnosti za sliko",
                        "https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png"
                    )
                }
                Column(modifier = Modifier.clickable { onGameSelected(GameType.imageInput) }) {
                    SimpleGameDisplay(
                        "Odgovor na vnos slike",
                        "https://cdn4.iconfinder.com/data/icons/remixicon-media/24/image-edit-line-512.png"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.clickable { onGameSelected(GameType.flashcards) }) {
                    SimpleGameDisplay("Flashcard Q/A", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F008%2F854%2F548%2Foriginal%2Fpoker-card-casino-3d-design-elements-free-png.png&f=1&nofb=1&ipt=dc7153b83d6a29256e18f2371cb24da4ca04dff1f772dfeb2925197d717819d4")
                }
                Column(modifier = Modifier.clickable { onWikipediaSelected() }) {
                    SimpleGameDisplay(
                        "Igra",
                        "https://imgs.search.brave.com/QzTLbWnVF-JPlkBmf4yp-nrlR1U4K-exj6kxSkMG7Ng/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9zdGF0/aWMudmVjdGVlenku/Y29tL3N5c3RlbS9y/ZXNvdXJjZXMvdGh1/bWJuYWlscy8wNTQv/MDY2LzUxOC9zbWFs/bC9jb250cm9sbGVy/LWdhbWUtaWNvbi1p/bi1ibGFjay1jaXJj/bGUtZnJlZS1wbmcu/cG5n"
                    )
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}