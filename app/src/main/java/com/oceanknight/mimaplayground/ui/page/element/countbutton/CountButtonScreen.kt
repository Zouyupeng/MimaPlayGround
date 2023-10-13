package com.oceanknight.mimaplayground.ui.page.element.countbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.oceanknight.mimaplayground.ui.page.element.volumebar.ProgressBar

/**
 *
 * @author: Oceanknight
 * @date: 2023/10/13
 * @describe:
 */
@Composable
fun CountButtonScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var valueCounter by remember {
                mutableIntStateOf(0)
            }
            CountButton(
                value = valueCounter.toString(),
                onValueIncreaseClick = {
                    valueCounter++
                },
                onValueDecreaseClick = {
                    valueCounter--
                },
                onValueClearClick = {
                    valueCounter = 0
                }
            )
        }
    }
}