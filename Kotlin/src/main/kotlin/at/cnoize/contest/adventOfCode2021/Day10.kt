package at.cnoize.contest.adventOfCode2021.day10

import at.cnoize.contest.adventOfCode2021.day10.NavigationLine.Companion.toNavigationLine
import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.middle
import at.cnoize.contest.util.validateNoNegatives
import java.math.BigInteger

private const val YEAR = 2021
private const val DAY = "10"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 1: \n")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "")
    workerPuzzle2.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 2: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "")
}

private val workerPuzzle1 = Worker { input ->
    input
        .map { it.toNavigationLine() }
        .mapNotNull { it.getFirstCorruption() }
        .mapNotNull(illegalClosingChunkScoring::get)
        .validateNoNegatives()
        .sum()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    input
        .map { it.toNavigationLine() }
        .mapNotNull(NavigationLine::autocompleteScore)
        .sorted()
        .middle()
        .toString()
}

private val chunkMap = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
private val openingChunks = chunkMap.keys
private val closingChunks = chunkMap.values
private val illegalClosingChunkScoring = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val autocompleteChunkScoring = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

private data class NavigationLine(val chunks: List<Char>) {
    val isIncomplete: Boolean = chunks.filter { it in openingChunks }.size != chunks.filter { it in closingChunks }.size
    val isCorrupted: Boolean = getFirstCorruption() != null

    fun getFirstCorruption(): Char? {
        val chunkStack = mutableListOf<Char>()

        for (chunk in chunks) {
            if (chunk in openingChunks) {
                chunkStack += chunk
            }
            if (chunk in closingChunks) {
                if (chunkMap[chunkStack.last()] == chunk) {
                    chunkStack.removeLast()
                } else {
                    return chunk
                }
            }
        }

        return null
    }

    fun autocomplete(): String? {
        val chunkStack = mutableListOf<Char>()

        for (chunk in chunks) {
            if (chunk in openingChunks) {
                chunkStack += chunk
            }
            if (chunk in closingChunks) {
                if (chunkMap[chunkStack.last()] == chunk) {
                    chunkStack.removeLast()
                } else {
                    return null
                }
            }
        }

        return chunkStack
            .reversed()
            .mapNotNull(chunkMap::get)
            .joinToString("")
    }

    fun autocompleteScore(): BigInteger? {
        return autocomplete()
            ?.fold(BigInteger.ZERO) { acc, autocompleteChunk ->
                acc.times(BigInteger.valueOf(5)) + autocompleteChunkScoring[autocompleteChunk]!!.toBigInteger()
            }
    }

    override fun toString(): String {
        return "NavigationLine ${chunks.joinToString("")} ${if (isIncomplete) "incomplete" else ""} ${if (isCorrupted) "corrupted: '${getFirstCorruption()}'" else ""}\n"
    }

    companion object {
        fun String.toNavigationLine() = NavigationLine(this.toList())
    }
}
