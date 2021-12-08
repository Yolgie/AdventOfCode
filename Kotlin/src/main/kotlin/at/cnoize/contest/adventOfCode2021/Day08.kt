package at.cnoize.contest.adventOfCode2021.day08

import at.cnoize.contest.util.*

private const val YEAR = 2021
private const val DAY = "08"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

private const val PARSING_REGEX = """^(?<signalPatterns>.+)\|(?<outputValues>.+)$"""

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 1: \n")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "")
    workerPuzzle2.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 2: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "")
}

private val workerPuzzle1 = Worker { input ->
    input
        .map(PARSING_REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toInput)
        .flatMap(Input::outputValues)
        .map(String::length)
        .count { length -> length == 2 || length == 3 || length == 4 || length == 7 }
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    input
        .map(PARSING_REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toInput)
        .map(Input::solveForSolution)
        .sum()
        .toString()
}

private fun MatchResult?.toInput(): Input {
    val (signalPatterns, outputValues) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into input")
    return Input(signalPatterns.splitOnSpace(), outputValues.splitOnSpace())
}

private data class Input(val signalPatterns: List<String>, val outputValues: List<String>) {
    val allDigits = (signalPatterns + outputValues).map(String::sorted).toSet()

    fun solveForSolution(): Int {
        val digits = mutableMapOf<Int, String>()
        digits[1] = allDigits.only { it.length == 2 }
        digits[4] = allDigits.only { it.length == 4 }
        digits[7] = allDigits.only { it.length == 3 }
        digits[8] = allDigits.only { it.length == 7 }
        val length5digits = allDigits.filter { it.length == 5 }.toSet()
        val length6digits = allDigits.filter { it.length == 6 }.toSet()

        digits[3] = length5digits.only { it.containsAll(digits[1]!!) }
        digits[6] = length6digits.only { !it.containsAll(digits[1]!!) }
        digits[9] = (length6digits-digits[6]!!).only { it.containsAll(digits[4]!!) }
        digits[0] = (length6digits-digits[6]!!-digits[9]!!).only()
        val segmentE = digits[8]!!.removeAll(digits[9]!!).toList().only()
        digits[2] = length5digits.only { it.contains(segmentE) }
        digits[5] = (length5digits-digits[3]!!-digits[2]!!).only()

        val digitMap = digits.swapKeysAndValues()

        return outputValues
            .map(String::sorted)
            .map(digitMap::get)
            .joinToInt()
    }
}
