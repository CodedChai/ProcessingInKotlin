package com.codedchai.domain.schotter

import com.codedchai.domain.RgbColor

data class SchotterStone(
  val x: Float = 0f,
  val y: Float = 0f,
  var xTranslate: Float = 0f,
  var yTranslate: Float = 0f,
  val sideLength: Float,
  var rotation: Float = 0f,
  val color: RgbColor,
  var xVelocity: Float = 0f,
  var yVelocity: Float = 0f,
  var rotationVelocity: Float = 0f,
  var cyclesUntilStationary: Int = 0,
  var currentRowNumber: Int = 0,
  var currentColNumber: Int = 0
)
