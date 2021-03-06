package com.codedchai.sketch.flowfield

import com.codedchai.domain.Coordinate
import com.codedchai.sketch.BaseSketch
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import processing.core.PApplet

class FuzzyFlowField : BaseSketch() {

  override fun setup() {
    super.setup()
    background(10)
    // visualizeGrid(buildNoiseGrid())
    noLoop()
  }

  fun visualizeGrid(angleGrid: Map<Coordinate, Float>) {
    angleGrid.entries.forEach { entry ->
      fill(map(entry.value, 0f, PI * 2f, 0f, 255f))
      circle(entry.key.x, entry.key.y, 5.0f)
    }
  }

  fun buildNoiseGrid(): Map<Coordinate, Float> {
    val angleGrid: MutableMap<Coordinate, Float> = mutableMapOf()

    for (x in 0..width) {
      for (y in 0..height) {
        val scaledX = x * 0.005f
        val scaledY = y * 0.005f

        val noiseValue = noise(scaledX, scaledY)

        val angle = map(noiseValue, 0.0f, 1.0f, 0.0f, PI * 2.0f)
        angleGrid[Coordinate(x, y)] = angle
      }
    }

    return angleGrid
  }

  fun makeFlowFieldLine(
    angleGrid: Map<Coordinate, Float>,
    startingCoordinate: Coordinate,
    step: Int,
    stepsToTake: Int
  ): List<Coordinate> {
    val flowLine = mutableListOf(startingCoordinate)

    var currentAngle = angleGrid[startingCoordinate]!!
    var currentCoordinate = startingCoordinate
    for (i in 0..stepsToTake) {
      val xMove = cos(currentAngle) * step
      val yMove = sin(currentAngle) * step

      currentAngle += angleGrid[currentCoordinate] ?: break
      currentCoordinate = Coordinate((currentCoordinate.x + xMove).toInt(), (currentCoordinate.y + yMove).toInt())
      flowLine.add(currentCoordinate)
    }

    return flowLine
  }

  fun drawLine(polygon: List<Coordinate>) {
    beginShape(LINE_STRIP)

    stroke(230)
    fill(230)
    tint(255f, 127f)

    polygon.forEach { coordinate ->
      vertex(coordinate.x, coordinate.y)
    }

    endShape(CLOSE)
  }

  override fun settings() {
    size(1600, 1600, P2D)
  }

  override fun draw() = runBlocking {
    background(10)
    val angleGrid = buildNoiseGrid()
    // visualizeGrid(angleGrid)

    val numLines = 4000

    repeat(numLines) {
      launch {
        val flowLine = makeFlowFieldLine(
          angleGrid,
          Coordinate(random(0f, width.toFloat()).toInt(), random(0f, height.toFloat()).toInt()),
          random(3f, 10f).toInt(),
          random(5f, 180f).toInt()
        )
        drawLine(flowLine)
      }
    }
    super.draw()
  }
}

fun main() {
  PApplet.main("${FuzzyFlowField::class.java.packageName}.${FuzzyFlowField::class.java.simpleName}")
}