package com.codedchai.domain

data class Square(
  val x: Float = 0f,
  val y: Float = 0f,
  val xTranslate: Float = 0f,
  val yTranslate: Float = 0f,
  val sideLength: Float,
  var rotation: Float = 0f,
  val color: RgbColor
)