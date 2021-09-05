package com.codedchai.sketch

import com.codedchai.constants.RgbColorSchemeConstants
import com.codedchai.domain.Circle
import com.codedchai.domain.Coordinate
import com.codedchai.extensions.addIfNotNull
import processing.core.PApplet
import kotlin.random.Random

class CirclePackingInArbitraryPolygon : PApplet() {

  val maxScreenSize = 1600
  val random = Random(maxScreenSize) // use resolution as the seed for some consistency

  val colorScheme = RgbColorSchemeConstants.GREEN_PASTELS

  val circleColorsSize = colorScheme.colors.size
  val circles: MutableList<Circle> = mutableListOf()

  val triangleOnLeftPolygon = listOf(Coordinate(100f, 100f), Coordinate(800f, 400f), Coordinate(200f, 1400f))
  val kShapePolygon = listOf(Coordinate(200f, 100f), Coordinate(1200f, 100f), Coordinate(400f, 700f), Coordinate(1200f, 1300f), Coordinate(200f, 1300f))
  val anotherShapePolygon =
    listOf(Coordinate(maxScreenSize * .25f, 0f), Coordinate(maxScreenSize * 1f, maxScreenSize * 0.1f), Coordinate(maxScreenSize * .9f, maxScreenSize * .9f), Coordinate(maxScreenSize * .5f, maxScreenSize * .95f))

  val polygons: List<List<Coordinate>> = listOf(triangleOnLeftPolygon, kShapePolygon, anotherShapePolygon)

  override fun setup() {
    noStroke()
    smooth()
    surface.setTitle("Circle Packing Arbitrary Shapes")
    surface.setResizable(true)
    surface.setLocation(100, 100)

    val circlesPerPolygon = 1200

    polygons.forEach { polygon ->
      val circlesInPolygons = mutableListOf<Circle>()

      while (circlesInPolygons.size <= circlesPerPolygon) {
        addCirclesRandomly(polygon, circlesInPolygons)
      }

      circles.addAll(circlesInPolygons)
    }

    noLoop()
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
  }

  override fun draw() {
    background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)

    //visualizePolygon(polygon2)

    circles.forEach {
      fill(it.color.r, it.color.g, it.color.b)
      circle(it.x, it.y, it.radius * 2f)
    }
  }

  fun polygonContainsPoint(polygonVertexCoordinates: List<Coordinate>, testX: Float, testY: Float): Boolean {
    val numVertices = polygonVertexCoordinates.size
    var containsPoint = false
    var trailingIndex = numVertices - 1

    for (leadingIndex in 0 until numVertices) {
      val deltaX = polygonVertexCoordinates[trailingIndex].x - polygonVertexCoordinates[leadingIndex].x
      val ySpread = testY - polygonVertexCoordinates[leadingIndex].y
      val deltaY = polygonVertexCoordinates[trailingIndex].y - polygonVertexCoordinates[leadingIndex].y
      if (polygonVertexCoordinates[leadingIndex].y > testY != polygonVertexCoordinates[trailingIndex].y > testY &&
        testX < deltaX * ySpread / deltaY + polygonVertexCoordinates[leadingIndex].x
      ) {
        containsPoint = !containsPoint
      }
      trailingIndex = leadingIndex
    }

    return containsPoint
  }

  fun visualizePolygon(polygon: List<Coordinate>) {
    beginShape()

    polygon.forEach { coordinate ->
      vertex(coordinate.x, coordinate.y)
    }

    endShape(CLOSE)
  }

  private fun addCirclesRandomly(polygon: List<Coordinate>, circlesInPolygon: MutableList<Circle>): List<Circle> {
    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 110f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 90f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 50f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 50f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 40f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 40f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 20f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(0f, height.toFloat()),
        random(3f, 10f),
        polygon,
        circlesInPolygon
      )
    )

    circlesInPolygon.addIfNotNull(
      addCircleIfNoCollision(
        random(0f, width.toFloat()),
        random(height.toFloat() / 2, height.toFloat()),
        random(5f, 10f),
        polygon,
        circlesInPolygon
      )
    )

    return circlesInPolygon
  }

  fun addCircleIfNoCollision(x: Float, y: Float, radius: Float, polygon: List<Coordinate>, circlesInPolygon: List<Circle>): Circle? {
    val color = colorScheme.colors[random.nextInt(circleColorsSize)]

    val circle = Circle(x, y, radius, color)
    return if (!collidesWithExistingCircles(circle, circlesInPolygon) && polygonContainsPoint(polygon, x, y)) {
      circle
    } else {
      null
    }
  }

  fun collidesWithExistingCircles(circle: Circle, circlesInPolygon: List<Circle>): Boolean {
    return circlesInPolygon.any { dist(it.x, it.y, circle.x, circle.y) < (it.radius + circle.radius) }
  }
}

fun main() {
  PApplet.main("${CirclePackingInArbitraryPolygon::class.java.packageName}.${CirclePackingInArbitraryPolygon::class.java.simpleName}")
}
