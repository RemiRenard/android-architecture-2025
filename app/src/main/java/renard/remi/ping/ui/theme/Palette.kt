package renard.remi.ping.ui.theme

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import renard.remi.ping.ui.theme.Palette.*

enum class Palette {
    DEFAULT, VIOLET, RED
}

fun getColorSchemeByPalette(
    isDarkTheme: Boolean,
    context: Context,
    isDynamicColors: Boolean,
    palette: Palette?
): ColorScheme {
    return if (isDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (isDarkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        when (palette) {
            DEFAULT -> if (isDarkTheme) greenDarkColorPalette else greenLightColorPalette
            VIOLET -> if (isDarkTheme) violetDarkColorPalette else violetLightColorPalette
            RED -> if (isDarkTheme) redDarkColorPalette else redLightColorPalette
            // It's null when dynamics colors are not supported by the device, so green by default
            null -> if (isDarkTheme) greenDarkColorPalette else greenLightColorPalette
        }
    }
}

fun getPrimaryColorByPalette(
    isDarkTheme: Boolean,
    palette: Palette
): Color {
    return when (palette) {
        DEFAULT -> if (isDarkTheme) Green80 else Green40
        VIOLET -> if (isDarkTheme) Violet80 else Violet40
        RED -> if (isDarkTheme) Red40 else Red60
    }
}

val greenDarkColorPalette = darkColorScheme(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    secondary = DarkGreen80,
    onSecondary = DarkGreen20,
    secondaryContainer = DarkGreen30,
    onSecondaryContainer = DarkGreen90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = GreenGrey30,
    onSurface = GreenGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = GreenGrey30,
    surfaceTint = GreenGrey30,
    onSurfaceVariant = GreenGrey80,
    outline = GreenGrey80
)

val greenLightColorPalette = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,
    inversePrimary = Green80,
    secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = GreenGrey90,
    onSurface = GreenGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = GreenGrey90,
    surfaceTint = GreenGrey90,
    onSurfaceVariant = GreenGrey30,
    outline = GreenGrey50
)

val violetDarkColorPalette = darkColorScheme(
    primary = Violet80,
    onPrimary = Violet20,
    primaryContainer = Violet30,
    onPrimaryContainer = Violet90,
    inversePrimary = Violet40,
    secondary = Yellow80,
    onSecondary = Yellow20,
    secondaryContainer = Yellow30,
    onSecondaryContainer = Yellow90,
    tertiary = Violet40,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Violet30,
    onSurface = Violet90,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Violet30,
    surfaceTint = Violet30,
    onSurfaceVariant = Violet90,
    outline = Violet90
)

val violetLightColorPalette = lightColorScheme(
    primary = Violet40,
    onPrimary = Color.White,
    primaryContainer = Violet90,
    onPrimaryContainer = Violet10,
    inversePrimary = Violet80,
    secondary = Yellow40,
    onSecondary = Color.White,
    secondaryContainer = Yellow90,
    onSecondaryContainer = Yellow10,
    tertiary = Violet20,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Violet90,
    onSurface = Violet30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = Violet90,
    surfaceTint = Violet90,
    onSurfaceVariant = Violet30,
    outline = Violet40
)

val redDarkColorPalette = darkColorScheme(
    primary = Red40,
    onPrimary = Color.White,
    primaryContainer = Red80,
    onPrimaryContainer = Red10,
    inversePrimary = Red50,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Red60,
    onTertiary = Red20,
    tertiaryContainer = Red30,
    onTertiaryContainer = Red90,
    error = Red90,
    onError = Color.White,
    errorContainer = Red10,
    onErrorContainer = Red80,
    background = Grey10,
    onBackground = Grey90,
    surface = Red30,
    onSurface = Red90,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Red40,
    surfaceTint = Red40,
    onSurfaceVariant = Red80,
    outline = Red80
)

val redLightColorPalette = lightColorScheme(
    primary = Red60,
    onPrimary = Color.White,
    primaryContainer = Red90,
    onPrimaryContainer = Red10,
    inversePrimary = Red40,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Red50,
    onTertiary = Color.White,
    tertiaryContainer = Red90,
    onTertiaryContainer = Red10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Red90,
    onSurface = Red30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = Red90,
    surfaceTint = Red90,
    onSurfaceVariant = Red30,
    outline = Red50
)
