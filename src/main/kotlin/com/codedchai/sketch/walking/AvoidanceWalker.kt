package com.codedchai.sketch.walking

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Point
import com.codedchai.domain.Vec2
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

// https://www.reddit.com/r/generative/comments/pwi1ch/geometric_avoidance/
class AvoidanceWalker : PApplet() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL
  val points = mutableListOf<Point>()
  lateinit var pGraphics: PGraphics

  val shouldSaveImage = false
  var shouldSaveAnimation = true
  val formattedDate = OffsetDateTime.now().toEpochSecond()

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.strokeWeight(1f)

    points.forEach {
      //pGraphics.pushMatrix()
      //pGraphics.translate(width / 2f, height / 2f)

      pGraphics.stroke(it.color.r, it.color.g, it.color.b)

      pGraphics.point(it.coordinate.x, it.coordinate.y)
      //pGraphics.popMatrix()
    }

    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    updatePoints(pGraphics)

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      pGraphics.save("${imageDirectory}${AvoidanceWalker::class.java.simpleName}-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\"
      saveFrame("${animationDirectory}${AvoidanceWalker::class.java.simpleName}-$formattedDate-######.png");
    }
  }

  fun updatePoints(pGraphics: PGraphics) {
    val pointsToRemove = mutableListOf<Point>()
    points.forEach { point ->
      var newCoordinates = point.coordinate + point.direction!!
      var numTries = 0
      while (pGraphics.get(newCoordinates.x.toInt(), newCoordinates.y.toInt()) != 0) {
        if (numTries > 100) {
          pointsToRemove.add(point)
          break
        }
        println("Collision! ${pGraphics.get(newCoordinates.x.toInt(), newCoordinates.y.toInt())}")
        val newDirection = Vec2(random(-1f, 1f), random(-1f, 1f))
        point.direction = newDirection
        newCoordinates = point.coordinate + point.direction!!
        numTries++
      }
      point.coordinate += point.direction!!
    }

    points.removeAll(pointsToRemove)
  }

  override fun keyPressed() {
    //  shouldSaveAnimation = !shouldSaveAnimation
  }

  fun buildInitialWalkerPoints(radius: Float) {

    for (theta in 0..360 step 2) {
      points.add(Point(Coordinate(width / 2 + radius * sin(radians(theta.toFloat())), height / 2 + radius * cos(radians(theta.toFloat()))), colorScheme.randomColor(), Vec2(random(-1f, 1f), random(-1f, 1f))))
    }
  }

  override fun setup() {
    pGraphics = createGraphics(1000, 1000)

    background(0)
    // background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    surface.setResizable(true)
    surface.setLocation(0, 0)
    buildInitialWalkerPoints(200f)

  }

  override fun settings() {
    size(1000, 1000, P2D)
  }
}

fun main() {
  PApplet.main("${AvoidanceWalker::class.java.packageName}.${AvoidanceWalker::class.java.simpleName}")
}