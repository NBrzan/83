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
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.group83.a83.view.NavAndSideBarView
import com.group83.a83.view.ScreenEnum

@Preview(name = "App preview", showBackground = true)
@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    MaterialTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var selected by remember { mutableStateOf(ScreenEnum.Home) }
        val scope = rememberCoroutineScope()

        val db = remember { FullDatabase(driverFactory) }
        val controller = remember { GameContainerController() }

        //fullDatabase.insertCard(front = "Sample Front", back = "Sample Back")
        val cards = db.getAllCards()
        controller.setQuestions(cards)



        // Wire up the NavBar composable so App actually uses the remembered state
        NavAndSideBarView(
            drawerState = drawerState,
            selected = selected,
            onSelectedChange = { selected = it },
            scope = scope
        )


    }
}
