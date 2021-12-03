package at.cnoize.adventOfCode2019.dayXX

import at.cnoize.contest.util.Worker

const val YEAR = 2019
const val DAY = "XX"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    input.toString()
}

val workerPuzzle2 = Worker { input ->
    input.toString()
}

