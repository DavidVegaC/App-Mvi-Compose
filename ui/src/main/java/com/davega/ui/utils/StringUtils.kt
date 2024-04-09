package com.davega.ui.utils

import java.util.regex.Pattern

fun String.isNumber(): Boolean {
    return Regex("^[0-9]*$").matches(this)
}

fun String.containsEmoji(): Boolean {
    val regex = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+")
    val matcher = regex.matcher(this)
    return matcher.find()
}