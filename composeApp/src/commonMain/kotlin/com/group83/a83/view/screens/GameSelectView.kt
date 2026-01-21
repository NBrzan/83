package com.group83.a83.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.model.GameType
import com.group83.a83.view.component.SimpleGameDisplay
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            .verticalScroll(rememberScrollState())
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
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.abcd) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleGameDisplay(
                        "Več možnosti",
                        "https://cdn4.iconfinder.com/data/icons/top-search-7/128/_list_bullets_points_checklist_choose--512.png",
                        modifier = Modifier
                    )
                }
                Column(
                    modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.input) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.imageABCD) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleGameDisplay(
                        "Več možnosti za sliko",
                        "https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png"
                    )
                }
                Column(
                    modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.imageInput) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleGameDisplay(
                        "Odgovor na vnos slike",
                        "https://cdn4.iconfinder.com/data/icons/remixicon-media/24/image-edit-line-512.png"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier.weight(1f).clickable { onGameSelected(GameType.flashcards) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleGameDisplay("Flashcard Q/A", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F008%2F854%2F548%2Foriginal%2Fpoker-card-casino-3d-design-elements-free-png.png&f=1&nofb=1&ipt=dc7153b83d6a29256e18f2371cb24da4ca04dff1f772dfeb2925197d717819d4")
                }
                Column(
                    modifier = Modifier.weight(1f).clickable { onWikipediaSelected() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleGameDisplay(
                        "Igra",
                        "https://cdn-icons-png.flaticon.com/512/10736/10736914.png"
                    )
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}