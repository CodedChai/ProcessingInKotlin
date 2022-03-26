package com.codedchai.sketch.animation

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.schotter.SchotterOffsets
import com.codedchai.domain.schotter.SchotterStone
import com.codedchai.extensions.drawSchotterStone
import com.codedchai.sketch.BaseSketch
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import processing.core.PApplet
import kotlin.random.Random

class Schotter : BaseSketch() {

  private val MARGIN_FROM_SIDE = 110f
  private val MARGIN_FROM_TOP = 100f
  private val PERCENT_OF_STONE_MOTION = .4f
  private val MARGIN_BETWEEN_STONES = 20f
  private val HALF_MARGIN_BETWEEN_STONES = MARGIN_BETWEEN_STONES / 2f
  private val NUM_ROWS = 12

  private val schotterGrid: List<SchotterStone> = generateSchotterGrid(8, NUM_ROWS, 100f, MARGIN_BETWEEN_STONES)

  override fun draw() {
    pGraphics.beginDraw()
    background(66)
    pGraphics.translate(MARGIN_FROM_SIDE, MARGIN_FROM_TOP)
    schotterGrid.forEach { pGraphics.drawSchotterStone(it) }
    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    super.draw()

    updateSchotterGrid()
  }

  fun generateSchotterGrid(numColumns: Int, numRows: Int, sideLength: Float, margin: Float): List<SchotterStone> {
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
    }
  }

  fun getSchotterOffsets(factor: Float, halfMargin: Float): SchotterOffsets {
    return SchotterOffsets(
      xOffset = factor * random(-halfMargin, halfMargin),
      yOffset = factor * random(-halfMargin, halfMargin),
      rotation = factor * random(-55f, 55f)
    )
  }

  fun updateSchotterGrid() = runBlocking {
    schotterGrid.map { stone -> launch { updateSchotterStone(stone) } }.joinAll()
  }

  fun updateSchotterStone(stone: SchotterStone) {
    if (stone.cyclesUntilStationary > 0) {
      stone.cyclesUntilStationary -= 1
      stone.xTranslate += stone.xVelocity
      stone.yTranslate += stone.yVelocity
      stone.rotation += stone.rotationVelocity
    } else {
      val newCyclesUntilStationary = Random.nextInt(40, 230)
      if (Random.nextFloat() < PERCENT_OF_STONE_MOTION) {
        val factor = stone.currentRowNumber.toFloat() / NUM_ROWS.toFloat();
        val (newXOffset, newYOffset, newRotation) = getSchotterOffsets(factor, MARGIN_BETWEEN_STONES)
        val newXPosition =
          stone.currentColNumber * stone.sideLength + MARGIN_BETWEEN_STONES * stone.currentColNumber + newXOffset
        val newYPosition =
          stone.currentRowNumber * stone.sideLength + MARGIN_BETWEEN_STONES * stone.currentRowNumber + newYOffset
        stone.xVelocity = (newXPosition - stone.xTranslate) / newCyclesUntilStationary.toFloat()
        stone.yVelocity = (newYPosition - stone.yTranslate) / newCyclesUntilStationary.toFloat()
        stone.rotationVelocity = (newRotation - stone.rotation) / newCyclesUntilStationary.toFloat()
      } else {
        stone.xVelocity = 0f
        stone.yVelocity = 0f
        stone.rotationVelocity = 0f
      }
      stone.cyclesUntilStationary = newCyclesUntilStationary
    }
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P2D)
    shouldSaveAnimation = true
    background(66)
  }

  override fun settings() {
    size(1200, 1600, P2D)
  }
}

fun main() {
  PApplet.main("${Schotter::class.java.packageName}.${Schotter::class.java.simpleName}")
}