package com.davega.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
){
    Box(
        modifier = Modifier
            .then(modifier)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(14.dp),
                spotColor = Color(0x85929292)
            )
            .background(
                color = color,
                shape = RoundedCornerShape(14.dp)
            )
            .run {
                if(onClick != null){
                    clickable(
                        onClick = onClick
                    )
                } else {
                    this
                }
            }
    ) {
        content()
    }
}