package d12.p1

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

  val pathCount: Int = findPaths("start", connections, listOf()).size

  println(pathCount)
}

fun findPaths(current: String, connections: List<Connection>, path: List<String>): Set<List<String>> {
  if (current.isEnd()) {
    return setOf(path + current)
  }

  val paths: MutableSet<List<String>> = mutableSetOf()
  connections
    .filter { it.start == current }
    .filter { it.end.isLargeCave() || !path.contains(it.end) }
    .forEach { paths.addAll(findPaths(it.end, connections, path + current)) }

  connections
    .filter { it.end == current }
    .filter { it.start.isLargeCave() || !path.contains(it.start) }
    .forEach { paths.addAll(findPaths(it.start, connections, path + current)) }

  return paths
}

data class Connection (
  val start: String,
  val end: String
)

fun String.isLargeCave(): Boolean {
  return this[0].isUpperCase()
}

fun String.isEnd(): Boolean {
  return this == "end"
}
