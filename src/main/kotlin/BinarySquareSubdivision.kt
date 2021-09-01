import processing.core.PApplet
import processing.core.PGraphics

class BinarySquareSubdivision : PApplet() {

  val maxScreenSize = 880

  lateinit var polygon: PGraphics

  override fun setup() {
    polygon = createGraphics(width, height, P2D)
    background(10)
    generatePoints(
      width / 2F,
      height / 2F
    ).also {
      polygon.beginDraw()
      drawPoints(it)
      polygon.endDraw()
    }
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
  }

  override fun draw() {
    image(polygon, 0F, 0F)
  }

  fun drawPoints(coordinates: List<Coordinate>) {

    println(coordinates.size)
    // polygon.noFill()
    coordinates.forEach {
      println("${it.x} ${it.y}")
      polygon.stroke(240)
      polygon.strokeWeight(10F)
      polygon.point(it.x, it.y)
    }
  }

  fun generatePoints(x: Float, y: Float): List<Coordinate> {
    var currentX = x
    var currentY = y
    val coordinates = mutableListOf<Coordinate>()

    while (isValidPoint(currentX, currentY)) {
      coordinates.add(Coordinate(currentX, currentY))
      currentX /= 2f
      currentY /= 2f
    }

    currentX = x
    currentY = y

    while (isValidPoint(currentX, currentY)) {
      coordinates.add(Coordinate(currentX, currentY))
      currentX += (abs(width - currentX) / 2F)
      currentY += (abs(height - currentY) / 2F)
    }

    currentX = x
    currentY = y

    while (isValidPoint(currentX, currentY)) {
      coordinates.add(Coordinate(currentX, currentY))
      currentX /= 2f
      currentY += (abs(height - currentY) / 2F)
    }

    currentX = x
    currentY = y

    while (isValidPoint(currentX, currentY)) {
      coordinates.add(Coordinate(currentX, currentY))
      currentX += (abs(width - currentX) / 2F)
      currentY /= 2f
    }

    return coordinates
  }

  private fun isValidPoint(x: Float, y: Float): Boolean {
    if (x > (width - 10) || y > (height - 10) || x < 10F || y < 10F) {
      return false
    }
    return true
  }
}

data class Coordinate(
  val x: Float,
  val y: Float
)

fun main() {
  PApplet.main("BinarySquareSubdivision")
}