package com.davega.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davega.ui.R

@Composable
fun AppRetry(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Button(onClick = onClick) {
            Text(text = stringResource(id = R.string.str_retry))
        }
    }
}