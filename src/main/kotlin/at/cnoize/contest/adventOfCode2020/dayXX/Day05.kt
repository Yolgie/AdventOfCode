package at.cnoize.contest.adventOfCode2020.day05

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.minAndMax
import at.cnoize.contest.util.toRange
import java.math.BigInteger

const val INPUT_FILE = "Day05.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    input
        .map(::parseBinaryPositioning)
        .map(Seat::seatId)
        .maxOrNull()
        .toString()
}

val workerPuzzle2 = Worker { input ->
    val allTakenSeats = input
        .map(::parseBinaryPositioning)
        .map(Seat::seatId)

    allTakenSeats.minAndMax()
        .toRange()
        .filterNot { it in allTakenSeats }
        .toString()
}

data class Seat(val row: Int, val col: Int) {
    val seatId = row * 8 + col
}

fun parseBinaryPositioning(input: String): Seat {
    val rowInput = input.take(7)
    val colInput = input.takeLast(3)

    val row = rowInput
        .map {
            when (it) {
                'F' -> 0
                'B' -> 1
                else -> throw IllegalArgumentException("row can not be found with input: $it")
            }
        }
        .mapIndexed { index, selector ->
            BigInteger.valueOf(2).pow(6 - index).multiply(selector.toBigInteger())
        }
        .reduce(BigInteger::add)
        .toInt()

    val col = colInput
        .map {
            when (it) {
                'L' -> 0
                'R' -> 1
                else -> throw IllegalArgumentException("col can not be found with input: $it")
            }
        }
        .mapIndexed { index, selector ->
            BigInteger.valueOf(2).pow(2 - index).multiply(selector.toBigInteger())
        }
        .reduce(BigInteger::add)
        .toInt()

    return Seat(row, col)
}
