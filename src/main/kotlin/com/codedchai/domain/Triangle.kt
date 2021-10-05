package com.codedchai.domain

import kotlin.math.sqrt

data class Triangle(
  val centerPoint: Coordinate,
  val sideLength: Float,
  val color: RgbColor,
  val isUpsideDown: Boolean = false
) {

  constructor(x: Int, y: Int, sideLength: Float, color: RgbColor, isUpsideDown: Boolean = false) : this(Coordinate(x.toFloat(), y.toFloat()), sideLength, color, isUpsideDown)
  constructor(x: Float, y: Float, sideLength: Float, color: RgbColor, isUpsideDown: Boolean = false) : this(Coordinate(x, y), sideLength, color, isUpsideDown)

  fun pointA(): Coordinate {
    return if (isUpsideDown) {
      bottomPoint()
    } else {
      topPoint()
    }
  }

  fun pointB(): Coordinate {
    return if (isUpsideDown) {
      topRightPoint()
    } else {
      bottomRightPoint()
    }
  }

  fun pointC(): Coordinate {
    return if (isUpsideDown) {
      topLeftPoint()
    } else {
      bottomLeftPoint()
    }
  }

  fun topPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x,
      y = centerPoint.y - (sqrt(3f) / 3f) * sideLength // Subtract because zero is at the top and positive numbers go down
    )
  }

  fun bottomLeftPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x - (sideLength / 2f),
      y = centerPoint.y + (sqrt(3f) / 6f) * sideLength // Add because zero is at the top and positive numbers go down
    )
  }

  fun bottomRightPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x + (sideLength / 2f),
      y = centerPoint.y + (sqrt(3f) / 6f) * sideLength // Add because zero is at the top and positive numbers go down
    )
  }

  fun bottomPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x,
      y = centerPoint.y + (sqrt(3f) / 3f) * sideLength // Add because zero is at the top and positive numbers go down
    )
  }

  fun topLeftPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x - (sideLength / 2f),
      y = centerPoint.y - (sqrt(3f) / 6f) * sideLength // Subtract because zero is at the top and positive numbers go down
    )
  }

  fun topRightPoint(): Coordinate {
    return Coordinate(
      x = centerPoint.x + (sideLength / 2f),
      y = centerPoint.y - (sqrt(3f) / 6f) * sideLength // Subtract because zero is at the top and positive numbers go down
    )
  }
}