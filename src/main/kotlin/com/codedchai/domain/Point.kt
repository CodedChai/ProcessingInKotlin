package com.codedchai.domain

data class Point(
  var coordinate: Coordinate,
  val color: RgbColor,
  var direction: Vec2? = null
)
