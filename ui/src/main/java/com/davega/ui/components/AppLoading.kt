package com.davega.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davega.ui.R
import kotlinx.coroutines.delay

@Composable
fun AppLoading(
    modifier: Modifier = Modifier,
    label: String? = null
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(modifier = Modifier.size(120.dp)){
            AppAnimation(
                resId = R.raw.loading,
                modifier = Modifier.fillMaxSize()
            )
        }
        label?.let {
            var step by remember {
                mutableStateOf(0)
            }
            LaunchedEffect(Unit){
                while (true){
                    if(step == 3){
                        step = 1
                    } else {
                        step += 1
                    }
                    delay(500)
                }
            }
            Text(
                text = buildString {
                    append(
                        stringResource(
                            id = R.string.loading
                        )
                    )
                    repeat(step){
                        append(".")
                    }
                },
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}