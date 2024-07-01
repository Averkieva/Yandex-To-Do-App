package com.example.todolistyandex.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistyandex.ui.theme.CustomColors
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.theme.darkColors
import com.example.todolistyandex.ui.theme.lightColors

@Composable
fun ColorPalettePreview(colors: CustomColors) {
    Column(modifier = Modifier.padding(16.dp)) {
        ColorBox(color = colors.supportSeparator, label = "Support Separator")
        ColorBox(color = colors.supportOverlay, label = "Support Overlay")
        ColorBox(color = colors.labelPrimary, label = "Label Primary")
        ColorBox(color = colors.labelSecondary, label = "Label Secondary")
        ColorBox(color = colors.labelTertiary, label = "Label Tertiary")
        ColorBox(color = colors.labelDisable, label = "Label Disable")
        ColorBox(color = colors.colorRed, label = "Color Red")
        ColorBox(color = colors.colorGreen, label = "Color Green")
        ColorBox(color = colors.colorBlue, label = "Color Blue")
        ColorBox(color = colors.colorGray, label = "Color Gray")
        ColorBox(color = colors.colorGrayLight, label = "Color Gray Light")
        ColorBox(color = colors.backPrimary, label = "Back Primary")
        ColorBox(color = colors.backSecondary, label = "Back Secondary")
        ColorBox(color = colors.backElevated, label = "Back Elevated")
    }
}

@Composable
fun ColorBox(color: Color, label: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.body1)
    }
}

@Preview(showBackground = true)
@Composable
fun LightThemeColorPalettePreview() {
    CustomTheme(darkTheme = false) {
        ColorPalettePreview(colors = lightColors)
    }
}

@Preview(showBackground = true)
@Composable
fun DarkThemeColorPalettePreview() {
    CustomTheme(darkTheme = true) {
        ColorPalettePreview(colors = darkColors)
    }
}

@Preview(showBackground = true)
@Composable
fun LightThemeTypographyPreview() {
    CustomTheme(darkTheme = false) {
        TypographyPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkThemeTypographyPreview() {
    CustomTheme(darkTheme = true) {
        TypographyPreview()
    }
}

@Composable
fun TypographyPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Large Title", style = MaterialTheme.typography.h1)
        Text(text = "Title", style = MaterialTheme.typography.h2)
        Text(text = "Subtitle", style = MaterialTheme.typography.subtitle1)
        Text(text = "Button", style = MaterialTheme.typography.button)
        Text(text = "Subtitle 2", style = MaterialTheme.typography.subtitle2)
        Text(text = "Body", style = MaterialTheme.typography.body1)
    }
}