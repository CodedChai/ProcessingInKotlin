package com.codedchai.sketch.circlepacking

import com.codedchai.constants.RgbColorConstants
import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Circle
import processing.core.PApplet
import java.time.OffsetDateTime

class CirclePackingLogarithmicSpiral : PApplet() {

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS
  val circleColorsSize = colorScheme.colors.size
  val circles = mutableListOf<Circle>()
  val spiral = mutableListOf<Circle>()

  override fun draw() {
    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    circles.forEach {
      fill(it.color.r, it.color.g, it.color.b)
      circle(it.x, it.y, it.radius * 2f)
    }

    val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
    val formattedDate = OffsetDateTime.now().toEpochSecond()
    save("${imageDirectory}circle-packing-spiral-output-$formattedDate.png")
  }

  fun buildLogarithmicSpiral(a: Float, b: Float, thetaStep: Float, thetaMax: Float): List<Circle> {
    var theta = 0f
    val spiralCircles = mutableListOf<Circle>()

    while (theta < thetaMax) {
      val x = a * cos(theta) * exp(b * theta)
      val y = a * sin(theta) * exp(b * theta)
      val radiusFromTheta = pow(map(pow(theta, 2f), 0f, pow(60f, 2f), 0f, 21f), 2.8f)
      spiralCircles.add(Circle(x + width / 2f, y + height / 2f, radiusFromTheta, RgbColorConstants.IMPERIAL_PURPLE))
      theta += thetaStep
    }

    return spiralCircles
  }

  override fun setup() {
    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    surface.setResizable(true)
    surface.setLocation(0, 0)
    spiral.addAll(buildLogarithmicSpiral(.5f, .2f, .01f, 80f))

    while (circles.size <= 10000) {
      addCirclesRandomly()
    }

    noLoop()
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
      random(1f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 20f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(1f, 10f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(height.toFloat() / 2, height.toFloat()),
      random(1f, 10f)
    )
  }

  fun addCircleIfNoCollision(x: Float, y: Float, radius: Float) {
    val color = colorScheme.colors[random(circleColorsSize.toFloat()).toInt()]

    val circle = Circle(x, y, radius, color)
    if (spiralContainsCircle(circle) && !collidesWithExistingCircles(circle)) {
      circles.add(circle)
    }
  }

  fun spiralContainsCircle(circle: Circle): Boolean {
    return spiral.any { spiralPoint -> dist(spiralPoint.x, spiralPoint.y, circle.x, circle.y) < (spiralPoint.radius - circle.radius) }
  }

  fun collidesWithExistingCircles(circle: Circle): Boolean {
    return circles.any { dist(it.x, it.y, circle.x, circle.y) < (it.radius + circle.radius) }
  }

  override fun settings() {
    size(1600, 1600, P2D)
  }
}

fun main() {
  PApplet.main("${CirclePackingLogarithmicSpiral::class.java.packageName}.${CirclePackingLogarithmicSpiral::class.java.simpleName}")
}