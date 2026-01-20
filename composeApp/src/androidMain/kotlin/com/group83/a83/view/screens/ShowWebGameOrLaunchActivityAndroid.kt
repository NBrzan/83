package com.group83.a83.view.screens

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun ShowWebGameWithActivity(onBack: () -> Unit) {
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        val intent = Intent(ctx, com.group83.a83.GameActivity::class.java)
        ctx.startActivity(intent)
        onBack()
    }
}
