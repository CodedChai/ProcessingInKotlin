package com.codedchai.sketch

import processing.core.PApplet

class PulsingCircles : PApplet() {

  val maxScreenSize = 1200

  var maxDistance: Float = 40F

  override fun setup() {
    noStroke()
    smooth()
    surface.setResizable(true)
    surface.setLocation(100, 100)
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
    maxDistance = dist(0F, 0F, width.toFloat(), height.toFloat())
  }

  override fun draw() {
    background(10)

    val frameOffset = cos(frameCount.toFloat() / 200f) * 60f

    for (i in 0..width + 20 step 20) {
      for (j in 0..width + 20 step 20) {
        val size = dist(width / 2f, height / 2f, i.toFloat(), j.toFloat()) / maxDistance * frameOffset
        ellipse(i.toFloat(), j.toFloat(), size, size)
      }
    }
  }
}

fun main() {
  PApplet.main("com.codedchai.sketch.PulsingCircles")
}