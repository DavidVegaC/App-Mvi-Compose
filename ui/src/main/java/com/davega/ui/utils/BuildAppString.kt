package com.davega.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

inline fun buildAppString(builder: (BuilderApp).()->Unit): AnnotatedString {
    return BuilderApp().apply(builder).build()
}

class BuilderApp {

    private val builder: AnnotatedString.Builder = AnnotatedString.Builder()

    fun append(
        text: String,
        color: Color = Color.Unspecified,
        fontWeight: FontWeight? = null
    ){
        builder.apply {
            withStyle(SpanStyle(color = color, fontWeight = fontWeight)){
                append(text)
            }
        }
    }

    fun build(): AnnotatedString = builder.toAnnotatedString()

}