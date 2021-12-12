package at.cnoize.contest.adventOfCode2020.day01

import at.cnoize.contest.util.Worker

private const val YEAR = 2020
private const val DAY = "01"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"
private const val SUM_TARGET = YEAR

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    val inputAsInts = input.map(String::toInt)

    inputAsInts.forEach { first ->
        inputAsInts.forEach { second ->
            if (first + second == SUM_TARGET)
                return@Worker (first * second).toString()
        }
    }

    throw IllegalStateException("No Solution Found")
}

private val workerPuzzle2 = Worker { input ->
    val inputAsInts = input.map(String::toInt)

    inputAsInts.forEach { first ->
        inputAsInts.forEach { second ->
            inputAsInts.forEach { third ->
                if (first + second + third == SUM_TARGET)
                    return@Worker (first * second * third).toString()
            }
        }
    }

    throw IllegalStateException("No Solution Found")
}

