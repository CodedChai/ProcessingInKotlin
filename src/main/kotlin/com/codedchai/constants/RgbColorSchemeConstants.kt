package com.codedchai.constants

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
  }
}