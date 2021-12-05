package at.cnoize.contest.adventOfCode2021.day03

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.binaryToInt
import at.cnoize.contest.util.transpose

const val YEAR = 2021
const val DAY = "03"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

private val workerPuzzle1 = Worker { input ->
    val transposed = input.map { it.asIterable() }.transpose()
    val counts = transposed.map { it.count { element -> element != '0' } }
    val totalCount = transposed.first().size
    val gamma = counts.map { if (it > totalCount / 2) '1' else '0' }.joinToString("")
    val epsilon = counts.map { if (it < totalCount / 2) '1' else '0' }.joinToString("")

    (gamma.binaryToInt() * epsilon.binaryToInt()).toString()
}

private val workerPuzzle2 = Worker { input ->
    val oxygenRating = filterCandidates(input, '1', '0')
    val co2Rating = filterCandidates(input, '0', '1')

    (oxygenRating.binaryToInt() * co2Rating.binaryToInt()).toString()
}

private fun filterCandidates(input: Iterable<String>, target: Char, other: Char): String {
    val candidates = input.toMutableList()
    var pos = 0
    while (candidates.size > 1) {
        val criterium = getCountPerDigit(candidates, target, other)
        candidates.removeIf { it[pos] != criterium[pos] }
        pos++
    }
    return candidates.first()
}

private fun getCountPerDigit(matrix: List<String>, target: Char, other: Char): String {
    val transposed = matrix.map { it.toList() }.transpose()
    val totalCount = transposed.first().size
    val counts = transposed.map { it.count { element -> element == '1' } }
    val result = (counts.map { if (it >= totalCount / 2.0) target else other }.joinToString(""))
    return result
}
