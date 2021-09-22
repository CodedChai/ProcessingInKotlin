package com.codedchai.sketch.animation

import com.codedchai.domain.Coordinate
import processing.core.PApplet

class PerlinSineWaves : PApplet() {

  override fun setup() {
    background(10)
    surface.setResizable(true)
    surface.setLocation(0, 0)
  }

  fun sineWave(minY: Float, maxY: Float, frameCountScalarValue: Float = .01f): List<Coordinate> {
    val sineWavePoints = mutableListOf<Coordinate>()
    for (x in 0..width step 2) {
      val mappedX = map(x.toFloat(), 0f, width.toFloat(), .5f * PI, 2.5f * PI)
      val scaledFrameCount = frameCount * frameCountScalarValue
      val noiseOffset = noise(map(x.toFloat(), 0f, width.toFloat(), 0f, 10f) + scaledFrameCount, scaledFrameCount, minY - scaledFrameCount)
      val y = map(sin(mappedX), -1f, 1f, minY, maxY) + noiseOffset * 70f
      sineWavePoints.add(Coordinate(x.toFloat(), y))
    }
    return sineWavePoints
  }

  fun visualizePolygon(polygon: List<Coordinate>) {
    beginShape(LINE_STRIP)

    polygon.forEach { coordinate ->
      vertex(coordinate.x, coordinate.y)
    }

    stroke(230)
    endShape(CLOSE)
  }

  override fun settings() {
    size(1200, 1600, P2D)
  }

  fun getFrameCountScaleValue(isNegative: Boolean): Float {
    return if (isNegative) {
      -.01f
    } else {
      .01f
    }
  }

  override fun draw() {
    background(10)
    var isNegative = false
    for (startingY in 0..(height - 300) step 100) {
      visualizePolygon(sineWave(startingY.toFloat(), startingY + 200f, getFrameCountScaleValue(isNegative)))
      isNegative = !isNegative
    }
  }
}

fun main() {
  PApplet.main("${PerlinSineWaves::class.java.packageName}.${PerlinSineWaves::class.java.simpleName}")
}