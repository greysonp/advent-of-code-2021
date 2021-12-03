const fs = require('fs')

const oneCounts = []
let totalLines = 0

fs.readFileSync('input.txt')
  .toString()
  .split('\n')
  .forEach(line => {
    for (let i = 0; i < line.length; i++) {
      oneCounts[i] = oneCounts[i] || 0

      if (line.charAt(i) === '1') {
        oneCounts[i]++
      }
    }

    totalLines++
  })

let gamma = ''
let epsilon = ''

for (let i = 0; i < oneCounts.length; i++) {
  if (oneCounts[i] > totalLines / 2) {
    gamma += '1'
    epsilon += '0'
  } else {
    gamma += '0'
    epsilon += '1'
  }
}

let gammaInt = parseInt(gamma, 2)
let epsilonInt = parseInt(epsilon, 2)

console.log(gammaInt * epsilonInt)

