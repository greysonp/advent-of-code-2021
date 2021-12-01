import java.io.File

val values: List<Int> = File("input.txt")
  .readLines()
  .map { Integer.parseInt(it) }

var count = 0

for (i in 3 until values.size) {
  val current = values[i] + values[i - 1] + values[i - 2]
  val previous = values[i - 1] + values[i - 2] + values[i - 3]

  if (current > previous) {
    count++
  }
}

println(count)