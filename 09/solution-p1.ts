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

  const mins: number[] = []

  for (let x = 0; x < grid.length; x++) {
    for (let y = 0; y < grid[x].length; y++) {
      const center = grid[x][y]
      const left = Math.max(x - 1, 0)
      const right = Math.min(x + 1, grid.length - 1)
      const up = Math.max(y - 1, 0)
      const down = Math.min(y + 1, grid[x].length - 1)

      if (left !== x && grid[left][y] <= center) {
        continue
      }

      if (right !== x && grid[right][y] <= center) {
        continue
      }

      if (up !== y && grid[x][up] <= center) {
        continue
      }

      if (down !== y && grid[x][down] <= center) {
        continue
      }

      mins.push(center)
    }
  }

  let sum = 0
  mins.forEach(v => sum += v + 1)

  console.log(sum)
}

main();