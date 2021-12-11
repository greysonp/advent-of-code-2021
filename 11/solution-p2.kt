package d11.p2

import java.io.File

fun main() {
  val grid: MutableList<MutableList<Int>> = File("11/input.txt")
    .readLines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map { line ->
      val nums: MutableList<Int> = mutableListOf()
      for (i in line.indices) {
        nums.add(line[i].digitToInt())
      }
      nums
    }
    .toMutableList()

  var flashes = 0
  for (i in 1 .. Int.MAX_VALUE) {
    flashes += step(grid)
    if (allFlashing(grid)) {
      println("All will flash on step $i")
      break
    }
  }
}

fun step(grid: MutableList<MutableList<Int>>): Int {
  var flashes = 0

  for (x in grid.indices) {
    for (y in grid[x].indices) {
      grid[x][y] += 1
    }
  }

  for (x in grid.indices) {
    for (y in grid[x].indices) {
      if (grid[x][y] > 9) {
        flashes += flash(grid, x, y)
      }
    }
  }

  return flashes
}

fun flash(grid: MutableList<MutableList<Int>>, x: Int, y: Int): Int {
  var flashes = 1

  grid[x][y] = 0

  flashes += flashBump(grid, x - 1, y)
  flashes += flashBump(grid, x - 1, y - 1)
  flashes += flashBump(grid, x - 1, y + 1)
  flashes += flashBump(grid, x, y - 1)
  flashes += flashBump(grid, x, y + 1)
  flashes += flashBump(grid, x + 1, y)
  flashes += flashBump(grid, x + 1, y - 1)
  flashes += flashBump(grid, x + 1, y + 1)

  return flashes
}

fun flashBump(grid: MutableList<MutableList<Int>>, x: Int, y: Int): Int {
  if (x < 0 || x >= grid.size || y < 0 || y >= grid[x].size || grid[x][y] == 0) {
    return 0
  }

  grid[x][y] += 1

  if (grid[x][y] > 9) {
    return flash(grid, x, y)
  } else {
    return 0
  }
}

fun allFlashing(grid: List<List<Int>>): Boolean {
  for (x in grid.indices) {
    for (y in grid[x].indices) {
      if (grid[x][y] != 0) {
        return false
      }
    }
  }

  return true
}

fun printGrid(grid: List<List<Int>>) {
  for (x in grid.indices) {
    for (y in grid[x].indices) {
      print(grid[x][y])
    }
    println()
  }
  println()
}

