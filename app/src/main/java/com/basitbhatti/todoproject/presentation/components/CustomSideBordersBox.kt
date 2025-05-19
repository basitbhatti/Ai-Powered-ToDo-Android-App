package com.basitbhatti.todoproject.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSideBordersBox(
    borderColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    drawTop: Boolean = false,
    drawBottom: Boolean = false,
    drawStart: Boolean = false,
    drawEnd: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier
        .padding(borderWidth) // content padding inside the border
        .wrapContentSize()
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidthPx = borderWidth.toPx()
            val width = size.width
            val height = size.height

            if (drawTop) {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(width, 0f),
                    strokeWidth = strokeWidthPx
                )
            }
            if (drawBottom) {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, height),
                    end = Offset(width, height),
                    strokeWidth = strokeWidthPx
                )
            }
            if (drawStart) {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, height),
                    strokeWidth = strokeWidthPx
                )
            }
            if (drawEnd) {
                drawLine(
                    color = borderColor,
                    start = Offset(width, 0f),
                    end = Offset(width, height),
                    strokeWidth = strokeWidthPx
                )
            }
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(borderWidth)
        ) {
            content()
        }
    }
}
