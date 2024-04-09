package com.davega.products.ui.detail.mapper

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davega.products.R
import com.davega.products.domain.product.entities.Currency
import com.davega.products.domain.product.entities.Product
import com.davega.products.ui.utils.money
import com.davega.ui.components.AppCard

@Composable
fun Product.UiMapperDetail(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedText = stringResource(id = R.string.shared_text, numberAccount)
    AppCard(
        color = when (currency) {
            Currency.PEN -> Color(0xFFF5ED9F)
            Currency.USD -> Color(0xFFB0F1A5)
        }
    ) {
        Column {
            Row(
                modifier = modifier
                    .height(110.dp)
            ){
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background
                    )
                    .padding(
                        16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.7f)
                ) {
                    Text(
                        text = stringResource(id = R.string.number_account),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = numberAccount,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF676767)
                    )
                }
                Button(
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                sharedText
                            )
                            type = "text/plain"
                        }
                        context.startActivity(
                            Intent.createChooser(sendIntent, null)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = CircleShape
                ) {
                    Text(text = stringResource(id = R.string.share))
                }
            }
        }
    }
}