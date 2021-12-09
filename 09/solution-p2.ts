export function main() {
  const grid: number[][] = Deno.readTextFileSync('input.txt')
    .split('\n')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(line => {
      const nums: number[] = []
      for (let i = 0; i < line.length; i++) {
        nums.push(parseInt(line.charAt(i)))
      }
      return nums
    })

  const basins: Set<number>[] = []
  for (let x = 0; x < grid.length; x++) {
    for (let y = 0; y < grid[x].length; y++) {
      basins.push(visit(grid, x, y, new Set()))
    }
  }

  for (let i = 0; i < basins.length; i++) {
    const b1 = basins[i]

    for (let j = basins.length - 1; j > i; j--) {
      const b2 = basins[j]
      if (b1.size === b2.size && intersection(b1, b2).size === b1.size) {
        basins.splice(j, 1)
      }
    }
  }

  const sortedSize: number[] = basins.map(b => b.size)
    .sort((lhs, rhs) => rhs - lhs)

  console.log(sortedSize[0] * sortedSize[1] * sortedSize[2])
}

function visit(grid: number[][], x: number, y: number, visited: Set<number>): Set<number> {
  if (visited.has(hash(grid.length, x, y))) {
    return visited
  }

  if (x < 0 || x > grid.length - 1 || y < 0 || y > grid[x].length - 1) {
    return visited
  }

  if (grid[x][y] === 9) {
    return visited
  }

  visited.add(hash(grid.length, x, y))

  visit(grid, x - 1, y, visited)
  visit(grid, x + 1, y, visited)
  visit(grid, x, y - 1, visited)
  visit(grid, x, y + 1, visited)

  return visited
}

function hash(width: number, x: number, y: number): number {
  return width * y + x
}

function intersection(setA: Set<number>, setB: Set<number>): Set<number> {
  return new Set([...setA].filter(x => setB.has(x)));
}

main();