import processing.core.PApplet
import kotlin.random.Random

class CirclePackingRandomlyRealtime : PApplet() {

  val maxScreenSize = 1600
  val random = Random(maxScreenSize) // use resolution as the seed for some consistency

  val circleColors = listOf(
    DARK_SEA_GREEN,
    SAGE,
    DESERT_SAND,
    MILK,
    TAUPE_GRAY
  )
  val circleColorsSize = circleColors.size
  val circles: MutableList<Circle> = mutableListOf()

  override fun setup() {
    noStroke()
    smooth()
    surface.setTitle("Random Circle Packing in Realtime")
    surface.setResizable(true)
    surface.setLocation(100, 100)
  }

  override fun settings() {
    size(maxScreenSize, maxScreenSize, P2D)
  }

  override fun draw() {
    background(237f, 234f, 228f)
    addCirclesRandomly()

    circles.forEach {
      fill(it.color.r, it.color.g, it.color.b)
      circle(it.x, it.y, it.radius * 2f)
    }

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
    val color = circleColors[random.nextInt(circleColorsSize)]

    val circle = Circle(x, y, radius, color)
    if (!collidesWithExistingCircles(circle)) {
      circles.add(circle)
    }
  }

  fun collidesWithExistingCircles(circle: Circle): Boolean {
    return circles.any { dist(it.x, it.y, circle.x, circle.y) < (it.radius + circle.radius) }
  }

  companion object {
    val DARK_SEA_GREEN = CircleColor(145f, 179f, 131f)
    val SAGE = CircleColor(197f, 193f, 139f)
    val DESERT_SAND = CircleColor(221f, 211f, 172f)
    val MILK = CircleColor(255f, 253f, 246f)
    val TAUPE_GRAY = CircleColor(138f, 137f, 133f)
  }
}

fun main() {
  PApplet.main("CirclePackingRandomlyRealtime")
}

data class Circle(
  val x: Float,
  val y: Float,
  val radius: Float,
  val color: CircleColor
)

data class CircleColor(val r: Float, val g: Float, val b: Float)

