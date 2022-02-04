package com.codedchai.sketch

import com.codedchai.sketch.animation.Schotter
import processing.core.PApplet
import processing.core.PGraphics
import java.time.OffsetDateTime

abstract class BaseSketch: PApplet() {

  lateinit var pGraphics: PGraphics
  private val formattedDate = OffsetDateTime.now().toEpochSecond()
  val sketchName: String = this.javaClass.simpleName
  val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
  val animationDirectory = "C:\\Users\\Connor\\Pictures\\animations\\"

  fun saveImageOutput(shouldSaveImage: Boolean = false, shouldSaveAnimation: Boolean = false) {
    if (shouldSaveImage) {
      pGraphics.save("${imageDirectory}$sketchName-$formattedDate.png")
    }

    if (shouldSaveAnimation) {
      saveFrame("${animationDirectory}$sketchName-$formattedDate\\$sketchName-######.png")
    }
  }

  override fun setup() {
    super.setup()
    surface.setResizable(true)
    surface.setLocation(0, 0)
  }
}