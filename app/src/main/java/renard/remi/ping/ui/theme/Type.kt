package renard.remi.ping.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 46.sp,
        letterSpacing = 1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        lineHeight = 42.sp,
        letterSpacing = 1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)