package com.codedchai.sketch

import processing.core.PApplet
import processing.core.PGraphics
import java.io.File
import java.time.OffsetDateTime
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

abstract class BaseSketch : PApplet() {

  lateinit var pGraphics: PGraphics
  val sketchName: String = this.javaClass.simpleName

  private val formattedDate = OffsetDateTime.now().toEpochSecond()
  private val homeDirectory = System.getProperty("user.home")
  private val separator = File.separator
  private val sketchDirName = "sketches"
  private val baseSketchDirectory = "${homeDirectory}${separator}${sketchDirName}${separator}${sketchName}${separator}"
  private val imageDirectory = "${baseSketchDirectory}images${separator}"
  private val animationDirectory = "${baseSketchDirectory}animations${separator}${formattedDate}${separator}"

  var shouldSaveImage = false
  var shouldSaveAnimation = false

  override fun draw() {
    if (shouldSaveImage) {
      if (Path(imageDirectory).notExists()) {
        Path(imageDirectory).createDirectories()
      }

      val filename = "${imageDirectory}$sketchName-$formattedDate.png"
      println("Saving $filename")
      save(filename)
      shouldSaveImage = false
    }

    if (shouldSaveAnimation) {
      if (Path(animationDirectory).notExists()) {
        Path(animationDirectory).createDirectories()
      }

      saveFrame("${animationDirectory}$sketchName-$formattedDate\\$sketchName-######.png")
    }
  }

  override fun keyPressed() {
    shouldSaveImage = true

    shouldSaveAnimation = !shouldSaveAnimation
    println("saving animation: $shouldSaveAnimation to $animationDirectory")
  }

  override fun setup() {
    super.setup()
    surface.setResizable(true)
    surface.setLocation(0, 0)
  }
}