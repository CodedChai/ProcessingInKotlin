package com.codedchai.constants

import com.codedchai.constants.RgbColorConstants.Companion.BOYSENBERRY
import com.codedchai.constants.RgbColorConstants.Companion.CADET_BLUE
import com.codedchai.constants.RgbColorConstants.Companion.CHAMPAGNE_PINK
import com.codedchai.constants.RgbColorConstants.Companion.CORN_YELLOW
import com.codedchai.constants.RgbColorConstants.Companion.IMPERIAL_PURPLE
import com.codedchai.constants.RgbColorConstants.Companion.JAPANESE_VIOLET
import com.codedchai.constants.RgbColorConstants.Companion.ORANGE_RED
import com.codedchai.constants.RgbColorConstants.Companion.SALMON
import com.codedchai.constants.RgbColorConstants.Companion.SUNRAY
import com.codedchai.constants.RgbColorConstants.Companion.WATERMELON_RED
import com.codedchai.constants.RgbColorConstants.Companion.YANKEES_BLUE
import com.codedchai.domain.RgbColorScheme

class RgbColorSchemeConstants {
  companion object {
    val GREEN_PASTELS = RgbColorScheme(
      backgroundColor = RgbColorConstants.ALABASTAR,
      colors = listOf(
        RgbColorConstants.DARK_SEA_GREEN,
        RgbColorConstants.MILK,
        RgbColorConstants.SAGE,
        RgbColorConstants.DESERT_SAND,
        RgbColorConstants.TAUPE_GRAY
      )
    )

    val LOVERS_SUNSET = RgbColorScheme(
      backgroundColor = RgbColorConstants.ALABASTAR,
      colors = listOf(
        CORN_YELLOW,
        ORANGE_RED,
        WATERMELON_RED,
        BOYSENBERRY,
        IMPERIAL_PURPLE,
        JAPANESE_VIOLET
      )
    )

    val SOFT_AND_ROYAL = RgbColorScheme(
      backgroundColor = CHAMPAGNE_PINK,
      colors = listOf(
        YANKEES_BLUE,
        CADET_BLUE,
        SALMON,
        SUNRAY
      )
    )
  }
}