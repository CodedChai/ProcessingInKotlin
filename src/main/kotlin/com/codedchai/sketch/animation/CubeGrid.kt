package com.codedchai.sketch.animation

import com.codedchai.sketch.BaseSketch
import processing.core.PApplet

class CubeGrid : BaseSketch() {

  val maxScreenSize = 880

  val numCubesInRow = 11
  val numCubes = numCubesInRow * numCubesInRow

  val cubeSize = (maxScreenSize / 2) / numCubesInRow.toFloat()

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P3D)
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P3D)
    smooth(8)
  }

  override fun draw() {
    background(10)

    (0 until numCubes).forEach { index ->
      drawCube(index)
      image(pGraphics, 0F, 0F)
    }

//    if (frameCount == 720) {
//      exit()
//    }
  }

  fun drawCube(index: Int) {
    pGraphics.beginDraw()
    pGraphics.clear()
    // cube.lights()
    pGraphics.stroke(230)
    pGraphics.strokeWeight(3F)
    pGraphics.noFill()
    val row = index / numCubesInRow
    val column = index % numCubesInRow
    val widthIncrement = width / numCubesInRow.toFloat()
    val heightIncrement = height / numCubesInRow.toFloat()
    pGraphics.translate(column * widthIncrement + cubeSize, row * heightIncrement + cubeSize)
    val maxRotation = PI * 2
    val xRotation = (maxRotation / numCubesInRow) * (row - numCubesInRow / 2) - PI
    val yRotation = (maxRotation / numCubesInRow) * (column - numCubesInRow / 2) - PI
    pGraphics.rotateX(radians(frameCount / 2.0F) + xRotation)
    //cube.rotateY((frameCount / 20.0F) + yRotation)
    pGraphics.rotateZ(radians(frameCount / 2.0F) + yRotation)
    pGraphics.box(cubeSize)
    pGraphics.endDraw()
  }
}

fun main() {
  PApplet.main("${CubeGrid::class.java.packageName}.${CubeGrid::class.java.simpleName}")
}