package com.codedchai.extensions

fun <T> MutableCollection<T>.addIfNotNull(value: T?) {
  value?.let { this.add(value) }
}
