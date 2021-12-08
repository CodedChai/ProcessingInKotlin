package com.codedchai.sketch.animation

import com.codedchai.constants.RgbColorConstants
import com.codedchai.domain.Square
import com.codedchai.extensions.drawSquare
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

class Schotter : PApplet() {

  lateinit var pGraphics: PGraphics

  val shouldSaveImage = false
  var shouldSaveAnimation = false
  val formattedDate = OffsetDateTime.now().toEpochSecond()

  val schotterGrid = generateSchotterGrid(8, 12, 100f, 20f)

  override fun draw() {
    pGraphics.beginDraw()

    schotterGrid.forEach { pGraphics.drawSquare(it) }

    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      pGraphics.save("${imageDirectory}${Schotter::class.java.simpleName}-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\$formattedDate-${Schotter::class.java.simpleName}\\"
      saveFrame("${animationDirectory}${Schotter::class.java.simpleName}-######.png")
    }
  }

  fun generateSchotterGrid(width: Int, height: Int, sideLength: Float, margin: Float): List<Square> {
    return (0 until width).flatMap { x ->
      (0 until height).map { y ->
        Square(
          x = x * sideLength + margin * x,
          y = y * sideLength + margin * y,
          sideLength = sideLength,
          color = RgbColorConstants.MILK
        )
      }
    }
  }

  override fun setup() {
    pGraphics = createGraphics(width, height, P2D)

    background(0)

    surface.setResizable(true)
    surface.setLocation(0, 0)
    noLoop()
  }

  override fun settings() {
    size(1300, 1700, P2D)
  }
}

fun main() {
  PApplet.main("${Schotter::class.java.packageName}.${Schotter::class.java.simpleName}")
}