package at.cnoize.contest.adventOfCode2021.day01

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.zipWithNext

private const val YEAR = 2021
private const val DAY = "01"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    val depths = input.map(String::toInt)
    val increases = depths.zipWithNext()
        .count { (first, second) -> first < second }

    increases.toString()
}

private val workerPuzzle2 = Worker { input ->
    val depths = input.map { i -> i.toInt() }
    val increases = depths.zipWithNext(2)
        .map(List<Int>::sum)
        .zipWithNext()
        .count { (first, second) -> first < second }

    increases.toString()
}

