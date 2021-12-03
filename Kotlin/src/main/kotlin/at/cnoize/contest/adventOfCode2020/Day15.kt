package at.cnoize.contest.adventOfCode2020.day15

import at.cnoize.contest.util.Worker

const val INPUT_FILE = "Day15.input.test"
//const val INPUT_FILE ="Day15.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
    input.toString()
}

val workerPuzzle2 = Worker { input ->
    "N/A"
}

