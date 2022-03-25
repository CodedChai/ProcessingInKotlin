package com.codedchai.sketch.animation

import com.codedchai.constants.RgbColorConstants
import com.codedchai.domain.Square
import com.codedchai.extensions.drawSquare
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

class Schotter : BaseSketch() {

  val schotterGrid = generateSchotterGrid(8, 12, 100f, 20f)

  private val MARGIN_FROM_SIDE = 100f
  private val MARGIN_FROM_TOP = 100f
  override fun draw() {

    pGraphics.beginDraw()

    pGraphics.translate(MARGIN_FROM_SIDE, MARGIN_FROM_TOP)
    schotterGrid.forEach { pGraphics.drawSquare(it) }
    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    super.draw()
  }

  fun generateSchotterGrid(numColumns: Int, numRows: Int, sideLength: Float, margin: Float): List<Square> {
    val halfMargin = margin / 2f
    return (0 until numColumns).flatMap { x ->
      (0 until numRows).map { y ->
       val factor = y.toFloat() / numRows.toFloat();
       val xOffset = factor * random(-halfMargin, halfMargin)
       val yOffset = factor * random(-halfMargin, halfMargin)
       val rotation = factor * random(-45f, 45f)

        Square(
          xTranslate = x * sideLength + margin * x + xOffset,
          yTranslate = y * sideLength + margin * y + yOffset,
          sideLength = sideLength,
          rotation = rotation,
          color = RgbColorConstants.MILK
        )
      }
    }
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P2D)

    background(0)

    noLoop()
  }

  override fun settings() {
    size(1200, 1600, P2D)
  }
}

fun main() {
  PApplet.main("${Schotter::class.java.packageName}.${Schotter::class.java.simpleName}")
}