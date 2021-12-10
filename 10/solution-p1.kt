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

  val scores: Map<Char, Int> = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
  )

  var score = 0

  File("10/input.txt")
    .readLines()
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .forEach { line ->
      val stack: Stack<Char> = Stack()

      for (i in line.indices) {
        if (symbols.containsKey(line[i])) {
          stack.push(symbols[line[i]])
        } else if (stack.pop() != line[i]) {
          score += scores[line[i]]!!
          break
        }
      }
    }

  println(score)
}