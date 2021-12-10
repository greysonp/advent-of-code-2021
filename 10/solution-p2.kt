package d10.p1

import java.io.File
import java.util.*

fun main() {
  val symbols: Map<Char, Char> = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
  )

  val pointValues: Map<Char, Int> = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
  )

  val lineScores: MutableList<Long> = mutableListOf()

  File("10/input.txt")
    .readLines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .forEach { line ->
      val stack: Stack<Char> = Stack()
      var corrupted = false

      for (i in line.indices) {
        if (symbols.containsKey(line[i])) {
          stack.push(symbols[line[i]])
        } else if (stack.pop() != line[i]) {
          corrupted = true
          break
        }
      }

      if (!corrupted) {
        var lineScore = 0L
        do {
          lineScore *= 5
          lineScore += pointValues[stack.pop()]!!
        } while (stack.size > 0)

        lineScores.add(lineScore)
      }
    }

  lineScores.sort()
  println(lineScores[lineScores.size / 2])
}