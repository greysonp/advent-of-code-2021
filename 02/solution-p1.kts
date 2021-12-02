import java.io.File

var horizontal = 0
var depth = 0

File("input.txt")
  .readLines()
  .forEach { line ->
    val parts: List<String> = line.split(" ")
    val command: String = parts[0]
    val value: Int = Integer.parseInt(parts[1])

    when (command) {
      "forward" -> horizontal += value
      "up" -> depth -= value
      "down" -> depth += value
    }
  }

println(horizontal * depth)