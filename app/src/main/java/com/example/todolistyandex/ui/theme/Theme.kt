package com.example.todolistyandex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        colorRed = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
    )
}

val lightColors = CustomColors(
    supportSeparator = Black33,
    supportOverlay = Black0F,
    labelPrimary = Black,
    labelSecondary = Black99,
    labelTertiary = Black4D,
    labelDisable = Black26,
    colorRed = RedLight,
    colorGreen = GreenLight,
    colorBlue = BlueLight,
    colorGray = Gray,
    colorGrayLight = GrayLight,
    backPrimary = BackLightPrimary,
    backSecondary = White,
    backElevated = White
)

val darkColors = CustomColors(
    supportSeparator = White33,
    supportOverlay = Black,
    labelPrimary = White,
    labelSecondary = White99,
    labelTertiary = White66,
    labelDisable = White26,
    colorRed = RedDark,
    colorGreen = GreenDark,
    colorBlue = BlueDark,
    colorGray = Gray,
    colorGrayLight = GrayDark,
    backPrimary = BackDarkPrimary,
    backSecondary = BackDarkSecondary,
    backElevated = BackDarkElevated
)

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        content = content
    )
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}