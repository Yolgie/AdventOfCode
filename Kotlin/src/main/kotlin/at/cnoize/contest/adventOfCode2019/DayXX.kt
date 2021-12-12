package at.cnoize.adventOfCode2019.dayXX

import at.cnoize.contest.util.Worker

private const val YEAR = 2019
private const val DAY = "XX"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    input.toString()
}

private val workerPuzzle2 = Worker { input ->
    input.toString()
}

