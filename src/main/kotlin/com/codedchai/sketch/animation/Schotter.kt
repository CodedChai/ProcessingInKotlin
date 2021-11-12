package com.codedchai.sketch.animation

import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime


class Schotter : PApplet() {

  lateinit var pGraphics: PGraphics

  val shouldSaveImage = false
  var shouldSaveAnimation = false
  val formattedDate = OffsetDateTime.now().toEpochSecond()

  override fun draw() {
    pGraphics.beginDraw()
    


    pGraphics.endDraw()

    image(pGraphics, 0f, 0f)

    if (shouldSaveImage) {
      val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
      pGraphics.save("${imageDirectory}${Schotter::class.java.simpleName}-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\$formattedDate-${Schotter::class.java.simpleName}\\"
      saveFrame("${animationDirectory}${Schotter::class.java.simpleName}-######.png");
    }
  }

  override fun setup() {
    pGraphics = createGraphics(width, height, P2D)

    background(0)

    surface.setResizable(true)
    surface.setLocation(0, 0)
    noLoop()
  }

  override fun settings() {
    size(1400, 1800, P2D)
  }
}

fun main() {
  PApplet.main("${Schotter::class.java.packageName}.${Schotter::class.java.simpleName}")
}