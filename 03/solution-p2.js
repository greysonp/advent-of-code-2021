const fs = require('fs')

function main() {
  let lines = fs.readFileSync('input.txt')
    .toString()
    .split('\n')

  const lineLength = lines[0].length

  let oxygenLines = [...lines]
  let co2Lines= [...lines]

  for (let i = 0; i < lineLength; i++) {
    if (oxygenLines.length > 1) {
      let commonBit = mostCommonBit(oxygenLines, i)
      oxygenLines = oxygenLines.filter(line => line.charAt(i) === commonBit)
    }

    if (co2Lines.length > 1) {
      let uncommonBit = mostCommonBit(co2Lines, i) === '1' ? '0' : '1'
      co2Lines = co2Lines.filter(line => line.charAt(i) === uncommonBit)
    }
  }

  let oxygenInt = parseInt(oxygenLines[0], 2)
  let co2Int = parseInt(co2Lines[0], 2)

  console.log(oxygenInt * co2Int)
}

function mostCommonBit(numbers, position) {
  let count = 0

  numbers.forEach(line => {
    if (line.charAt(position) === '1') {
      count++
    }
  })

  return count >= numbers.length / 2 ? '1' : '0'
}

main()