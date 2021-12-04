async function main() {
  const lines = (await Deno.readTextFile('input.txt')).split('\n')

  const numbers = lines[0]
    .split(',')
    .map(val => val.trim())
    .filter(val => val.length > 0)
    .map(val => parseInt(val))

  const chunks: string[][] = []
  for (let i = 2; i < lines.length - 5; i += 6) {
    chunks.push(lines.slice(i, i + 5))
  }

  const boards: Board[] = chunks.map(chunk => new Board(chunk))

  let winningBoard: Board | null = null
  let winningNumber: number | null = 0

  for (let i = 0; i < numbers.length; i++) {
    const number = numbers[i]
    winningNumber = number

    for (let j = 0; j < boards.length; j++) {
      const board = boards[j]
      board.markNumber(number)
      if (board.hasWon()) {
        winningBoard = board
        break;
      }
    }

    if (winningBoard) {
      break;
    }
  }

  if (winningBoard) {
    console.log(winningBoard.calculateScore(winningNumber))
  } else {
    console.log('Something went wrong :(')
  }
}

type Item = {
  value: number,
  marked: boolean
}

class Board {

  private rows:  Item[][]

  constructor(chunk: string[]) {
    this.rows = chunk.map(line => {
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
  }

  markNumber(number: number) {
    for (let i = 0; i < this.rows.length; i++) {
      for (let j = 0; j < this.rows[i].length; j++) {
        if (this.rows[i][j].value === number) {
          this.rows[i][j].marked = true
        }
      }
    }
  }

  hasWon() {
    for (let i = 0; i < this.rows.length; i++) {
      let allMarked = true
      for (let j = 0; j < this.rows[i].length; j++) {
        allMarked = allMarked && this.rows[i][j].marked
      }

      if (allMarked) {
        return true;
      }
    }

    for (let i = 0; i < this.rows.length; i++) {
      let allMarked = true
      for (let j = 0; j < this.rows[i].length; j++) {
        allMarked = allMarked && this.rows[j][i].marked
      }

      if (allMarked) {
        return true;
      }
    }

    return false;
  }

  calculateScore(winningNumber: number) {
    let sum = 0

    for (let i = 0; i < this.rows.length; i++) {
      for (let j = 0; j < this.rows.length; j++) {
        if (!this.rows[i][j].marked) {
          sum += this.rows[i][j].value
        }
      }
    }

    return sum * winningNumber
  }
}

await main()