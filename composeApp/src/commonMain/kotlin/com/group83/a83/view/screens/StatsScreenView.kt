package com.group83.a83.view.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.group83.a83.controller.GameContainerController
import com.group83.a83.controller.StatsController
import com.group83.a83.model.Stats

@Composable
fun StatsScreen(controller: StatsController) {
    val stats by remember { controller.stats }.collectAsState()

    val slices = listOf(
        PieSlice(stats.correct, Color(0xFF4CAF50)),
        PieSlice(stats.incorrect, Color(0xFFF44336)),
        PieSlice(stats.unanswered, Color(0xFF9E9E9E))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7D4)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tvoj napredek",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E3E3E),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Pie Chart
        PieChart(slices)

        Spacer(modifier = Modifier.height(24.dp))

        // Count items
        StatItem("✅ Pravilno", stats.correct, Color(0xFF4CAF50))
        StatItem("❌ Nepravilno", stats.incorrect, Color(0xFFF44336))
        StatItem("⏳ Neodgovorjeno", stats.unanswered, Color(0xFF9E9E9E))
    }
}

data class PieSlice(val value: Int, val color: Color)

@Composable
fun PieChart(slices: List<PieSlice>, modifier: Modifier = Modifier) {
    val total = slices.sumOf { it.value }.takeIf { it > 0 } ?: 1

    Canvas(modifier = modifier.size(200.dp)) {
        var startAngle = -90f
        slices.forEach { slice ->
            val sweep = (slice.value.toFloat() / total) * 360f
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true
            )
            startAngle += sweep
        }
    }
}

@Composable
fun StatItem(label: String, value: Int, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 20.sp, color = tint)
        Text(value.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = tint)
    }
}