package at.cnoize.contest.adventOfCode2020.day10

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions

private const val YEAR = 2020
private const val DAY = "10"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

private const val MIN_JOLT_DIFFERENCE = 1
private const val MAX_JOLT_DIFFERENCE = 3

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    val adapters = input.map(String::toInt)
    val device = adapters.maxOrNull()?.plus(MAX_JOLT_DIFFERENCE)
        ?: throw IllegalArgumentException("No adapters provided")

    val differences = (adapters + 0 + device).sorted().zipWithNext()
        .groupBy { (first, second) -> second - first }

    if (MIN_JOLT_DIFFERENCE in differences && MAX_JOLT_DIFFERENCE in differences)
        (differences[MIN_JOLT_DIFFERENCE]!!.size * differences[MAX_JOLT_DIFFERENCE]!!.size).toString()
    else throw IllegalStateException("Required differences are not found in the input")
}

private val workerPuzzle2 = Worker { input ->
    val adapters = input.map(String::toInt)
    val device = adapters.maxOrNull()?.plus(MAX_JOLT_DIFFERENCE)
        ?: throw IllegalArgumentException("No adapters provided")

    val completeList = (adapters + 0 + device).sorted()

    val candidatesForRemoval = adapters
        .filter { adapter -> isValid(completeList - adapter) }
        .sorted()

    countValidArrangements(completeList, candidatesForRemoval).plus(1).toString()
}

private fun countValidArrangements(completeList: List<Int>, candidatesForRemoval: List<Int>): Int {
    if (candidatesForRemoval.isEmpty()) return 0

    val currentCandidate = candidatesForRemoval.first()
    val otherCandidates = candidatesForRemoval.drop(1)
    val shortList = completeList - currentCandidate

    return if (isValid(shortList)) {
        1 +
                countValidArrangements(shortList, otherCandidates) +
                countValidArrangements(completeList, otherCandidates)
    } else {
        0 + countValidArrangements(completeList, otherCandidates)
    }
}

private fun isValid(adapters: List<Int>): Boolean {
    return adapters
        .sorted()
        .zipWithNext()
        .all { (first, second) -> second - first in MIN_JOLT_DIFFERENCE..MAX_JOLT_DIFFERENCE }
}
