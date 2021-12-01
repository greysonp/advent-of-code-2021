const fs = require('fs')

const values = fs.readFileSync('input.txt')
  .toString()
  .split('\n')
  .map(line => parseInt(line))

let count = 0

for (let i = 1; i < values.length; i++) {
  if (values[i] > values[i - 1]) {
    count++
  }
}

console.log(count)