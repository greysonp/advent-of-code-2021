export async function main() {
  const positions: number[] = (await Deno.readTextFile('input.txt'))
    .split(',')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(v => parseInt(v))
    .sort((lhs, rhs) => lhs - rhs)

  let sum = 0
  positions.forEach(v => sum += v)

  let minCost = 1000000000000

  for (let i = positions[0]; i < positions[positions.length - 1]; i++) {
    minCost = Math.min(minCost, cost(positions, i))
  }

  console.log('cost: ' + minCost)
}

function cost(positions: number[], target: number): number {
  let sum = 0

  positions.forEach(v => {
    let diff = Math.abs(v - target)
    let summation = (diff * (diff + 1))/2
    sum += summation
  })

  return sum
}

await main()