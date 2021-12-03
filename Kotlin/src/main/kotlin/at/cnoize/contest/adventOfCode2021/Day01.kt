package at.cnoize.contest.adventOfCode2021.day01

import at.cnoize.contest.util.Worker

const val YEAR = 2021
const val DAY = "01"

const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
    val depths = input.map(String::toInt)
    val increases = depths.zipWithNext()
        .count { (first, second) -> first < second }

    increases.toString()
}

val workerPuzzle2 = Worker { input ->
    val depths = input.map { i -> i.toInt() }
    val increases = depths.zipWithNext().zip(depths.drop(2))
        .map { (pair, single) -> pair.toList() + single }
        .map(List<Int>::sum)
        .zipWithNext()
        .count { (first, second) -> first < second }

    increases.toString()
}

