package com.codedchai.sketch.codingtrain

import com.codedchai.sketch.BaseSketch
import peasy.PeasyCam
import processing.core.PApplet
import processing.core.PVector

class MandelBulb : BaseSketch() {

  val VOXEL_RESOLUTION = 32
  lateinit var cam: PeasyCam

  override fun draw() {
    background(0)

    (0 until VOXEL_RESOLUTION).forEach { i ->
      (0 until VOXEL_RESOLUTION).forEach { j ->
        (0 until VOXEL_RESOLUTION).forEach { k ->
          val x = map(i.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)
          val y = map(j.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)
          val z = map(k.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)

          val r = sqrt(pow(x, 2f) + pow(y, 2f) + pow(z, 2f))
          val theta = atan2(sqrt(pow(x, 2f) + pow(y, 2f)), z)
          val phi = atan2(y, x)

          val zeta = PVector(0f, 0f, 0f)
          val maxIterations = 10

          while (true) {
            val n = 8f
            val newX = pow(r, n) * sin(theta * n) * cos(phi * n)
            val newY = pow(r, n) * sin(theta * n) * sin(phi * n)
            val newZ = pow(r, n) * cos(theta * n)

          }

          stroke(255)
          point(x, y, z)
        }
      }
    }

    super.draw()
  }

  override fun setup() {
    super.setup()
    background(0)

    cam = PeasyCam(this, 1500.0)
  }

  override fun settings() {
    size(1000, 1000, P3D)
  }
}

fun main() {
  PApplet.main("${MandelBulb::class.java.packageName}.${MandelBulb::class.java.simpleName}")
}
