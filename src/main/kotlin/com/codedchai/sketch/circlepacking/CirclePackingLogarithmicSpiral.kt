package com.codedchai.sketch.circlepacking

import com.codedchai.constants.RgbColorConstants
import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Circle
import com.codedchai.extensions.backgroundColor
import com.codedchai.extensions.drawCircle
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

class CirclePackingLogarithmicSpiral : PApplet() {

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS
  val circles = mutableListOf<Circle>()
  val spiral = mutableListOf<Circle>()
  lateinit var pGraphics: PGraphics

  val screenWidth = 2250
  val screenHeight = 3000

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.noStroke()
    pGraphics.backgroundColor(colorScheme)

    circles.forEach {
      pGraphics.drawCircle(it)
    }
    // pGraphics.filter(GRAY)
    pGraphics.endDraw()

    val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
    val formattedDate = OffsetDateTime.now().toEpochSecond()
 //   pGraphics.save("${imageDirectory}circle-packing-spiral-output-$formattedDate.tiff")
   image(pGraphics, 0f, 0f)
  }

  fun buildLogarithmicSpiral(a: Float, b: Float, thetaStep: Float, thetaMax: Float): List<Circle> {
    var theta = 0f
    val spiralCircles = mutableListOf<Circle>()

    while (theta < thetaMax) {
      val x = a * cos(theta) * exp(b * theta)
      val y = a * sin(theta) * exp(b * theta)
      val radiusFromTheta = pow(map(pow(theta, 2f), 0f, pow(60f, 2f), 0f, 21f), 2.8f)
      spiralCircles.add(Circle(x + screenWidth / 2f, y + screenHeight / 2f, radiusFromTheta, RgbColorConstants.IMPERIAL_PURPLE))
      theta += thetaStep
    }

    return spiralCircles
  }

  override fun setup() {
    pGraphics = createGraphics(screenWidth, screenHeight)

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
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 110f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 90f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(1f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(3f, 20f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(0f, screenHeight.toFloat()),
      random(1f, 10f)
    )
    addCircleIfNoCollision(
      random(0f, screenWidth.toFloat()),
      random(screenHeight.toFloat() / 2, screenHeight.toFloat()),
      random(1f, 10f)
    )
  }

  fun addCircleIfNoCollision(x: Float, y: Float, radius: Float) {
    val circle = Circle(x, y, radius, colorScheme.randomColor())
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
    size(screenWidth, screenHeight, P2D)
  }
}

fun main() {
  PApplet.main("${CirclePackingLogarithmicSpiral::class.java.packageName}.${CirclePackingLogarithmicSpiral::class.java.simpleName}")
}