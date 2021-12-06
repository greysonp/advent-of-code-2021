export async function main() {
  const fish: number[] = (await Deno.readTextFile('input.txt'))
    .split(',')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(v => parseInt(v))

  for (let i = 0; i < 80; i++) {
    advanceDay(fish)
  }

  console.log(fish.length)
}

function advanceDay(fish: number[]) {
  let extras: number[] = []

  for (let i = 0; i < fish.length; i++) {
    fish[i] -= 1
    if (fish[i] < 0) {
      fish[i] = 6
      extras.push(8)
    }
  }

  extras.forEach(v => fish.push(v))
}

await main()