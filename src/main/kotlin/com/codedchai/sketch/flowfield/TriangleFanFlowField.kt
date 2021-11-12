package com.codedchai.sketch.flowfield

import com.codedchai.domain.Coordinate
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import processing.core.PApplet

class ThickLineFlowField : PApplet() {

  override fun setup() {
    background(10)
    surface.setResizable(true)
    surface.setLocation(0, 0)
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

  fun makeFlowFieldLine(angleGrid: Map<Coordinate, Float>, startingCoordinate: Coordinate, step: Int, stepsToTake: Int): List<Coordinate> {
    val flowLine = mutableListOf(startingCoordinate)

    var currentAngle = angleGrid[startingCoordinate]!!
    var currentCoordinate = startingCoordinate
    for (i in 0..stepsToTake) {
      val xMove = cos(currentAngle) * step
      val yMove = sin(currentAngle) * step

      currentAngle = angleGrid[currentCoordinate] ?: break
      currentCoordinate = Coordinate((currentCoordinate.x + xMove).toInt(), (currentCoordinate.y + yMove).toInt())
      flowLine.add(currentCoordinate)
    }

    return flowLine
  }

  fun drawFan(polygon: List<Coordinate>) {
    beginShape(TRIANGLE_FAN)

    stroke(111f, 123f, 229f, 100f)
    fill(197f, 231f, 208f, 90f)

    polygon.forEach { coordinate ->
      vertex(coordinate.x, coordinate.y)
    }

    endShape(CLOSE)
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }

  override fun draw() {
    // background(111f, 123f, 229f)
    //  background(197f, 231f, 208f)
    //  background(174f, 15f, 250f)
    background(180f, 172f, 180f)
    val angleGrid = buildNoiseGrid()
    // visualizeGrid(angleGrid)

    val numLines = 200
    runBlocking {
      repeat(numLines) {
        launch {
          val flowLine = makeFlowFieldLine(angleGrid, Coordinate(random(0f, width.toFloat()).toInt(), random(0f, height.toFloat()).toInt()), random(3f, 10f).toInt(), random(5f, 90f).toInt())
          drawFan(flowLine)
        }
      }
    }
  }
}

fun main() {
  PApplet.main("${ThickLineFlowField::class.java.packageName}.${ThickLineFlowField::class.java.simpleName}")
}