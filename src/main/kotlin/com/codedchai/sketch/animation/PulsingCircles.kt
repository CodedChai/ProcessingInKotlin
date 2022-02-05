package com.codedchai.sketch.animation

import com.codedchai.sketch.BaseSketch
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import processing.core.PApplet

class PulsingCircles : BaseSketch() {

  val maxScreenSize = 1200

  var maxDistance: Float = 40F

  override fun setup() {
    super.setup()
    noStroke()
    smooth()
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
    maxDistance = dist(0F, 0F, width.toFloat(), height.toFloat())
  }

  override fun draw() {
    background(10)

    val frameOffset = cos(frameCount.toFloat() / 200f) * 60f
    runBlocking {
      for (i in 0..width + 20 step 20) {
        for (j in 0..width + 20 step 20) {
          launch {
            val size = dist(width / 2f, height / 2f, i.toFloat(), j.toFloat()) / maxDistance * frameOffset
            ellipse(i.toFloat(), j.toFloat(), size, size)
          }
        }
      }
    }
    super.draw()
  }
}

fun main() {
  PApplet.main("${PulsingCircles::class.java.packageName}.${PulsingCircles::class.java.simpleName}")
}