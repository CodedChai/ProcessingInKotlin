package com.codedchai.sketch.walking

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Point
import com.codedchai.domain.Vec2
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet
import processing.core.PGraphics

// https://www.reddit.com/r/generative/comments/pwi1ch/geometric_avoidance/
class AvoidanceWalker : BaseSketch() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL
  val points = mutableListOf<Point>()

  val directionalVectors = listOf(
    Vec2(1, 1),
    Vec2(1, 0),
    Vec2(1, -1),
    Vec2(0, 1),
    Vec2(0, -1),
    Vec2(-1, 1),
    Vec2(-1, 0),
    Vec2(-1, -1)
  )

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
    super.draw()
  }

  fun getPossibleNewCoordinates(point: Point): Coordinate {
    if (random(0f, 100f) < 0.1f) {
      point.direction = directionalVectors.random()
    }
    return point.coordinate + point.direction!!
  }

  fun updatePoints(pGraphics: PGraphics) {
    val pointsToRemove = mutableListOf<Point>()
    points.forEach { point ->

      var newCoordinates = getPossibleNewCoordinates(point)
      var numTries = 0
      while (pGraphics.get(newCoordinates.x.toInt(), newCoordinates.y.toInt()) != 0) {
        if (numTries >= directionalVectors.size) {
          pointsToRemove.add(point)
          break
        }
        // println("Collision! ${pGraphics.get(newCoordinates.x.toInt(), newCoordinates.y.toInt())}")
        val newDirection = directionalVectors[numTries]
        point.direction = newDirection
        newCoordinates = point.coordinate + point.direction!!
        numTries++
      }
      point.coordinate += point.direction!!
    }

    points.removeAll(pointsToRemove)
  }

  fun buildInitialWalkerPoints(radius: Float) {

    for (theta in 0..360 step 1) {
      points.add(
        Point(
          coordinate = Coordinate(
            width / 2 + radius * sin(radians(theta.toFloat())),
            height / 2 + radius * cos(radians(theta.toFloat()))
          ),
          direction = directionalVectors.random(),
          color = colorScheme.randomColor()
        )
      )
    }
  }

  override fun setup() {
    pGraphics = createGraphics(width, height)

    background(0)
    // background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    surface.setResizable(true)
    surface.setLocation(0, 0)
    buildInitialWalkerPoints(200f)
    buildInitialWalkerPoints(202f)

    buildInitialWalkerPoints(20f)

    buildInitialWalkerPoints(400f)
    buildInitialWalkerPoints(402f)

    buildInitialWalkerPoints(600f)
    buildInitialWalkerPoints(602f)

    buildInitialWalkerPoints(900f)
    buildInitialWalkerPoints(902f)
  }

  override fun settings() {
    size(1400, 1400, P2D)
  }
}

fun main() {
  PApplet.main("${AvoidanceWalker::class.java.packageName}.${AvoidanceWalker::class.java.simpleName}")
}