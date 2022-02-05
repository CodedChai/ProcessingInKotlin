package com.codedchai.sketch.circlepacking


import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Circle
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet
import processing.core.PImage
import kotlin.random.Random

class CirclePackingFromImage : BaseSketch() {

  val colorScheme = RgbColorSchemeConstants.SOFT_AND_ROYAL

  val circleColorsSize = colorScheme.colors.size
  val circles: MutableList<Circle> = mutableListOf()
  val imageDirectory = "C:\\Users\\Connor\\Pictures\\"
  lateinit var circlePackingImage: PImage

  lateinit var random: Random

  // TODO: Find better way to load in files...use resources directory in project??
  override fun setup() {
    super.setup()
    circlePackingImage = loadImage("${imageDirectory}circle_packing_2.png", "png")
    random =
      Random(circlePackingImage.pixelWidth + circlePackingImage.pixelHeight) // use resolution as the seed for some consistency
    surface.setSize(circlePackingImage.pixelWidth, circlePackingImage.pixelHeight)
    noStroke()
    smooth()
    surface.setTitle("Random Circle Packing Pre-Computed From File")

    while (circles.size <= 7000) {
      addCirclesRandomly()
    }

    noLoop()
  }

  override fun settings() {
    size(1000, 1000, P2D)
  }

  override fun draw() {
    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    circles.forEach {
      fill(it.color.r, it.color.g, it.color.b)
      circle(it.x, it.y, it.radius * 2f)
    }

    super.draw()
  }

  private fun addCirclesRandomly() {
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 110f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 90f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 50f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 40f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 20f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(0f, height.toFloat()),
      random(3f, 10f)
    )
    addCircleIfNoCollision(
      random(0f, width.toFloat()),
      random(height.toFloat() / 2, height.toFloat()),
      random(5f, 10f)
    )
  }

  fun addCircleIfNoCollision(x: Float, y: Float, radius: Float) {
    val color = colorScheme.colors[random.nextInt(circleColorsSize)]

    val circle = Circle(x, y, radius, color)
    if (isCircleInValidImagePixel(circle) && !collidesWithExistingCircles(circle)) {
      circles.add(circle)
    }
  }

  fun isCircleInValidImagePixel(circle: Circle): Boolean {
    val pixelColor = circlePackingImage.get(circle.x.toInt(), circle.y.toInt())
    //  println(pixelColor)
    return pixelColor == -1
  }

  fun collidesWithExistingCircles(circle: Circle): Boolean {
    return circles.any { dist(it.x, it.y, circle.x, circle.y) < (it.radius + circle.radius) }
  }
}

fun main() {
  PApplet.main("${CirclePackingFromImage::class.java.packageName}.${CirclePackingFromImage::class.java.simpleName}")
}