package com.davega.products.ui.products.mappers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davega.products.R
import com.davega.products.domain.product.entities.Currency
import com.davega.products.domain.product.entities.Product
import com.davega.products.ui.utils.money
import com.davega.ui.components.AppCard

@Composable
fun Product.UiMapper(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppCard(
        modifier = modifier
            .height(110.dp),
        color = when (currency) {
            Currency.PEN -> Color(0xFFF5ED9F)
            Currency.USD -> Color(0xFFB0F1A5)
        },
        onClick = onClick
    ){
        Row {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = when(currency){
                        Currency.PEN -> R.drawable.ic_pen
                        Currency.USD -> R.drawable.ic_usd
                    }),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(
                            topStart = 56.dp,
                            bottomEnd = 14.dp,
                            topEnd = 14.dp
                        )
                    )
                    .fillMaxHeight()
                    .padding(horizontal = 27.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = money(
                        amount = amount,
                        currency = currency
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF676767)
                )
            }
        }
    }
}