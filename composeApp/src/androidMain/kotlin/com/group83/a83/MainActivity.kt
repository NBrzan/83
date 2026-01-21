package com.group83.a83

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.group83.a83.cache.AndroidDatabaseDriverFactory
import com.group83.a83.cache.DatabaseDriverFactory
import com.group83.a83.cache.FullDatabase
import com.group83.a83.controller.GameContainerController
import com.group83.a83.model.GameQuestion
import com.group83.a83.view.screens.GameContainerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val driver = AndroidDatabaseDriverFactory(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            App(driver)
        }
    }
}

