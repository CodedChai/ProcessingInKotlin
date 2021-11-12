package com.codedchai.sketch.circlepacking

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Circle
import processing.core.PApplet
import kotlin.random.Random

class CirclePackingRandomlyRealtime : PApplet() {

  val maxScreenSize = 1600
  val random = Random(maxScreenSize) // use resolution as the seed for some consistency

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS

  val circleColorsSize = colorScheme.colors.size
  val circles: MutableList<Circle> = mutableListOf()

  override fun setup() {
    noStroke()
    smooth()
    surface.setTitle("Random Circle Packing in Realtime")
    surface.setResizable(true)
    surface.setLocation(100, 100)
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
  }

  override fun draw() {
    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    addCirclesRandomly()

    circles.forEach {
      fill(it.color.r, it.color.g, it.color.b)
      circle(it.x, it.y, it.radius * 2f)
    }

  }

  private fun addCirclesRandomly() {
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 110f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 90f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 20f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 10f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(height.toFloat() / 2, height.toFloat()),
      random(5f, 10f)
    )
  }

  fun addCircleIfNoCollision(x: Float, y: Float, radius: Float) {
    val color = colorScheme.colors[random.nextInt(circleColorsSize)]

    val circle = Circle(x, y, radius, color)
    if (!collidesWithExistingCircles(circle)) {
      circles.add(circle)
    }
  }

  fun collidesWithExistingCircles(circle: Circle): Boolean {
    return circles.any { dist(it.x, it.y, circle.x, circle.y) < (it.radius + circle.radius) }
  }
}

fun main() {
  PApplet.main("com.codedchai.sketch.CirclePackingRandomlyRealtime")
}
