import processing.core.PApplet

class Untitled : PApplet() {

    val maxScreenSize = 1200


    override fun setup() {
        noStroke()
    }

    override fun settings() {
        size(maxScreenSize, maxScreenSize, P2D)
    }

    override fun draw() {
        background(10)

        
    }
}

fun main() {
    PApplet.main("Untitled")
}