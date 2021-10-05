package com.codedchai.sketch.recursive

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Coordinate
import com.codedchai.domain.Triangle
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

class TriangleContainingTriangle : PApplet() {

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS
  val triangles = mutableListOf<Triangle>()
  lateinit var pGraphics: PGraphics

  val sideLengthStep = 50f

  val shouldSaveImage = true

  override fun draw() {
    pGraphics.beginDraw()
    pGraphics.background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    pGraphics.strokeWeight(5f)
    pGraphics.noFill()

    triangles.forEach {
      pGraphics.stroke(it.color.r, it.color.g, it.color.b)
      val pointA = it.pointA()
      val pointB = it.pointB()
      val pointC = it.pointC()
      pGraphics.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y)
    }

    //   pGraphics.filter(GRAY)
    pGraphics.endDraw()

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      val formattedDate = OffsetDateTime.now().toEpochSecond()
      pGraphics.save("${imageDirectory}${TriangleContainingTriangle::class.java.simpleName}-$formattedDate.png")
    }
    image(pGraphics, 0f, 0f)
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
        buildTrianglesAtPoint(Coordinate(xStart + totalLength * columnNumber, yStart + totalHeight * rowNumber), sideLength, false)
      }
    }

  }

  fun buildTrianglesAtPoint(centerPoint: Coordinate, sideLength: Float, isUpsideDown: Boolean = false) {
    if (sideLength < 20f) {
      return
    }

    triangles.add(Triangle(centerPoint, sideLength, colorScheme.randomColor(), isUpsideDown))

    buildTrianglesAtPoint(centerPoint, sideLength - sideLengthStep, isUpsideDown)
  }

  override fun setup() {
    pGraphics = createGraphics(1000, 1000)

    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
    surface.setResizable(true)
    surface.setLocation(0, 0)

    buildTriangles(width / 5f)

    noLoop()
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }
}

fun main() {
  PApplet.main("${TriangleContainingTriangle::class.java.packageName}.${TriangleContainingTriangle::class.java.simpleName}")
}