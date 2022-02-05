package com.codedchai.sketch.flowfield

import com.codedchai.domain.Coordinate
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet
import java.util.*

// TODO: Fix, one of the updates broke it
class EvenlySpacedFlowField : BaseSketch() {

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

    for (x in 0..maxX) {
      for (y in 0..maxY) {
        val scaledX = x * 0.005f
        val scaledY = y * 0.005f

        val noiseValue = noise(scaledX, scaledY)

        val angle = map(noiseValue, 0.0f, 1.0f, 0.0f, PI * 2.0f)
        angleGrid[Coordinate(x, y)] = angle
      }
    }

    return angleGrid
  }

  fun generateNextCoordinate(angleGrid: Map<Coordinate, Float>, currentCoordinate: Coordinate, step: Int): Coordinate? {
    val currentAngle = angleGrid[currentCoordinate] ?: return null
    val xMove = (cos(currentAngle) * step).toInt()
    val yMove = (sin(currentAngle) * step).toInt()

    return Coordinate((currentCoordinate.x + xMove).toInt(), (currentCoordinate.y + yMove).toInt())
  }

  fun drawLine(polygon: List<Coordinate>) {
    beginShape(LINE_STRIP)

    stroke(230)
    fill(230)

    polygon.forEach { coordinate ->
      vertex(
        map(coordinate.x, 0f, maxX.toFloat(), 0f, width.toFloat()),
        map(coordinate.y, 0f, maxY.toFloat(), 0f, height.toFloat())
      )
    }

    endShape(CLOSE)
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }

  val coordinateToStreamlineId = mutableMapOf<Coordinate, Int>()

  val separationSize = 3

  fun getNewStartingCoordinate(angleGrid: Map<Coordinate, Float>): Coordinate? {
    angleGrid.keys.forEach { currentCoordinate ->
      val testCoordinate = Coordinate(currentCoordinate.x + separationSize, currentCoordinate.y)
      if (isValidPoint(testCoordinate)) {
        return testCoordinate
      }
    }
    return null
  }

  var currentId = 0

  fun generateStreamline(angleGrid: Map<Coordinate, Float>, startingCoordinate: Coordinate) {
    val possibleCoordinates = LinkedList<Coordinate>()
    possibleCoordinates.push(startingCoordinate)
    while (possibleCoordinates.isNotEmpty()) {
      val testCoordinate = possibleCoordinates.pop()

      if (isValidPoint(testCoordinate)) {
        coordinateToStreamlineId[testCoordinate] = currentId
      }

      generateNextCoordinate(angleGrid, testCoordinate, 6)
        ?.let { possibleCoordinates.push(it) }
    }
    currentId++
  }

  val maxX = 300
  val maxY = 300

  fun isValidPoint(coordinate: Coordinate): Boolean {
    return maxX > coordinate.x && maxY > coordinate.y && 0 < coordinate.x && 0 < coordinate.y && coordinateToStreamlineId[coordinate] == null
  }

  override fun draw() {
    background(10)
    val angleGrid = buildNoiseGrid()
    // visualizeGrid(angleGrid)


    generateStreamline(angleGrid, Coordinate(50, 150))

    var currentStartingCoordinate = getNewStartingCoordinate(angleGrid)
    while (currentStartingCoordinate != null) {
      generateStreamline(angleGrid, currentStartingCoordinate)
      currentStartingCoordinate = getNewStartingCoordinate(angleGrid)
    }

    drawLine(coordinateToStreamlineId.keys.toList())
    super.draw()
  }
}

fun main() {
  PApplet.main("${EvenlySpacedFlowField::class.java.packageName}.${EvenlySpacedFlowField::class.java.simpleName}")
}