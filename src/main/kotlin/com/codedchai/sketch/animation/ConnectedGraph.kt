package com.codedchai.sketch.animation

import com.codedchai.constants.RgbColorConstants
import com.codedchai.domain.Circle
import com.codedchai.extensions.drawCircle
import com.codedchai.sketch.BaseSketch
import processing.core.PApplet
import processing.core.PGraphics

class ConnectedGraph : BaseSketch() {

  override fun draw() {
    background(66)
    val circle1 = Circle(width / 2f, height / 2f, 10f, RgbColorConstants.ORANGE_RED)
    val node1 = Node(circle1)
    val circle2 = Circle(width / 4f, height / 2f, 10f, RgbColorConstants.ORANGE_RED)
    val node2 = Node(circle2)

    node1.connectNode(node2)

    with(pGraphics) {
      beginDraw()
      drawNodes(listOf(node1, node2), this)
      endDraw()
    }
    image(pGraphics, 0f, 0f)

    super.draw()
  }

  fun generateNodes(): List<Node> {
    repeat(8) { x ->
      repeat(8) { y ->

      }
    }

    return emptyList()
  }

  fun drawNodes(nodes: List<Node>, pGraphics: PGraphics) {
    nodes.forEach { node ->
      pGraphics.drawCircle(node.circle)
      node.connectedNodes.forEach { connectedNode ->
        pGraphics.line(node.circle.x, node.circle.y, connectedNode.circle.x, connectedNode.circle.y)
      }
    }
  }

  override fun setup() {
    super.setup()
    pGraphics = createGraphics(width, height, P2D)
    background(66)
  }

  override fun settings() {
    size(1100, 1400, P2D)
  }
}

fun main() {
  PApplet.main("${ConnectedGraph::class.java.packageName}.${ConnectedGraph::class.java.simpleName}")
}

data class Node(
  val circle: Circle,
  val connectedNodes: MutableList<Node> = mutableListOf()
) {
  fun connectNode(otherNode: Node) {
    connectedNodes.add(otherNode)
    otherNode.connectedNodes.add(this)
  }
}