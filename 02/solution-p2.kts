import java.io.File

var horizontal = 0
var aim = 0
var depth = 0

File("input.txt")
  .readLines()
  .forEach { line ->
    val parts: List<String> = line.split(" ")
    val command: String = parts[0]
    val value: Int = Integer.parseInt(parts[1])

    when (command) {
      "forward" -> {
        horizontal += value
        depth += aim * value
      }
      "up" -> aim -= value
      "down" -> aim += value
    }
  }

println(horizontal * depth)