package com.example.appcontatos.ui.contact.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.appcontatos.ui.theme.AppContatosTheme
import kotlin.math.absoluteValue

private fun String.toHslColor(
    saturation: Float = 0.5f,
    lightness: Float = 0.5f
): Int {
    val hue: Int = fold(0) { acc, char -> acc + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@Composable
fun ContactAvatar(
    modifier: Modifier = Modifier,
    firstName: String,
    lastName: String,
    size: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val name = "$firstName $lastName".trim()
        val color = Color(name.toHslColor())
        val initials = (firstName.take(1) + lastName.take(1)).uppercase()
        Canvas(modifier = modifier.fillMaxSize()) {
            drawCircle(SolidColor(color), radius = size.toPx() / 2)
        }
        Text(
            text = initials,
            style = textStyle,
            color = Color.White,

        )
    }

}

@Preview(showBackground = true)
@Composable
fun ContactAvatarPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        ContactAvatar(
            modifier = modifier,
            firstName = "John",
            lastName = "Doe",
        )
    }
}