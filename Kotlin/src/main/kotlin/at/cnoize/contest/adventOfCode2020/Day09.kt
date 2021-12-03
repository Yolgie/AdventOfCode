package at.cnoize.contest.adventOfCode2020.day09

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.minAndMax
import at.cnoize.contest.util.sum
import at.cnoize.contest.util.takeWhileInclusive
import java.math.BigInteger

const val YEAR = 2020
const val DAY = "09"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val FLOATING_SIZE = 5
//const val SUM_TARGET = 127L // the answer from the puzzle1

const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"
const val FLOATING_SIZE = 25
const val SUM_TARGET = 393911906L // the answer from the puzzle1

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
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

val workerPuzzle2 = Worker { input ->
    val cypher = input.map(String::toBigInteger)

    cypher.forEachIndexed { indexStart, _ ->
        cypher.drop(indexStart+1).forEachIndexed endIndex@ { index, _ ->
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

fun List<BigInteger>.containsSum(sum: BigInteger): Boolean {
    this.forEachIndexed { index, first ->
        this.drop(index+1).forEach { second ->
            if (first + second == sum) {
                return true
            }
        }
    }
    return false
}
