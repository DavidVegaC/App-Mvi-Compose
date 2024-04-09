package com.davega.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormatString(): String {
    val strFormat = "dd 'de' MMMM 'del' yyyy"
    return SimpleDateFormat(strFormat, Locale("es")).format(this)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}