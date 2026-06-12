package com.qyupaww.jetpackcomposedigidex.util

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.neoBrutalismStyle(
    shadowColor: Color = Color.Black,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 12.dp,
    offsetX: Dp = 4.dp,
    offsetY: Dp = 4.dp
): Modifier = composed {
    this
        .drawBehind {
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(offsetX.toPx(), offsetY.toPx()),
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
            )
        }
        .border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(cornerRadius)
        )
}
