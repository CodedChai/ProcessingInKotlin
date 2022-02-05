package com.codedchai.sketch.recursive

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Triangle
import com.codedchai.extensions.drawTriangle
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

// TODO: Fix, one of the updates broke it
class GrowingTriangles : BaseSketch() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL
  val triangles = mutableListOf<Triangle>()

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    pGraphics.strokeWeight(5f)
    pGraphics.noFill()

    triangles.forEach {
      pGraphics.drawTriangle(it, width / 2f, height / 2f)
    }

    //   pGraphics.filter(GRAY)
    pGraphics.endDraw()

    updateTriangles()

    image(pGraphics, 0f, 0f)
    super.draw()
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
    super.setup()
    pGraphics = createGraphics(1000, 1000)

    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    buildInitialTriangles(
      centerPoint = Coordinate(0f, 0f),
      sideLength = 10f,
      maxSideLength = width.toFloat() * 3f,
      sideLengthStep = 50f
    )
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }
}

fun main() {
  PApplet.main("${GrowingTriangles::class.java.packageName}.${GrowingTriangles::class.java.simpleName}")
}