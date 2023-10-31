package com.oceanknight.mimaplayground.ui.page.element.typewriter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 * author: Oceanknight
 * date: 2023/10/31
 * describe:
 */
@Preview
@Composable
fun TypeWriterScreen() {
    val animatePartList = remember {
        listOf(
            "first",
            "second",
            "finally"
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        TypeWriterText(
            text = "举一个栗子🌰吧",
            highlightText = "栗子🌰",
            parts = animatePartList
        )
    }
}