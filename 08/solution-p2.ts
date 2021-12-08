export function main() {
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

  let sum = 0
  entries.forEach(entry => {
    sum += decodeOutput(entry)
  })

  console.log(sum)
}

// Segment counts:
// 0: 6
// 1: 2
// 2: 5
// 3: 5
// 4: 4
// 5: 5
// 6: 6
// 7: 3
// 8: 7
// 9: 6
function decodeOutput(entry: Entry): number {
  const knownDigits: Set<string>[] = []
  for (let i = 0; i < 10; i++) {
    knownDigits.push(new Set())
  }

  const allSamples: string[] = entry.signals.concat(entry.outputs)

  allSamples.forEach(sample => {
    switch (sample.length) {
      case 2:
        addCharsToSet(sample, knownDigits[1])
        break;
      case 4:
        addCharsToSet(sample, knownDigits[4])
        break;
      case 3:
        addCharsToSet(sample, knownDigits[7])
        break;
      case 7:
        addCharsToSet(sample, knownDigits[8])
        break;
    }
  })

  allSamples.forEach(sample => {
    const letters: Set<string> = asSet(sample)

    if (sample.length === 5) {
      // 2, 3, 5
      if (knownDigits[1].size > 0 && intersection(knownDigits[1], letters).size === 2) {
        addCharsToSet(sample, knownDigits[3])
      } else if (knownDigits[7].size > 0 && intersection(knownDigits[7], letters).size === 3) {
        addCharsToSet(sample, knownDigits[3])
      } else if (knownDigits[4].size > 0 && intersection(knownDigits[4], letters).size === 2) {
        addCharsToSet(sample, knownDigits[2])
      }
    } else if (sample.length === 6) {
      // 0, 6, 9
      if (knownDigits[4].size > 0 && intersection(knownDigits[4], letters).size === 4) {
        addCharsToSet(sample, knownDigits[9])
      } else if (knownDigits[1].size > 0 && intersection(knownDigits[1], letters).size === 1) {
        addCharsToSet(sample, knownDigits[6])
      } else if (knownDigits[7].size > 0 && intersection(knownDigits[7], letters).size === 2) {
        addCharsToSet(sample, knownDigits[6])
      }
    }
  })

  allSamples.forEach(sample => {
    const letters: Set<string> = asSet(sample)

    if (sample.length === 5) {
      // 2, 3, 5
      if (knownDigits[3].size > 0 && intersection(knownDigits[3], letters).size !== letters.size && knownDigits[9].size > 0 && intersection(knownDigits[9], letters).size === 5) {
        addCharsToSet(sample, knownDigits[5])
      } else if (knownDigits[6].size > 0 && intersection(knownDigits[6], letters).size === 5) {
        addCharsToSet(sample, knownDigits[5])
      } else if (knownDigits[3].size > 0 && intersection(knownDigits[3], letters).size === 4 && knownDigits[4].size > 0 && intersection(knownDigits[4], letters).size === 3) {
        addCharsToSet(sample, knownDigits[5])
      }
    } else if (sample.length === 6) {
      // 0, 6, 9
      if (knownDigits[6].size > 0 && intersection(knownDigits[6], letters).size !== letters.size && intersection(knownDigits[4], letters).size === 3) {
        addCharsToSet(sample, knownDigits[0])
      }
    }
  })

  let finalOutput = ''
  entry.outputs.forEach(output => {
    finalOutput += findMatchingDigit(knownDigits, output)
  })

  return parseInt(finalOutput)
}

function addCharsToSet(val: string, set: Set<String>) {
  if (set.size > 0) {
    return
  }

  for (let i = 0; i < val.length; i++) {
    set.add(val.charAt(i))
  }
}

function findMatchingDigit(signalSets: Set<string>[], output: string): string {
  for (let i = 0; i < signalSets.length; i++) {
    const signalSet: Set<string> = signalSets[i]
    if (matchesSignal(signalSet, output)) {
      return '' + i
    }
  }

  console.error(`Could not find digit for output with length ${output}`)
  Deno.exit(1)
}

function matchesSignal(signalSet: Set<string>, output: string): boolean {
  if (output.length !== signalSet.size) {
    return false
  }

  for (let i = 0; i < output.length; i++) {
    if (!signalSet.has(output.charAt(i))) {
      return false
    }
  }

  return true
}

function asSet(output: string): Set<string> {
  const set: Set<string> = new Set()

  for (let i = 0; i < output.length; i++) {
    set.add(output[i])
  }

  return set
}

function intersection(setA: Set<string>, setB: Set<string>) {
  return new Set([...setA].filter(x => setB.has(x)));
}

type Entry = {
  signals: string[],
  outputs: string[]
}

main()