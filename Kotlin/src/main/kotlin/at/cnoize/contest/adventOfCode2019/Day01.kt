package at.cnoize.adventOfCode2019.day01

import at.cnoize.contest.util.Worker

private const val YEAR = 2019
private const val DAY = "01"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    input
        .map(String::toInt)
        .map(::getFuel)
        .sum().toString()
}

private val workerPuzzle2 = Worker { input ->
    input
        .map(String::toInt)
        .map(::getFuelRecursive)
        .sum().toString()
}

private fun getFuelRecursive(mass: Int): Int {
    var totalFuel = 0
    var newFuel = getFuel(mass)

    while (newFuel > 0) {
        totalFuel += newFuel
        newFuel = getFuel(newFuel)
    }
    return totalFuel
}

private fun getFuel(mass: Int): Int {
    return (mass / 3) - 2
}
