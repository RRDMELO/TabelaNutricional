package com.rocketseat.RRM.tabelanutricional.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rocketseat.RRM.tabelanutricional.R

val Poppins = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
)

val Inter = FontFamily(
    Font(R.font.inter, FontWeight.Normal)
)

val Typography = Typography(
    // Headings (Poppins)
    headlineLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),

    // Subheading
    titleSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),

    // Numbers
    bodyLarge = TextStyle( // number-md
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle( // number-sm
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),

    // Texts (Inter)
    displayLarge = TextStyle( // text1
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = (32.sp * 1.2f) // 120%
    ),
    displayMedium = TextStyle( // text2
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = (24.sp * 1.2f) // 120%
    )
)
