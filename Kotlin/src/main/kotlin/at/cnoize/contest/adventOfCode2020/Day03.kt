package at.cnoize.contest.adventOfCode2020.day03

import at.cnoize.contest.util.Worker

private const val YEAR = 2020
private const val DAY = "03"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    countTreesHit(input, 3, 1).toString()
}

private val workerPuzzle2 = Worker { input ->
    val slopes = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    )

    slopes.map { (horizontal, vertical) -> countTreesHit(input, horizontal, vertical) }
        .map(Int::toBigInteger)
        .reduce { acc, i -> acc * i }
        .toString()
}

private fun countTreesHit(input: Iterable<String>, horizontalStep: Int, verticalStep: Int): Int {
    return input.mapIndexed { index, row ->
        if (index % verticalStep == 0) {
            row[horizontalStep * index / verticalStep % row.length] == '#'
        } else {
            false
        }
    }
        .count { it }
}

