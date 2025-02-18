package renard.remi.ping.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFont.PoppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)