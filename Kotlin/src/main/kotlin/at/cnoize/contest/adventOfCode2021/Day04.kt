package at.cnoize.contest.adventOfCode2021.day04

import at.cnoize.contest.util.*

private const val YEAR = 2021
private const val DAY = "04"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

private val workerPuzzle1 = Worker { input ->
    val selectedNumbers = input.first().split(",").map { it.toInt() }.iterator()
    val boards = input.drop(1).chunked(BingoBoard.size).map { it.toBingoBoard() }

    generateSequence(boards) { remainingBoards ->
        val nextNumber = selectedNumbers.next()
        remainingBoards.map { it.mark(nextNumber) }
    }
        .takeUntilFirst { it.winner() }
        .score()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    val selectedNumbers = input.first().split(",").map { it.toInt() }.iterator()
    val boards = input.drop(1).chunked(BingoBoard.size).map { it.toBingoBoard() }

    generateSequence(boards) { remainingBoards ->
        val nextNumber = selectedNumbers.next()
        val newBoards = remainingBoards.map { it.mark(nextNumber) }

        if (newBoards.size > 1)
            newBoards.filterNot { it.winner() }
        else
            newBoards
    }
        .takeUntil { it.all { it.winner() } }
        .only()
        .score()
        .toString()
}

private data class BingoBoard(val values: List<Int>, val lastNumber: Int) {
    fun sum(): Int = values.sum()
    fun score(): Int = lastNumber.times(this.sum())
    fun winner(): Boolean {
        val rows = values.chunked(size)
        val cols = values.withIndex().groupBy { it.index % size }.map { it.value.map { it.value } }
        return rows.map { it.sum() }.any { it == 0 }
                || cols.map { it.sum() }.any { it == 0 }
    }

    fun mark(nextNumber: Int): BingoBoard {
        return BingoBoard(values.map { if (it == nextNumber) 0 else it }, nextNumber)
    }

    companion object {
        const val size: Int = 5
    }
}

private fun List<String>.toBingoBoard(): BingoBoard {
    return BingoBoard(this.flatMap { it.splitOnSpace().map { it.toInt() } }, 0)
}
