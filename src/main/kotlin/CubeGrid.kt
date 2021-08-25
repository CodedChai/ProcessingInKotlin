import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import processing.core.PApplet
import processing.core.PGraphics

class CubeGrid : PApplet() {

    val maxScreenSize = 880
    lateinit var cube: PGraphics

    val numCubesInRow = 11
    val numCubes = numCubesInRow * numCubesInRow

    val cubeSize = (maxScreenSize / 2) / numCubesInRow.toFloat()

    override fun setup() {
        cube = createGraphics(width, height, P3D)
    }

    override fun settings() {
        size(maxScreenSize, maxScreenSize, P3D)
    }

    override fun draw() {
        background(10)

        runBlocking {
            (0 until numCubes).forEach { index ->
                launch {
                    drawCube(index)
                    image(cube, 0F, 0F)
                }
            }
        }
    }

    fun drawCube(index: Int) {
        cube.beginDraw()
        cube.clear()
        // cube.lights()
        cube.stroke(230)
        cube.strokeWeight(3F)
        cube.noFill()
        val row = index / numCubesInRow
        val column = index % numCubesInRow
        val widthIncrement = width / numCubesInRow.toFloat()
        val heightIncrement = height / numCubesInRow.toFloat()
        cube.translate(column * widthIncrement + cubeSize, row * heightIncrement + cubeSize)
        val maxRotation = PI * 2
        val xRotation = (maxRotation / numCubesInRow) * (row - numCubesInRow / 2) - PI
        val yRotation = (maxRotation / numCubesInRow) * (column - numCubesInRow / 2) - PI
        cube.rotateX((frameCount / 20.0F) + xRotation)
        //cube.rotateY((frameCount / 20.0F) + yRotation)
        cube.rotateZ((frameCount / 20.0F) + yRotation)
        cube.box(cubeSize)
        cube.endDraw()
    }
}

fun main() {
    PApplet.main("CubeGrid")
}