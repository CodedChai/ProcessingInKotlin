package com.codedchai.domain

data class Square(
  val x: Float,
  val y: Float,
  val sideLength: Float,
  var rotation: Float = 0f,
  val color: RgbColor
)