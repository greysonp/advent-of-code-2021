export async function main() {
  const lines: Line[] = (await Deno.readTextFile('input.txt'))
    .split('\n')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(line => {
      const parts = line.split("->").map(v => v.trim())
      const p1 = parts[0].split(',').map(v => parseInt(v.trim()))
      const p2 = parts[1].split(',').map(v => parseInt(v.trim()))
      return {
        start: {
          x: p1[0],
          y: p1[1]
        },
        end: {
          x: p2[0],
          y: p2[1]
        }
      }
    })

  const gridSize = 1000
  const grid: number[][] = buildGrid(gridSize, gridSize)

  lines.forEach(line => {
    drawLine(line, grid)
  })

  let overlapCount = 0
  for (let x = 0; x < gridSize; x++) {
    for (let y = 0; y < gridSize; y++) {
      if (grid[x][y] > 1) {
        overlapCount++
      }
    }
  }


  console.log(overlapCount)
}

function drawLine(line: Line, grid: number[][]) {
  if (line.start.x == line.end.x) {
    for (let y = Math.min(line.start.y, line.end.y); y <= Math.max(line.start.y, line.end.y); y++) {
      grid[line.start.x][y] += 1
    }
  } else if (line.start.y == line.end.y) {
    for (let x = Math.min(line.start.x, line.end.x); x <= Math.max(line.start.x, line.end.x); x++) {
      grid[x][line.start.y] += 1
    }
  } else {
    // ignore diagonals
  }
}

function buildGrid(width: number, height: number): number[][] {
  const grid: number[][] = []

  for (let i = 0; i < width; i++) {
    const row = []
    for (let j = 0; j < height; j++) {
      row.push(0)
    }
    grid.push(row)
  }

  return grid
}

type Point = {
  x: number,
  y: number
}

type Line = {
  start: Point,
  end: Point
}

await main()