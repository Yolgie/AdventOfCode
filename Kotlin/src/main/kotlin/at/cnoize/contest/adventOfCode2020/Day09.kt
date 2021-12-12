package at.cnoize.contest.adventOfCode2020.day09

import at.cnoize.contest.util.*
import java.math.BigInteger

private const val YEAR = 2020
private const val DAY = "09"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val FLOATING_SIZE = 5
//private const val SUM_TARGET = 127L // the answer from the puzzle1

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"
private const val FLOATING_SIZE = 25
private const val SUM_TARGET = 393911906L // the answer from the puzzle1

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    val cypher = input.map(String::toBigInteger)

    sequence {
        cypher
            .drop(FLOATING_SIZE)
            .forEachIndexed { index, current ->
                yield(current to cypher.subList(index, index + FLOATING_SIZE))
            }
    }
        .takeWhileInclusive { (current, preambel) -> preambel.containsSum(current) }
        .last() //
        .first.toString()
}

private val workerPuzzle2 = Worker { input ->
    val cypher = input.map(String::toBigInteger)

    cypher.forEachIndexed { indexStart, _ ->
        cypher.drop(indexStart + 1).forEachIndexed endIndex@{ index, _ ->
            val indexEnd = indexStart + 1 + index
            val sum = cypher.subList(indexStart, indexEnd).sum()
            if (sum == BigInteger.valueOf(SUM_TARGET)) {
                return@Worker cypher.subList(indexStart, indexEnd).minAndMax().toList().sum().toString()
            } else if (sum > BigInteger.valueOf(SUM_TARGET)) {
                return@endIndex
            }
        }
    }
    throw IllegalStateException("No solution found")
}

private fun List<BigInteger>.containsSum(sum: BigInteger): Boolean {
    this.forEachIndexed { index, first ->
        this.drop(index + 1).forEach { second ->
            if (first + second == sum) {
                return true
            }
        }
    }
    return false
}
