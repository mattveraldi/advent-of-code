const fs = require("fs");
const path = require("path");


const file = fs.readFileSync(path.join(__dirname, "../input.txt"));
const contents = file.toString();
const tokens = contents.split("\n");
let result = 0;
for (const line of tokens) {
    let firstNumber = null;
    let lastNumber = null;
    for (const letter of line) {
        if (Number.isFinite(+letter)) {
            if (!firstNumber) {
                firstNumber = letter;
            }
            else {
                lastNumber = letter;
            }
        }
    }
    if (!lastNumber) lastNumber = firstNumber;
    const num = firstNumber + lastNumber;
    result += +num;
}
console.log(result);