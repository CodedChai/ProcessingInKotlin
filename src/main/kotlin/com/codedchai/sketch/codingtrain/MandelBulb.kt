package com.codedchai.sketch.codingtrain

import com.codedchai.domain.Spherical
import com.codedchai.sketch.BaseSketch
import peasy.PeasyCam
import processing.core.PApplet
import processing.core.PVector

class MandelBulb : BaseSketch() {

  val VOXEL_RESOLUTION = 256
  lateinit var cam: PeasyCam

  val mandelBulbPoints = mutableListOf<PVector>()

  override fun draw() {
    background(0)
    mandelBulbPoints.forEach {
      stroke(255)
      point(it.x * 10, it.y * 10, it.z * 10)
    }
//    super.draw()

    println(frameRate)
  }

  fun buildMandleBulbPoints() {

    (0 until VOXEL_RESOLUTION).forEach { i ->
      (0 until VOXEL_RESOLUTION).forEach { j ->
        var edge = false
        (0 until VOXEL_RESOLUTION).forEach { k ->
          val x = map(i.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)
          val y = map(j.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)
          val z = map(k.toFloat(), 0f, VOXEL_RESOLUTION.toFloat(), -1f, 1f)

          val zeta = PVector(0f, 0f, 0f)
          var currentIteration = 0
          val maxIterations = 10
          val n = 16f
          while (true) {
            val sphericalZeta = spherical(zeta.x, zeta.y, zeta.z)
            val rToTheN = pow(sphericalZeta.r, n)
            val rToTheNTimesSinOfThetaTimesN = rToTheN * sin(sphericalZeta.theta * n)
            val newX = rToTheNTimesSinOfThetaTimesN * cos(sphericalZeta.phi * n)
            val newY = rToTheNTimesSinOfThetaTimesN * sin(sphericalZeta.phi * n)
            val newZ = rToTheN * cos(sphericalZeta.theta * n)

            zeta.x = newX + x
            zeta.y = newY + y
            zeta.z = newZ + z

            currentIteration++
            if (sphericalZeta.r > 16) {
              if (edge) {
                edge = false
              }
              break
            }
            if (currentIteration > maxIterations) {
              if (!edge) {
                edge = true
                mandelBulbPoints.add(PVector(x * 100, y * 100, z * 100))
              }
              break
            }
          }
        }
      }
    }

  }

  fun spherical(x: Float, y: Float, z: Float): Spherical {
    val r = sqrt(pow(x, 2f) + pow(y, 2f) + pow(z, 2f))
    val theta = atan2(sqrt(pow(x, 2f) + pow(y, 2f)), z)
    val phi = atan2(y, x)

    return Spherical(r, theta, phi)
  }

  override fun setup() {
    super.setup()
    background(0)
    pGraphics = createGraphics(width, height, P3D)

    buildMandleBulbPoints()


    cam = PeasyCam(this, 700.0)

  }

  override fun settings() {
    size(800, 800, P3D)
  }
}

fun main() {
  PApplet.main("${MandelBulb::class.java.packageName}.${MandelBulb::class.java.simpleName}")
}
