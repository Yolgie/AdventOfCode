package at.cnoize.contest.adventOfCode2020.dayXX

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions

private const val YEAR = 2020
private const val DAY = "XX"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    input.toString()
}

private val workerPuzzle2 = Worker { input ->
    "N/A"
}

