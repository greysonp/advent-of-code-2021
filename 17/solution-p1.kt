package d17.p1

import java.io.File

fun main() {
  val parts: List<String> = File("17/input.txt")
    .readLines()
    .first()
    .substring("target area: ".length)
    .split(",")
    .map { it.trim() }

  val xRangeParts = parts[0].substring("x=".length).split("..").map { it.toInt() }
  val yRangeParts = parts[1].substring("y=".length).split("..").map { it.toInt() }

  val xRange = IntRange(xRangeParts[0], xRangeParts[1])
  val yRange = IntRange(yRangeParts[0], yRangeParts[1])

  val paths: MutableList<PathData> = mutableListOf()

  for (x in 1..1000) {
    for (y in 1..1000) {
      paths.add(path(x, y, xRange, yRange))
    }
  }

  val maxY = paths
    .filter { it.hitTarget }
    .filter { it.path.isNotEmpty() }
    .map { data -> data.path.maxOf { p -> p.y } }
    .maxOf { it }

  println(maxY)
}

fun path(startXv: Int, startYv: Int, targetX: IntRange, targetY: IntRange): PathData {
  var current: P = P(0, 0)
  var xv: Int = startXv
  var yv: Int = startYv

  val path: MutableList<P> = mutableListOf()
  path.add(current)

  while (targetPossible(current, xv, yv, targetX, targetY)) {
    current = P(current.x + xv, current.y + yv)
    xv = if (xv < 0) xv + 1 else if (xv > 0) xv - 1 else xv
    yv -= 1

    path.add(current)

    if (current.x >= targetX.first && current.x <= targetX.last && current.y >= targetY.first && current.y <= targetY.last) {
      return PathData(path, true)
    }
  }

  return PathData(path, false)
}

fun targetPossible(location: P, xv: Int, yv: Int, targetX: IntRange, targetY: IntRange): Boolean {
  if (yv < 0 && location.y < targetY.first) {
    return false
  }

  if (xv > 0 && location.x > targetX.last) {
    return false
  }

  if (xv < 0 && location.x < targetX.first) {
    return false
  }

  return true
}

data class P(val x: Int, val y: Int)

data class PathData(val path: List<P>, val hitTarget: Boolean)