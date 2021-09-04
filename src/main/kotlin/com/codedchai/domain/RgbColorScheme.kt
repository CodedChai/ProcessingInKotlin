package com.codedchai.domain

data class RgbColorScheme(
  val backgroundColor: RgbColor? = null,
  val foregroundColor: RgbColor? = null,
  val primaryColor: RgbColor? = null,
  val secondaryColor: RgbColor? = null,
  val tertiaryColor: RgbColor? = null,
  val colors: List<RgbColor> = listOf()
)
