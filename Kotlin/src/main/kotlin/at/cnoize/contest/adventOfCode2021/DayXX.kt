package at.cnoize.contest.adventOfCode2021.dayXX

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions

private const val YEAR = 2021
private const val DAY = "XX"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    val optionsForTestRun = WorkerOptions(title = "Test")
    val optionsForFullRun = WorkerOptions(title = "Full")

    println("Advent of Code $YEAR $DAY")
    println("Part 1:")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
    println("Part 2:")
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
}

private val workerPuzzle1 = Worker { input: Iterable<String> ->
    input.toString()
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    "N/A"
}

