package d15.p1

import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
  val grid: List<List<Int>> = File("15/input.txt")
    .readLines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map { line ->
      line.map { it.toString().toInt() }
    }

  println(search(grid, P(0, 0), P(grid.size - 1, grid[grid.size - 1].size - 1)))
}

/** A* */
fun search(graph: List<List<Int>>, start: P, goal: P): Int {
  val width = graph.size
  val height = graph[width - 1].size

  val frontier: Queue<Node> = PriorityQueue(compareBy { it.priotity })
  frontier.add(Node(start, 0))

  val cameFrom: MutableMap<P, P?> = mutableMapOf()
  val costSoFar: MutableMap<P, Int> = mutableMapOf()

  cameFrom[start] = null
  costSoFar[start] = 0

  while (frontier.isNotEmpty()) {
    val current: Node = frontier.poll()!!

    if (current.p == goal) {
      break
    }

    val neighbors: MutableList<P> = mutableListOf()
    if (current.p.x > 0) {
      neighbors.add(P(current.p.x - 1, current.p.y))
    }
    if (current.p.x < width - 1) {
      neighbors.add(P(current.p.x + 1, current.p.y))
    }
    if (current.p.y > 0) {
      neighbors.add(P(current.p.x, current.p.y - 1))
    }
    if (current.p.y < height - 1) {
      neighbors.add(P(current.p.x, current.p.y + 1))
    }

    for (next in neighbors) {
      val newCost = costSoFar[current.p]!! + graph[next.x][next.y]

      if (!costSoFar.containsKey(next) || newCost  < costSoFar[next]!!) {
        costSoFar[next] = newCost
        val priority = newCost + h(next, goal)
        frontier.add(Node(next, priority))
        cameFrom[next] = current.p
      }
    }
  }

  return costSoFar[goal]!!
}

fun h(a: P, b: P): Int {
  // "Manhattan Distance"
  return abs(a.x - b.x) + abs(a.y - b.y)
}

data class Node(val p: P, val priotity: Int)

data class P(val x: Int, val y:Int)