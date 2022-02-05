package com.codedchai.sketch.recursive

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Triangle
import com.codedchai.extensions.backgroundColor
import com.codedchai.extensions.drawTriangle
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

class TriangleContainingTriangle : BaseSketch() {

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS
  val triangles = mutableListOf<Triangle>()

  val sideLengthStep = 10f

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.backgroundColor(colorScheme)

    pGraphics.strokeWeight(5f)
    pGraphics.noFill()

    triangles.forEach {
      pGraphics.drawTriangle(it)
    }

    //   pGraphics.filter(GRAY)
    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)
    super.draw()
  }

  fun buildTriangles(sideLength: Float) {

    val numColumns = ((.9f * width) / sideLength).toInt() - 1
    val numRows = ((.9f * height) / sideLength).toInt() - 1

    val totalLength = width.toFloat() / (numColumns + 1)
    val totalHeight = height.toFloat() / (numRows + 1)

    val xStart = totalLength / 2f
    val yStart = totalHeight / 1.7f

    (0..numColumns).forEach { columnNumber ->
      (0..numRows).forEach { rowNumber ->
        buildTrianglesAtPoint(
          Coordinate(xStart + totalLength * columnNumber, yStart + totalHeight * rowNumber),
          sideLength,
          false
        )
      }
    }

  }

  fun buildTrianglesAtPoint(centerPoint: Coordinate, sideLength: Float, isUpsideDown: Boolean = false) {
    if (sideLength < 20f) {
      return
    }

    triangles.add(Triangle(centerPoint, sideLength, colorScheme.randomColor(), 0f, isUpsideDown))

    buildTrianglesAtPoint(centerPoint, sideLength - sideLengthStep, isUpsideDown)
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(1000, 1000)

    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    buildTriangles(width / 15f)

    noLoop()
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }
}

fun main() {
  PApplet.main("${TriangleContainingTriangle::class.java.packageName}.${TriangleContainingTriangle::class.java.simpleName}")
}