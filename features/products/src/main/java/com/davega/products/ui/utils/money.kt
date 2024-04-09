package com.davega.products.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.davega.products.R
import com.davega.products.domain.product.entities.Currency
import java.math.BigDecimal
import java.text.DecimalFormat

@Composable
fun money(
    amount: Number,
    decimals: Int = 2,
    currency: Currency? = null,
    prefix: String = ""
): String {
    val str = amount.format(decimals = decimals)
    return when (currency) {
        Currency.PEN -> "${stringResource(id = R.string.sol_money_symbol)} $prefix$str"
        Currency.USD -> "${stringResource(id = R.string.dollar_money_symbol)} $prefix$str"
        else -> str
    }
}

@Composable
fun money(
    amount: String,
    decimals: Int = 2,
    currency: Currency? = null
): String {
    val str = BigDecimal(amount).format(decimals = decimals)
    return when (currency) {
        Currency.PEN -> "${stringResource(id = R.string.sol_money_symbol)} $str"
        Currency.USD -> "${stringResource(id = R.string.dollar_money_symbol)} $str"
        else -> str
    }
}

fun Number.format(decimals: Int): String {
    return if (this is BigDecimal) {
        format(decimals)
    } else {
        "%.${decimals}f".format(this.toDouble())
    }.normalizedSeparators()
}

internal fun BigDecimal.format(decimals: Int): String {
    val df = DecimalFormat("#,##0.${"0".repeat(decimals)}")
    return df.format(this)
}

internal fun String.normalizedSeparators(): String {
    return replace(",", ".")
        .split(".")
        .run {
            if (size == 1) {
                this
            } else {
                val last = last()
                val first = filter { it !== last }.joinToString("")
                listOf(first, last)
            }
        }
        .mapIndexed { index, s ->
            when (index) {
                0 -> s.reversed().chunked(3).joinToString(",").reversed()
                else -> s
            }
        }
        .joinToString(".")
}