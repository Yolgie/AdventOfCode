package at.cnoize.contest.adventOfCode2021.dayXX

import at.cnoize.contest.util.Worker

private const val YEAR = 2021
private const val DAY = "XX"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

private val workerPuzzle1 = Worker { input ->
    input.toString()
}

private val workerPuzzle2 = Worker { input ->
    "N/A"
}

