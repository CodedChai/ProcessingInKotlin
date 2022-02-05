package com.codedchai.sketch.cityscape

import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

class MirroredCityscape : BaseSketch() {

  fun drawBoxMirrored(x: Float, z: Float) {
    val height = random(250f, 415f)
    val fill = random(190f, 225f)

    pGraphics.pushMatrix()
    pGraphics.translate(x, width - height / 2, z)
    pGraphics.fill(fill)
    pGraphics.box(150f, height, 100f)
    pGraphics.popMatrix()

    pGraphics.pushMatrix()
    pGraphics.translate(x, height / 2, z)
    pGraphics.fill(fill)
    pGraphics.box(150f, height, 100f)
    pGraphics.popMatrix()
  }

  override fun draw() {
    pGraphics.lights()
    pGraphics.beginDraw()

    val xStep = 232f
    val zStep = 150f

    (-12..17).forEach { x ->
      (-31..5).forEach { z ->
        drawBoxMirrored(x * xStep, z * zStep)
      }
    }

    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)
    super.draw()
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P3D)

    background(0)

    noLoop()
  }

  override fun settings() {
    size(1400, 1400, P3D)
  }
}

fun main() {
  PApplet.main("${MirroredCityscape::class.java.packageName}.${MirroredCityscape::class.java.simpleName}")
}