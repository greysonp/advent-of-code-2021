package d14.p2

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

  val pairs: MutableMap<PositionedPair, Long> = mutableMapOf()
  for (i in 0 until template.length - 1) {
    val pair = template.substring(i, i + 2)
    val p = PositionedPair(pair, isFirst = i == 0, isLast = i == template.length - 2)
    pairs[p] = pairs.getOrDefault(p, 0) + 1
  }

  var current: Map<PositionedPair, Long> = pairs
  for (i in 1..40) {
    current = grow(current, rules)
  }

  println(score(current))
}

fun grow(pairs: Map<PositionedPair, Long>, rules: Map<String, String>): Map<PositionedPair, Long> {
  val output: MutableMap<PositionedPair, Long> = LinkedHashMap()

  pairs.forEach { (pair, count) ->
    val rule: String? = rules[pair.value]
    if (rule != null) {
      val leftJoinedPair = "${pair.value[0]}$rule"
      val rightJoinedPair = "$rule${pair.value[1]}"

      val left = PositionedPair(leftJoinedPair, isFirst = pair.isFirst)
      val right = PositionedPair(rightJoinedPair, isLast = pair.isLast)

      output[left] = output.getOrDefault(left, 0) + count
      output[right] = output.getOrDefault(right, 0) + count
    }
  }

  return output
}

fun score(pairs: Map<PositionedPair, Long>): Long {
  val counts: MutableMap<Char, Long> = mutableMapOf()

  pairs.filter { !it.key.isLast }.forEach { (pair, count) ->
    counts[pair.value[0]] = counts.getOrDefault(pair.value[0], 0) + count
  }

  val last: PositionedPair = pairs.keys.first { it.isLast }

  counts[last.value[0]] = counts.getOrDefault(last.value[0], 0) + 1
  counts[last.value[1]] = counts.getOrDefault(last.value[1], 0) + 1

  val max = counts.values.maxOf { it }
  val min = counts.values.minOf { it }

  println(counts)

  return max - min
}

data class PositionedPair(
  val value: String,
  val isFirst: Boolean = false,
  val isLast: Boolean = false
)