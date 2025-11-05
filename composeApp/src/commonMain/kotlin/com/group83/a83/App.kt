package com.group83.a83

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import com.group83.a83.cache.DatabaseDriverFactory
import com.group83.a83.cache.FullDatabase
import com.group83.a83.controller.GameContainerController
import com.group83.a83.model.Stats
import com.group83.a83.view.NavAndSideBarView
import com.group83.a83.view.ScreenEnum
import com.group83.a83.view.screens.UiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(name = "App preview", showBackground = true)
@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    MaterialTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selected by remember { mutableStateOf(ScreenEnum.Home) }
        val scope = rememberCoroutineScope()

        val db = remember { FullDatabase(driverFactory) }
        val controller = remember { GameContainerController() }

        var uiState by remember { mutableStateOf(UiState()) }
        //db.ensureDefaultSubjectExists()

        AppRepositories.statsRepository = remember { com.group83.a83.repository.StatsRepository(db) }
        db.saveStats(Stats(0, 0, 100))
        // Wire up the NavBar composable so App actually uses the remembered state
        NavAndSideBarView(
            drawerState = drawerState,
            selected = selected,
            onSelectedChange = { selected = it },
            scope = scope,
            controller = controller,
            db = db,
            uiState = uiState
        )
    }
}