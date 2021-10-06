package com.codedchai.domain

data class Coordinate(
  var x: Float,
  var y: Float
) {
  constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

  operator fun plus(increment: Int): Coordinate {
    return Coordinate(x + increment, y + increment)
  }

  operator fun plus(increment: Float): Coordinate {
    return Coordinate(x + increment, y + increment)
  }

  operator fun plus(coordinate: Coordinate): Coordinate {
    return Coordinate(x + coordinate.x, y + coordinate.y)
  }

  operator fun plus(vec2: Vec2): Coordinate {
    return Coordinate(x + vec2.x, y + vec2.y)
  }
  
  operator fun minus(increment: Int): Coordinate {
    return Coordinate(x - increment, y - increment)
  }

  operator fun minus(increment: Float): Coordinate {
    return Coordinate(x - increment, y - increment)
  }

  operator fun minus(coordinate: Coordinate): Coordinate {
    return Coordinate(x - coordinate.x, y - coordinate.y)
  }

}