export async function main() {
  const fish: number[] = (await Deno.readTextFile('input.txt'))
    .split(',')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(v => parseInt(v))


  let count = 0
  fish.forEach((v, i) => {
    console.log(`Starting ${i + 1}/${fish.length}...`)
    count += offspring(v, 256)
    console.log('Done')
  })

  console.log(count)
}

const memo: Map<number, number> = new Map()

function offspring(age: number, daysLeft: number): number {
  if (age === 8 && memo.has(daysLeft)) {
    const saved = memo.get(daysLeft)
    if (saved) {
      return saved
    }
  }

  let offspringCount = 1 // 1 for self

  while (daysLeft > age) {
    daysLeft -= age

    if (daysLeft > 0) {
      const additionalOffspring = offspring(8, daysLeft - 1)
      memo.set(daysLeft - 1, additionalOffspring)
      offspringCount += additionalOffspring
    }

    age = 6
    daysLeft -= 1
  }

  return offspringCount
}

await main()