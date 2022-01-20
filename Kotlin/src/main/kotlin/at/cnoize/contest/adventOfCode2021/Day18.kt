package at.cnoize.contest.adventOfCode2021.day18

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.debugPrintln
import at.cnoize.contest.util.divBy2RoundUp
import kotlin.text.contains

private const val YEAR = 2021
private const val DAY = "18"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

private const val DIGITS = "0123456789"
private const val REGEX_MATCH_BRACES =
    """(?s)(?=\[)(?:(?=.*?\[(?!.*?\1)(.*\](?!.*\2).*))(?=.*?\](?!.*?\2)(.*)).)+?.*?(?=\1)[^\[]*(?=\2$)"""
private const val REGEX_DIGITS = """(\d+)"""

@Suppress("DuplicatedCode")
fun main() {
    val optionsForTestRun = WorkerOptions(title = "Test")
    val optionsForFullRun = WorkerOptions(title = "Full")

    println("Advent of Code $YEAR $DAY")
    println("Part 1:")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
//    workerPuzzle1.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
    println("Part 2:")
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
}

private val workerPuzzle1 = Worker { input: Iterable<String> ->
    val numbers = input.map { it.toSnailFishNumber() }

    numbers
        .reduce { acc, otherNumber -> (acc + otherNumber).reduce() }
        .debugPrintln()
        .magnitude
        .toString()
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    "N/A"
}

private fun Int.toSnailFishNumber() = SnailFishNumber(regularNumber = this)
private fun Pair<SnailFishNumber, SnailFishNumber>.toSnailFishNumber() =
    SnailFishNumber(left = first, right = second)

private fun String.toSnailFishNumber(): SnailFishNumber {
    fun String.toSnailFishInternal() = when (this.first()) {
        in DIGITS -> this.toInt().toSnailFishNumber()
        else -> this.toSnailFishNumber()
    }
    require(this.first() == '[')
    require(this.last() == ']')
    val content = this.drop(1).dropLast(1)

    val first = if (content.first() in DIGITS) {
        REGEX_DIGITS.toRegex().find(content)!!.value
    } else if (content.first() == '[') {
        REGEX_MATCH_BRACES.toRegex().find(content)!!.value
    } else throw IllegalArgumentException("Cannot parse $content")
    val second = content.removePrefix(first).drop(1)

    return SnailFishNumber(left = first.toSnailFishInternal(), right = second.toSnailFishInternal())
}

private data class SnailFishNumberWithParent(
    val parent: SnailFishNumber
)

private data class SnailFishNumber(
    val regularNumber: Int? = null,
    val left: SnailFishNumber? = null,
    val right: SnailFishNumber? = null,
//    var parent: SnailFishNumber? = null
) {
    val maxDepth: Int = listOfNotNull(left?.maxDepth, right?.maxDepth, 0).maxOrNull()!!

    //    val depth: Int = parent?.depth?.plus(1) ?: 0
    val magnitude: Int = regularNumber ?: (left!!.magnitude * 3 + right!!.magnitude * 2)

    fun reduce(): SnailFishNumber {
        return this.explode()
    }

    fun explode(): SnailFishNumber {
        fun SnailFishNumber.explodeInternal(currentDepth: Int): Pair<SnailFishNumber?, Pair<Int, Int>?> {
            return if (left?.regularNumber != null && right?.regularNumber != null && currentDepth >= 4) {
                SnailFishNumber(regularNumber = -1) to Pair(left.regularNumber, right.regularNumber)
            } else {
                if (regularNumber != null) this to null
                else {
                    val possibleExplosionLeft = this.left!!.explodeInternal(currentDepth + 1)
                    val (newRight, explosionUp) = if (possibleExplosionLeft.second == null) {
                        val possibleExplosionRight = this.right!!.explodeInternal(currentDepth + 1)
                        possibleExplosionRight.first to possibleExplosionRight.second
                    } else {
                        this.right to possibleExplosionLeft.second
                    }
                    return SnailFishNumber(left = possibleExplosionLeft.first, right = newRight) to explosionUp
                }
            }
        }

        return this.explodeInternal(0).first!!
    }

    fun split(): SnailFishNumber {
        return when {
            regularNumber != null && regularNumber!! >= 10 -> SnailFishNumber(
                left = SnailFishNumber(regularNumber = regularNumber!! / 2),
                right = SnailFishNumber(regularNumber = regularNumber!!.divBy2RoundUp())
            )
            left != null && right != null -> SnailFishNumber(
                left = left.split(),
                right = right.split()
            )
            else -> this
        }
    }

    override fun toString(): String = regularNumber?.toString() ?: "[$left,$right]"

    operator fun plus(other: SnailFishNumber) = SnailFishNumber(left = this, right = other)
}
//
//private operator fun Pair<Int, Int>?.plus(other: Pair<Int, Int>?): Pair<Int, Int>? {
//    if (this != null)
//}

