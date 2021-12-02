const fs = require('fs')

let horizontal = 0
let depth = 0

fs.readFileSync('input.txt')
  .toString()
  .split('\n')
  .forEach(line => {
    const parts = line.split(' ')
    const command = parts[0]
    const value = parseInt(parts[1])

    switch (command) {
      case 'forward': 
        horizontal += value; 
        break;
      case 'up': 
        depth -= value; 
        break;
      case 'down': 
        depth += value; 
        break;
    }
  })

console.log(horizontal * depth)