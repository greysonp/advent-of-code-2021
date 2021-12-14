package d13.p1

import java.io.File

fun main() {
  val lines: List<String> = File("13/input.txt").readLines().map { it.trim() }

  val dataSplit = lines.indexOf("")
  val coords: Set<Point> = lines.subList(0, dataSplit)
    .map {
      val parts = it.split(",")
      Point(parts[0].toInt(), parts[1].toInt())
    }
    .toSet()

  val folds: List<Fold> = lines.subList(dataSplit + 1, lines.size)
    .filter { it.isNotEmpty() }
    .map {
      val parts = it.split("=")
      Fold(
        axis = if (parts[0].contains("x")) Axis.X else Axis.Y,
        position = parts[1].toInt()
      )
    }

  println(applyFold(coords, folds[0]).size)
}

fun applyFold(coords: Set<Point>, fold: Fold): Set<Point> {
  val output: MutableSet<Point> = mutableSetOf()

  if (fold.axis == Axis.X) {
    output.addAll(coords.filter { it.x < fold.position })

    output.addAll(
      coords
        .filter { it.x > fold.position }
        .map { it.copy(x = fold.position - (it.x - fold.position)) }
    )
  } else if (fold.axis == Axis.Y) {
    output.addAll(coords.filter { it.y < fold.position })

    output.addAll(
      coords
        .filter { it.y > fold.position }
        .map { it.copy(y = fold.position - (it.y - fold.position)) }
    )
  }

  return output
}

data class Point(val x: Int, val y: Int)

data class Fold(val axis: Axis, val position: Int)

enum class Axis {
  X, Y
}