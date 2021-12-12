package d12.p2

import java.io.File

fun main() {
  val connections: List<Connection> = File("12/input.txt")
    .readLines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map {
      val split = it.split('-')
      Connection(split[0], split[1])
    }

  val paths: Set<List<String>> = findPaths("start", connections, listOf())

  println(paths.size)
}

fun findPaths(current: String, connections: List<Connection>, path: List<String>): Set<List<String>> {
  if (current.isEnd()) {
    return setOf(path + current)
  }

  val updatedPath: List<String> = path + current

  val paths: MutableSet<List<String>> = mutableSetOf()
  connections
    .filter { it.start == current }
    .filter { isValidEndPoint(updatedPath, it.end) }
    .forEach { paths.addAll(findPaths(it.end, connections, updatedPath)) }

  connections
    .filter { it.end == current }
    .filter { isValidEndPoint(updatedPath, it.start) }
    .forEach { paths.addAll(findPaths(it.start, connections, updatedPath)) }

  return paths
}

fun isValidEndPoint(path: List<String>, candidate: String): Boolean {
  if (candidate.isLargeCave() || candidate.isEnd()) {
    return true
  }

  if (candidate.isStart()) {
    return false
  }

  val smallCaves = path.filter { it.isSmallCave() }
  return !smallCaves.contains(candidate) || smallCaves.toSet().size == smallCaves.size
}

data class Connection (
  val start: String,
  val end: String
)

fun String.isSmallCave(): Boolean {
  return this[0].isLowerCase() && !this.isStart() && !this.isEnd()
}

fun String.isLargeCave(): Boolean {
  return this[0].isUpperCase()
}

fun String.isStart(): Boolean {
  return this == "start"
}

fun String.isEnd(): Boolean {
  return this == "end"
}