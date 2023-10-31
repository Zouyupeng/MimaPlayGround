package com.oceanknight.mimaplayground.ui.page.element.typewriter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 *
 * author: Oceanknight
 * date: 2023/10/31
 * describe:
 */
@Composable
fun TypeWriterText(
    text: String = "",
    highlightText: String = "",
    parts: List<String> = listOf()
) {
    var partIndex by remember {
        mutableIntStateOf(0)
    }
    var partText by remember {
        mutableStateOf("")
    }
    var textToDisplay = "$text$partText"

    val highlightStart = text.indexOf(highlightText)

    var selectedPartRects by remember { mutableStateOf(listOf<Rect>()) }

    LaunchedEffect(key1 = parts) {
        while (partIndex <= parts.size) {
            val part = parts[partIndex]
            part.forEachIndexed{ charIndex, _ ->
                partText = part.substring(startIndex = 0, endIndex = charIndex + 1)
                delay(100)
            }
            delay(1000)
            part.forEachIndexed{ charIndex, _ ->
                partText = part.substring(startIndex = 0, endIndex = part.length - (charIndex + 1))
                delay(100)
            }
            delay(500)
            partIndex = (partIndex + 1) % parts.size
        }
    }

    Text(
        text =  textToDisplay,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            letterSpacing = -(1.6).sp,
            lineHeight = 52.sp
        ),
        color = Color.Black,
        onTextLayout = { layoutResult ->
            val start = text.length
            val end = textToDisplay.count()
            selectedPartRects = if (start < end) {
                layoutResult.getBoundingBoxesForRange(start = start, end = end - 1)
            } else { emptyList() }

            if (highlightStart >= 0) {
                selectedPartRects = selectedPartRects + layoutResult
                    .getBoundingBoxesForRange(start = highlightStart,
                        end = highlightStart + highlightText.length)
            }
        },
        modifier = Modifier.drawBehind {
            val borderSize = 20.sp.toPx()

            selectedPartRects.forEach{ rect ->
                val selectedRect = rect.translate(0f, -borderSize / 1.5f)
                drawLine(
                    color = Color(0x408559DA),
                    start = Offset(selectedRect.left, selectedRect.bottom),
                    end = selectedRect.bottomRight,
                    strokeWidth = borderSize
                )

            }
        }
    )
}

fun TextLayoutResult.getBoundingBoxesForRange(start: Int, end: Int): List<Rect> {
    var prevRect: Rect? = null
    var firstLineCharRect: Rect? = null
    val boundingBoxes = mutableListOf<Rect>()
    for (i in start..end) {
        val rect = getBoundingBox(i)
        val isLastRect = i == end

        // single char case
        if (isLastRect && firstLineCharRect == null) {
            firstLineCharRect = rect
            prevRect = rect
        }

        // `rect.right` is zero for the last space in each line
        // looks like an issue to me, reported: https://issuetracker.google.com/issues/197146630
        if (!isLastRect && rect.right == 0f) continue

        if (firstLineCharRect == null) {
            firstLineCharRect = rect
        } else if (prevRect != null) {
            if (prevRect.bottom != rect.bottom || isLastRect) {
                boundingBoxes.add(
                    firstLineCharRect.copy(right = prevRect.right)
                )
                firstLineCharRect = rect
            }
        }
        prevRect = rect
    }
    return boundingBoxes
}