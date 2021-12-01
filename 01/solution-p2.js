const fs = require('fs')

const values = fs.readFileSync('input.txt')
  .toString()
  .split('\n')
  .map(line => parseInt(line))

let count = 0

for (let i = 3; i < values.length; i++) {
  let current = values[i] + values[i - 1] + values[i - 2]
  let previous = values[i - 1] + values[i - 2] + values[i - 3]

  if (current > previous) {
    count++
  }
}

console.log(count)