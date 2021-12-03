package at.cnoize.adventOfCode2019.day01

import at.cnoize.contest.util.Worker

const val YEAR = 2019
const val DAY = "01"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    input
        .map(String::toInt)
        .map(::getFuel)
        .sum().toString()
}

val workerPuzzle2 = Worker { input ->
    input
        .map(String::toInt)
        .map(::getFuelRecursive)
        .sum().toString()
}

fun getFuelRecursive(mass: Int): Int {
    var totalFuel = 0
    var newFuel = getFuel(mass)

    while (newFuel > 0) {
        totalFuel += newFuel
        newFuel = getFuel(newFuel)
    }
    return totalFuel
}

fun getFuel(mass: Int): Int {
    return (mass / 3) - 2
}
