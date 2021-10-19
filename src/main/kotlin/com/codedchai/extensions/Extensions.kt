package com.codedchai.extensions

import com.codedchai.domain.Circle
import com.codedchai.domain.RgbColorScheme
import com.codedchai.domain.Triangle
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