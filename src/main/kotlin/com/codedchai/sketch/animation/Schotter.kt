package com.codedchai.sketch.animation

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.schotter.SchotterOffsets
import com.codedchai.domain.schotter.SchotterStone
import com.codedchai.extensions.drawSchotterStone
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet
import kotlin.random.Random

class Schotter : BaseSketch() {

  private val MARGIN_FROM_SIDE = 110f
  private val MARGIN_FROM_TOP = 10f
  private val PERCENT_OF_STONE_MOTION = .3f
  private val MARGIN_BETWEEN_STONES = 20f
  private val NUM_ROWS = 11

  private val schotterGrid: Sequence<SchotterStone> = generateSchotterGrid(8, NUM_ROWS, 100f, MARGIN_BETWEEN_STONES)

  override fun draw() {
    pGraphics.beginDraw()
    background(66)
    pGraphics.translate(MARGIN_FROM_SIDE, MARGIN_FROM_TOP)

    schotterGrid.forEach { stone -> pGraphics.drawSchotterStone(stone) }

    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    super.draw()

    updateSchotterGrid()
  }

  fun generateSchotterGrid(numColumns: Int, numRows: Int, sideLength: Float, margin: Float): Sequence<SchotterStone> {
    val halfMargin = margin / 2f
    return (0 until numColumns).flatMap { x ->
      (0 until numRows).map { y ->
        val factor = y.toFloat() / numRows.toFloat();
        val (xOffset, yOffset, rotation) = getSchotterOffsets(factor, halfMargin)

        SchotterStone(
          xTranslate = x * sideLength + margin * x + xOffset,
          yTranslate = y * sideLength + margin * y + yOffset,
          sideLength = sideLength,
          rotation = rotation,
          color = RgbColorSchemeConstants.GREEN_PASTELS.randomColor(),
          currentRowNumber = y,
          currentColNumber = x
        )
      }
    }.asSequence()
  }

  fun getSchotterOffsets(factor: Float, offsetFromCenter: Float): SchotterOffsets {
    return SchotterOffsets(
      xOffset = factor * random(-offsetFromCenter, offsetFromCenter),
      yOffset = factor * random(-offsetFromCenter, offsetFromCenter),
      rotation = factor * random(-55f, 55f)
    )
  }

  fun updateSchotterGrid() {
    schotterGrid.forEach { stone -> updateSchotterStone(stone) }
  }

  fun updateSchotterStone(stone: SchotterStone) {
    stone.apply {
      if (cyclesUntilStationary > 0) {
        cyclesUntilStationary -= 1
        xTranslate += xVelocity
        yTranslate += yVelocity
        rotation += rotationVelocity
      } else {
        val newCyclesUntilStationary = Random.nextInt(30, 270)
        if (Random.nextFloat() < PERCENT_OF_STONE_MOTION) {
          val factor = currentRowNumber.toFloat() / NUM_ROWS.toFloat();
          val (newXOffset, newYOffset, newRotation) = getSchotterOffsets(factor, MARGIN_BETWEEN_STONES * 4)
          val newXPosition = currentColNumber * sideLength + MARGIN_BETWEEN_STONES * currentColNumber + newXOffset
          val newYPosition = currentRowNumber * sideLength + MARGIN_BETWEEN_STONES * currentRowNumber + newYOffset
          xVelocity = (newXPosition - xTranslate) / newCyclesUntilStationary.toFloat()
          yVelocity = (newYPosition - yTranslate) / newCyclesUntilStationary.toFloat()
          rotationVelocity = (newRotation - rotation) / newCyclesUntilStationary.toFloat()
        } else {
          xVelocity = 0f
          yVelocity = 0f
          rotationVelocity = 0f
        }
        cyclesUntilStationary = newCyclesUntilStationary
      }
    }
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P2D)
    background(66)
  }

  override fun settings() {
    size(1100, 1400, P2D)
  }
}

fun main() {
  PApplet.main("${Schotter::class.java.packageName}.${Schotter::class.java.simpleName}")
}