package com.oceanknight.mimaplayground.ui.page.element.volumebar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 * @author: Oceanknight
 * @date: 2023/10/11
 * @describe:
 */
@Preview
@Composable
fun VolumeBarScreen(
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBar()
        }
    }
}