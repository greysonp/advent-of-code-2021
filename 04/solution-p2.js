const fs = require('fs')

function main() {
  const lines = fs.readFileSync('input.txt').toString().split('\n')

  const numbers = lines[0]
    .split(',')
    .map(val => val.trim())
    .filter(val => val.length > 0)
    .map(val => parseInt(val))

  const chunks = []
  for (let i = 2; i < lines.length - 5; i += 6) {
    chunks.push(lines.slice(i, i + 5))
  }

  const boards = chunks.map(chunk => newBoard(chunk))

  let lastToWin = null
  let winningNumber = 0
  let winningIndexes = new Set()

  for (let i = 0; i < numbers.length; i++) {
    const number = numbers[i]
    winningNumber = number

    for (let j = 0; j < boards.length; j++) {
      const board = boards[j]
      board.markNumber(number)
      if (!winningIndexes.has(j) && board.hasWon()) {
        winningIndexes.add(j)

        if (winningIndexes.size === boards.length) {
          lastToWin = board
          break;
        }
      }
    }

    if (lastToWin) {
      break;
    }
  }

  if (lastToWin) {
    console.log(lastToWin.calculateScore(winningNumber))
  } else {
    console.log('Something went wrong :(')
  }
}

function newBoard(chunk) {
  const rows = chunk.map(line => {
    return line
      .split(' ')
      .map(val => val.trim())
      .filter(val => val.length > 0)
      .map(val => parseInt(val))
      .map(val => {
        return {
          value: val,
          marked: false
        }
      })
  })

  return {
    markNumber: function(number) {
      for (let i = 0; i < rows.length; i++) {
        for (let j = 0; j < rows[i].length; j++) {
          if (rows[i][j].value === number) {
            rows[i][j].marked = true
          }
        }
      }
    },
    hasWon: function() {
      for (let i = 0; i < rows.length; i++) {
        let allMarked = true
        for (let j = 0; j < rows[i].length; j++) {
          if (!rows[i][j].marked) {
            allMarked = false;
            break;
          }
        }

        if (allMarked) {
          return true;
        }
      }

      for (let i = 0; i < rows.length; i++) {
        let allMarked = true
        for (let j = 0; j < rows[i].length; j++) {
          if (!rows[j][i].marked) {
            allMarked = false;
            break;
          }
        }

        if (allMarked) {
          return true;
        }
      }

      return false;
    },
    calculateScore: function(winningNumber) {
      let sum = 0

      for (let i = 0; i < rows.length; i++) {
        for (let j = 0; j < rows.length; j++) {
          if (!rows[i][j].marked) {
            sum += rows[i][j].value
          }
        }
      }

      return sum * winningNumber
    }
  }
}

main()