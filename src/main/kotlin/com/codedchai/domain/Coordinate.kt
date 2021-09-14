package com.codedchai.domain

data class Coordinate(
  val x: Float,
  val y: Float
) {
  constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
}