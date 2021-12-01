import java.io.File

val values: List<Int> = File("input.txt")
  .readLines()
  .map { Integer.parseInt(it) }

var count = 0

for (i in 1 until values.size) {
  if (values[i] > values[i - 1]) {
    count++
  }
}

println(count)