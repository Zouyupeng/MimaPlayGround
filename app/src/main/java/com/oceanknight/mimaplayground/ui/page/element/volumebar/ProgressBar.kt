package com.oceanknight.mimaplayground.ui.page.element.volumebar

import android.util.Log
import android.widget.ProgressBar
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

/**
 *
 * @author: Oceanknight
 * @date: 2023/10/11
 * @describe:
 */
@Preview
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    songTime: Long = 215L
) {
    val progressBarState = remember {
        ProgressBarState()
    }
    var currentTime by remember { mutableLongStateOf(0) }
    LaunchedEffect(progressBarState.isPlaying) {
        while(isActive && progressBarState.isPlaying) {
            progressBarState.timePlayed = currentTime.convertSecondToMinute()
            delay(1000L)
            currentTime++

            if (currentTime > songTime) {
                currentTime = 0
                progressBarState.isPlaying = false
                break
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeText(time = progressBarState.timePlayed)
            VolumeBar(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                isPlaying = progressBarState.isPlaying
            )
            TimeText(time = songTime.convertSecondToMinute())
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PlayButton(
                isPlaying = progressBarState.isPlaying
            ) {
                progressBarState.isPlaying = !progressBarState.isPlaying
            }
        }
    }

}

@Composable
fun TimeText(
    modifier: Modifier = Modifier,
    time: String
) {
    Text(
        modifier = modifier,
        text = time,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        maxLines = 1,
    )
}

@Composable
fun PlayButton(
    isPlaying: Boolean = false,
    onClick: () -> Unit = {}
) {
    // TODO: 查一下icon与button的两个点击区域不同,波纹特效不一致该怎么解决.
    IconButton(
        modifier = Modifier
            .size(70.dp)
            .border(1.dp, Color.Black, CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 32.dp),
                onClick = onClick
            ),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "PlayArrow"
        )

    }
}

const val MAX_LINES_COUNT = 200

@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false
) {
    val infiniteAnimation = rememberInfiniteTransition(label = "无限循环")
    val animations = mutableListOf<State<Float>>()
    val random = remember {
        Random(System.currentTimeMillis())
    }
    val heightDivider by animateFloatAsState(
        targetValue = if (isPlaying) 1f else 6f,
        animationSpec = tween(1000, easing = LinearEasing), label = "音量柱高度分母"
    )

    repeat(15) {
        val durationMills = random.nextInt(500, 2000)
        animations += infiniteAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMills),
                repeatMode = RepeatMode.Reverse,
            ), label = "条形柱基础随机高度百分比"
        )
    }

    val initialMultipliers = remember{
        mutableListOf<Float>().apply {
            repeat(MAX_LINES_COUNT) {
                this += random.nextFloat()
            }
        }
    }

//    val barColor by animateColorAsState(targetValue = )
    val barColor = MaterialTheme.colorScheme.primaryContainer

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val barWidthFloat = 12f
        val gapWidthFloat = 20f

        val count = ((canvasWidth / (barWidthFloat + gapWidthFloat)).toInt() - 2).coerceAtMost(MAX_LINES_COUNT)
        val animatedVolumeWidth = count * (barWidthFloat + gapWidthFloat)
        var startOffset = (canvasWidth - animatedVolumeWidth) / 2

        val barMinHeight = 0f
        val barMaxHeight = canvasHeight / 2f / heightDivider

        val brush = Brush.horizontalGradient(
            colors = listOf(Color(0xff12c2e9), Color(0xffc471ed), Color(0xfff64f59))
        )

        repeat(count) { index ->
            val currentSize = animations[index % animations.size].value
            var barHeightPercent = initialMultipliers[index] + currentSize
            if (barHeightPercent > 1.0f) {
                barHeightPercent = 2 - barHeightPercent
            }
            val barHeight = lerpF(barMinHeight, barMaxHeight, barHeightPercent)
            Log.d("Zyp", "barHeight : $barHeight  currentSize : $currentSize  barHeightPercent : $barHeightPercent  barMaxHeight : $barMaxHeight")
            drawLine(
//                color = barColor,
                brush = brush,
                start = Offset(startOffset, canvasHeight / 2 - barHeight / 2),
                end = Offset(startOffset, canvasHeight / 2 + barHeight / 2),
                strokeWidth = barWidthFloat,
                cap = StrokeCap.Round
            )
            startOffset += barWidthFloat + gapWidthFloat
        }
    }
}

fun lerpF(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop

class ProgressBarState() {
    var isPlaying by mutableStateOf(false)
    var timePlayed by mutableStateOf("00:00")
}