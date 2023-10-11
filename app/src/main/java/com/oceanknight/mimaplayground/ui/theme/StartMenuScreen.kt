package com.oceanknight.mimaplayground.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oceanknight.mimaplayground.NavRoute

/**
 *
 * @author: zouyupeng
 * @date: 2023/10/11
 * @describe:
 */
@Composable
fun StartMenuScreen(
    modifier: Modifier = Modifier,
    navigateTo: (NavRoute) -> Unit
) {
    Scaffold(
        modifier = Modifier
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            itemsIndexed(items = NavRoute.values()) { index, item ->
                Text(
                    text = index.toString() + ". " + item.name,
                    modifier = Modifier
                        .clickable {
                            navigateTo(item)
                        }
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp),
                    fontSize = 25.sp
                )

            }
        }
    }
}