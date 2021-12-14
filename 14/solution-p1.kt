package d14.p1

import java.io.File

fun main() {
  val lines: List<String> = File("14/input.txt").readLines()

  val template: String = lines[0]
  val rules: Map<String, String> = lines.subList(2, lines.size)
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .map { line ->
      val parts = line.split("->").map { it.trim() }
      Pair(parts[0], parts[1])
    }
    .toMap()

  var current = template
  for (i in 1..10) {
    current = grow(current, rules)
  }

  println(score(current))
}

fun grow(template: String, rules: Map<String, String>): String {
  val output = StringBuilder()
  for (i in 0 until template.length - 1) {
    val pair = template.substring(i, i + 2)

    output.append(pair[0])

    val insertion: String? = rules[pair]
    if (insertion != null) {
      output.append(insertion)
    }
  }
  output.append(template.last())
  return output.toString()
}

fun score(template: String): Int {
  val counts: Map<Char, Int> = template.groupingBy { it }.eachCount()

  val max = counts.values.maxOf { it }
  val min = counts.values.minOf { it }

  println(counts)

  return max - min
}