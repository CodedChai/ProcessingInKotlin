package com.codedchai.sketch.cityscape

import com.codedchai.constants.RgbColorSchemeConstants
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

class MirroredCityscape : PApplet() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL
  lateinit var pGraphics: PGraphics

  val shouldSaveImage = true
  var shouldSaveAnimation = false
  val formattedDate = OffsetDateTime.now().toEpochSecond()

  fun drawBox(x: Float, z: Float) {
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
        drawBox(x * xStep, z * zStep)
      }
    }

    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      save("${imageDirectory}${MirroredCityscape::class.java.simpleName}-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\$formattedDate-${MirroredCityscape::class.java.simpleName}\\"
      saveFrame("${animationDirectory}${MirroredCityscape::class.java.simpleName}-######.png");
    }
  }

  override fun setup() {
    pGraphics = createGraphics(width, height, P3D)

    background(0)

    surface.setResizable(true)
    surface.setLocation(0, 0)
    noLoop()
  }

  override fun settings() {
    size(1400, 1400, P3D)
  }
}

fun main() {
  PApplet.main("${MirroredCityscape::class.java.packageName}.${MirroredCityscape::class.java.simpleName}")
}