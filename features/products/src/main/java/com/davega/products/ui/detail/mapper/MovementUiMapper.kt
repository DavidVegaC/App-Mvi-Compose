package com.davega.products.ui.detail.mapper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davega.products.domain.movement.entities.Movement
import com.davega.products.domain.movement.entities.MovementType
import com.davega.products.ui.utils.money
import com.davega.ui.components.AppCard
import com.davega.ui.utils.toFormatString

@Composable
fun Movement.UiMapper(){
    AppCard(
        Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = date.toFormatString(),
                    fontSize = 12.sp,
                    color = Color(0xFFB6B6B6)
                )
            }
            Text(
                text = money(
                    amount = amount,
                    prefix = when(type){
                        MovementType.INPUT -> "+"
                        MovementType.OUTPUT -> "-"
                    },
                    currency = currency
                ),
                color = when(type){
                    MovementType.INPUT -> MaterialTheme.colorScheme.secondary
                    MovementType.OUTPUT -> MaterialTheme.colorScheme.error
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}