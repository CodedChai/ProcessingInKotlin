package com.codedchai.domain

data class Vec2(
  var x: Float,
  var y: Float
) {
  constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

  operator fun plus(increment: Int): Vec2 {
    return Vec2(x + increment, y + increment)
  }

  operator fun plus(increment: Float): Vec2 {
    return Vec2(x + increment, y + increment)
  }

  operator fun plus(vec2: Vec2): Vec2 {
    return Vec2(x + vec2.x, y + vec2.y)
  }

  operator fun minus(increment: Int): Vec2 {
    return Vec2(x - increment, y - increment)
  }

  operator fun minus(increment: Float): Vec2 {
    return Vec2(x - increment, y - increment)
  }

  operator fun minus(vec2: Vec2): Vec2 {
    return Vec2(x - vec2.x, y - vec2.y)
  }

}