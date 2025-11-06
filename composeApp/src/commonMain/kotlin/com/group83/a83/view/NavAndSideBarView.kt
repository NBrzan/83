package com.group83.a83.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.cache.FullDatabase
import com.group83.a83.view.screens.HomeScreenView
import com.group83.a83.view.screens.CreatorScreenView
import com.group83.a83.view.screens.GameContainerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.group83.a83.controller.GameContainerController
import com.group83.a83.controller.StatsController
import com.group83.a83.model.ABCDModel
import com.group83.a83.model.FlashcardModel
import com.group83.a83.model.GameQuestion
import com.group83.a83.model.GameType
import com.group83.a83.model.ImageABCDModel
import com.group83.a83.model.ImageInputModel
import com.group83.a83.model.InputModel
import com.group83.a83.view.component.FlashcardForm
import com.group83.a83.view.component.InputAnswerForm
import com.group83.a83.view.component.MultipleChoiceForm
import com.group83.a83.view.screens.GameSelectView
import com.group83.a83.view.screens.UiState
import com.group83.a83.view.screens.PreviewAllQuestionsView
import com.group83.a83.view.screens.StatsScreen

@Composable
fun NavAndSideBarView(
    drawerState: androidx.compose.material3.DrawerState,
    selected: ScreenEnum,
    onSelectedChange: (ScreenEnum) -> Unit,
    scope: CoroutineScope,
    db: FullDatabase,
    uiState: UiState
) {
    var subjects by remember { mutableStateOf(db.getAllSubjects()) }
    var selectedSubjectId by remember { mutableStateOf(subjects.firstOrNull()?.id ?: 1) }
    var createdQuestions by remember { mutableStateOf(listOf<GameQuestion>()) }
    var selectedQuestions by remember { mutableStateOf(listOf<GameQuestion>()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .safeContentPadding()
                        .padding(vertical = 8.dp)
                ) {
                    subjects.forEach { subject ->
                        DrawerItem(iconText = "•", label = subject.name) {
                            selectedSubjectId = subject.id
                            scope.launch { drawerState.close() }
                        }
                    }

                    DrawerItem(iconText = "➕", label = "Dodaj nov predmet") {
                        val newName = "Subject ${subjects.size + 1}"
                        db.insertSubject(newName)
                        subjects = db.getAllSubjects()
                        selectedSubjectId = subjects.firstOrNull()?.id ?: selectedSubjectId
                        scope.launch { drawerState.close() }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    DrawerItem(iconText = "⚙️", label = "Settings") { /* settings */ }
                    DrawerItem(iconText = "ℹ️", label = "About us") { /* about */ }
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selected == ScreenEnum.GameSelect,
                        onClick = { onSelectedChange(ScreenEnum.GameSelect) },
                        icon = { Text("🏠", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Domov") }
                    )

                    NavigationBarItem(
                        selected = selected == ScreenEnum.Creator,
                        onClick = { onSelectedChange(ScreenEnum.Creator) },
                        icon = { Text("✏️", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Ustvari") }
                    )

                    NavigationBarItem(
                        selected = selected == ScreenEnum.Statistics,
                        onClick = { onSelectedChange(ScreenEnum.Statistics) },
                        icon = { Text("📊", fontSize = 18.sp, textAlign = TextAlign.Center) },
                        label = { Text("Statistika") }
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .safeContentPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    IconButton(onClick = {
                        scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                    }) {
                        Text("☰", fontSize = 20.sp)
                    }
                }

                val onAddQuestion: (GameQuestion) -> Unit = { q ->
                    when (q) {
                        is FlashcardModel -> db.insertCardWithSubject(subjectId = selectedSubjectId, front = q.front, back = q.back)
                        is ABCDModel -> db.insertABCDQuestionWithAnswers(subjectId = selectedSubjectId, questionText = q.question, answers = q.answers.map { it.answer }, correctPositions = q.correctAnswers)
                        is ImageABCDModel -> db.insertABCDImageQuestionWithAnswers(subjectId = selectedSubjectId, questionText = q.question, answers = q.answers.map { it.answer }, correctPositions = q.correctAnswers, imageSrcText = q.imageSrc)
                        is InputModel -> db.insertInputQuestionWithAnswers(subjectId = selectedSubjectId, questionText = q.question, answers = q.answers.map { it.answer }, imageSrcText = null)
                        is ImageInputModel -> db.insertInputQuestionWithAnswers(subjectId = selectedSubjectId, questionText = q.question, answers = q.answers.map { it.answer }, imageSrcText = q.imageSrc)
                    }
                    createdQuestions = createdQuestions + q
                }

                val onAddSubject: (String, String) -> Unit = { newSubject, emoji ->
                    val trimmed = newSubject.trim()
                    val chosenEmoji = emoji.ifBlank { "📚" }
                    db.insertUniqueSubject("$chosenEmoji $trimmed")
                    subjects = db.getAllSubjects()
                }

                when (selected) {
                    ScreenEnum.Home -> HomeScreenView()

                    ScreenEnum.Creator -> {
                        val subjectQuestions = mutableListOf<GameQuestion>()
                        subjectQuestions.addAll(db.getCardsForSubject(selectedSubjectId))
                        subjectQuestions.addAll(db.getABCDQuestionsForSubject(selectedSubjectId))
                        subjectQuestions.addAll(db.getABCDImageQuestionsForSubject(selectedSubjectId))
                        subjectQuestions.addAll(db.getInputQuestionsForSubject(selectedSubjectId, 0))
                        subjectQuestions.addAll(db.getInputQuestionsForSubject(selectedSubjectId, 1))

                        CreatorScreenView(
                            uiState = uiState,
                            onAddSubject = onAddSubject,
                            createdQuestions = subjectQuestions,
                            onOpenForm = { form -> onSelectedChange(form) }
                        )
                    }

                    //ScreenEnum.Statistics -> StatisticsScreenView()

                    //ScreenEnum.Creator -> CreatorScreenView(uiState = uiState, onAddSubject = onAddSubject, createdQuestions = createdQuestions, onOpenForm = { form -> onSelectedChange(form) })
                    ScreenEnum.Statistics -> StatsScreen(StatsController())
                    ScreenEnum.GameSelect -> GameSelectView(onGameSelected = { gameType: GameType ->
                        scope.launch {
                            when (gameType) {
                                GameType.flashcards -> {
                                    val cards = db.getCardsForSubject(selectedSubjectId)
                                    selectedQuestions = cards
                                }
                                GameType.abcd -> {
                                    val questions = db.getABCDQuestionsForSubject(selectedSubjectId)
                                    selectedQuestions = questions
                                }
                                GameType.imageABCD -> {
                                    val questions = db.getABCDImageQuestionsForSubject(selectedSubjectId)
                                    selectedQuestions = questions
                                }
                                GameType.input -> {
                                    val questions = db.getInputQuestionsForSubject(selectedSubjectId, 0)
                                    selectedQuestions = questions
                                }
                                GameType.imageInput -> {
                                    val questions = db.getInputQuestionsForSubject(selectedSubjectId, 1)
                                    selectedQuestions = questions
                                }
                            }
                            onSelectedChange(ScreenEnum.Game)
                        }
                    })

                    ScreenEnum.Game -> GameContainerView(GameContainerController(questions = selectedQuestions))
                    ScreenEnum.MultipleForm -> {
                        MultipleChoiceForm(onAdd = { q -> onAddQuestion(q); onSelectedChange(ScreenEnum.Creator) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }

                    ScreenEnum.InputForm -> {
                        InputAnswerForm(onAdd = { q -> onAddQuestion(q); onSelectedChange(ScreenEnum.Creator) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }

                    ScreenEnum.FlashcardForm -> {
                        FlashcardForm(onAdd = { q -> onAddQuestion(q); onSelectedChange(ScreenEnum.Creator) })
                        Button(onClick = { onSelectedChange(ScreenEnum.Creator) }) { Text("Back") }
                    }

                    ScreenEnum.PreviewAllQuestionsView -> {
                        PreviewAllQuestionsView(db = db, subjectId = null, onBack = { onSelectedChange(ScreenEnum.Creator) })
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerItem(iconText: String, label: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(iconText, fontSize = 20.sp)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = label,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}