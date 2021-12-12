package at.cnoize.contest.adventOfCode2021.day07

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.minAndMax
import at.cnoize.contest.util.toRange
import kotlin.math.abs

private const val YEAR = 2021
private const val DAY = "07"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Answer Puzzle 2: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    val horizontalPositions = input.first().split(",").map(String::toInt)

    val alignment = horizontalPositions.minAndMax().toRange()
        .associateWith { targetPosition -> horizontalPositions.calcFuelConsumption(targetPosition) }
        .minByOrNull { (_, totalFuelConsumption) -> totalFuelConsumption }

    alignment?.value.toString()
}

private val workerPuzzle2 = Worker { input ->
    val horizontalPositions = input.first().split(",").map(String::toInt)

    val alignment = horizontalPositions.minAndMax().toRange()
        .associateWith { targetPosition -> horizontalPositions.calcFuelConsumptionExponential(targetPosition) }
        .minByOrNull { (_, totalFuelConsumption) -> totalFuelConsumption }

    alignment?.value.toString()
}

private fun List<Int>.calcFuelConsumption(targetPosition: Int): Int {
    return sumOf { crabPosition -> abs(crabPosition - targetPosition) }
}

private fun List<Int>.calcFuelConsumptionExponential(targetPosition: Int): Int {
    return sumOf { crabPosition -> (1..abs(crabPosition - targetPosition)).sum() }
}
