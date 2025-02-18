package renard.remi.ping.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import renard.remi.ping.R

object AppFont {

    val PoppinsFont = FontFamily(
        Font(R.font.poppins_regular),
        Font(R.font.poppins_italic, style = FontStyle.Italic),
        Font(R.font.poppins_black, FontWeight.Black),
        Font(R.font.poppins_black_italic, FontWeight.Black, style = FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_medium_italic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_bold_italic, FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.poppins_thin, FontWeight.Thin),
        Font(R.font.poppins_thin_italic, FontWeight.Thin, style = FontStyle.Italic),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_light_italic, FontWeight.Light, style = FontStyle.Italic),
        Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
        Font(R.font.poppins_semi_bold_italic, FontWeight.SemiBold, style = FontStyle.Italic),
        Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
        Font(R.font.poppins_extra_light_italic, FontWeight.ExtraLight, style = FontStyle.Italic),
        Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
        Font(R.font.poppins_extra_bold_italic, FontWeight.ExtraBold, style = FontStyle.Italic),
    )
}
