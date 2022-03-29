package com.codedchai.sketch.codingtrain

import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

class PrimeNumberSpiral : BaseSketch() {

  var x: Float = 0f
  var y: Float = 0f
  var currrentStep = 1
  val stepSize = 2
  var numberOfStepsUntilTurn = 1
  var state = 0
  var turnCounter = 1
  var previousX = 0f
  var previousY = 0f

  var totalSteps = 0

  override fun draw() {
    drawAllPrimesThatFitOnScreen()
    shouldSaveImage = true

    super.draw()
    noLoop()
  }

  private fun drawAllPrimesThatFitOnScreen() {
    while (currrentStep <= totalSteps) {
      fill(127)
      stroke(255)
      val isPrime = isPrime(currrentStep)

      if (isPrime) {
        circle(x, y, stepSize.toFloat() * .5f)
      }
      previousX = x
      previousY = y

      when (state) {
        0 -> x += stepSize
        1 -> y -= stepSize
        2 -> x -= stepSize
        3 -> y += stepSize
      }

      if (currrentStep % numberOfStepsUntilTurn == 0) {
        state = (state + 1) % 4
        turnCounter++
        if (turnCounter % 2 == 0) {
          numberOfStepsUntilTurn++
        }
      }

      currrentStep++
    }
  }

  fun isPrime(value: Int): Boolean {
    if (value == 1) return false
    return (2 until sqrt(value.toFloat()).toInt()).none {
      value % it == 0
    }
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P2D)
    background(66)

    x = width / 2f
    y = height / 2f
    previousX = x
    previousY = y

    val cols = width / stepSize
    val rows = height / stepSize
    totalSteps = cols * rows
  }

  override fun settings() {
    size(1600, 1600, P2D)
  }
}

fun main() {
  PApplet.main("${PrimeNumberSpiral::class.java.packageName}.${PrimeNumberSpiral::class.java.simpleName}")
}
