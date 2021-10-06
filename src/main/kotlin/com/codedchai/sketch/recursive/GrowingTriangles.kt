package com.codedchai.sketch.recursive

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Triangle
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

class GrowingTriangles : PApplet() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL
  val triangles = mutableListOf<Triangle>()
  lateinit var pGraphics: PGraphics

  val shouldSaveImage = false
  var shouldSaveAnimation = false

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    pGraphics.strokeWeight(5f)
    pGraphics.noFill()

    triangles.forEach {
      pGraphics.pushMatrix()
      pGraphics.translate(width / 2f, height / 2f)
      pGraphics.rotate(radians(it.rotation))
      pGraphics.stroke(it.color.r, it.color.g, it.color.b)
      val pointA = it.pointA()
      val pointB = it.pointB()
      val pointC = it.pointC()
      pGraphics.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y)
      pGraphics.popMatrix()
    }

    //   pGraphics.filter(GRAY)
    pGraphics.endDraw()

    updateTriangles()

    image(pGraphics, 0f, 0f)

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      val formattedDate = OffsetDateTime.now().toEpochSecond()
      pGraphics.save("${imageDirectory}${GrowingTriangles::class.java.simpleName}-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\"
      val formattedDate = OffsetDateTime.now().toEpochSecond()
      saveFrame("${animationDirectory}${GrowingTriangles::class.java.simpleName}-$formattedDate-######.png");
    }
  }

  override fun keyPressed() {
    shouldSaveAnimation = !shouldSaveAnimation
  }

  fun updateTriangles() {
    triangles.forEach { triangle ->
      triangle.sideLength += 1.1f
      triangle.rotation += .02f
      if (triangle.sideLength > width * 3) {
        triangle.centerPoint = Coordinate(0f, 0f)
        triangle.sideLength = 10f
        triangle.color = colorScheme.randomColor()
        triangle.rotation = 0f
      }
    }
  }

  fun buildInitialTriangles(centerPoint: Coordinate, sideLength: Float, maxSideLength: Float, sideLengthStep: Float) {
    if (sideLength > maxSideLength) {
      return
    }

    triangles.add(Triangle(centerPoint, sideLength, colorScheme.randomColor(), triangles.size.toFloat()))

    buildInitialTriangles(centerPoint, sideLength + sideLengthStep, maxSideLength, sideLengthStep)
  }

  override fun setup() {
    pGraphics = createGraphics(1000, 1000)

    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    surface.setResizable(true)
    surface.setLocation(0, 0)

    buildInitialTriangles(Coordinate(0f, 0f), 10f, width.toFloat() * 3f, 50f)
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }
}

fun main() {
  PApplet.main("${GrowingTriangles::class.java.packageName}.${GrowingTriangles::class.java.simpleName}")
}