export async function main() {
  const entries: Entry[] = Deno.readTextFileSync('input.txt')
    .split('\n')
    .map(v => v.trim())
    .filter(v => v.length > 0)
    .map(line => {
      const halves: string[] = line.split('|').map(v => v.trim())
      const firstHalf: string[] = halves[0].split(' ').map(v => v.trim())
      const secondHalf: string[] = halves[1].split(' ').map(v => v.trim())
      return {
        signals: firstHalf,
        outputs: secondHalf
      }
    })

  let count = 0
  entries.forEach(entry => {
    entry.outputs.forEach(output => {
      if (output.length === 2 || output.length == 4 || output.length ===  3 || output.length == 7) {
        count++
      }
    })
  })

  console.log(count)
}

type Entry = {
  signals: string[],
  outputs: string[]
}

await main()