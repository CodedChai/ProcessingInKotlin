package com.codedchai.extensions

import com.codedchai.domain.*
import com.codedchai.domain.schotter.SchotterStone
import processing.core.PApplet
import processing.core.PGraphics

fun <T> MutableCollection<T>.addIfNotNull(value: T?) {
  value?.let { this.add(value) }
}

fun PGraphics.drawCircle(circle: Circle) {
  this.fill(circle.color.r, circle.color.g, circle.color.b)
  this.stroke(circle.color.r, circle.color.g, circle.color.b)
  this.circle(circle.x, circle.y, circle.radius * 2f)
}

fun PGraphics.backgroundColor(colorScheme: RgbColorScheme) {
  this.background(colorScheme.backgroundColor!!.r, colorScheme.backgroundColor.g, colorScheme.backgroundColor.b)
}

fun PGraphics.drawTriangle(triangle: Triangle, xTranslate: Float = 0f, yTranslate: Float = 0f) {
  this.pushMatrix()
  this.translate(xTranslate, yTranslate)
  this.rotate(PApplet.radians(triangle.rotation))
  this.fill(triangle.color.r, triangle.color.g, triangle.color.b)
  this.stroke(triangle.color.r, triangle.color.g, triangle.color.b)
  val pointA = triangle.pointA()
  val pointB = triangle.pointB()
  val pointC = triangle.pointC()
  this.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y)
  this.popMatrix()
}

fun PGraphics.drawSquare(square: Square, xTranslate: Float = 0f, yTranslate: Float = 0f) {
  this.pushMatrix()
  this.translate(square.xTranslate + xTranslate, square.yTranslate + yTranslate)
  this.rotate(PApplet.radians(square.rotation))
  this.fill(square.color.r, square.color.g, square.color.b)
  this.stroke(square.color.r, square.color.g, square.color.b)

  this.square(square.x, square.y, square.sideLength)
  this.popMatrix()
}

fun PGraphics.drawSchotterStone(schotterStone: SchotterStone, xTranslate: Float = 0f, yTranslate: Float = 0f) {
  this.pushMatrix()
  this.translate(schotterStone.xTranslate + xTranslate, schotterStone.yTranslate + yTranslate)
  this.rotate(PApplet.radians(schotterStone.rotation))
  this.fill(schotterStone.color.r, schotterStone.color.g, schotterStone.color.b)
  this.stroke(schotterStone.color.r, schotterStone.color.g, schotterStone.color.b)

  this.square(schotterStone.x, schotterStone.y, schotterStone.sideLength)
  this.popMatrix()
}