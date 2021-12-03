package at.cnoize.contest.adventOfCode2020.day01

import at.cnoize.contest.util.Worker

const val YEAR = 2020
const val DAY = "01"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"
const val SUM_TARGET = YEAR

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    val inputAsInts = input.map(String::toInt)

    inputAsInts.forEach { first ->
        inputAsInts.forEach { second ->
            if (first + second == SUM_TARGET)
                return@Worker (first * second).toString()
        }
    }

    throw IllegalStateException("No Solution Found")
}

val workerPuzzle2 = Worker { input ->
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

