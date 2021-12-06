package at.cnoize.contest.adventOfCode2021.day06

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.sum
import java.math.BigInteger

private const val YEAR = 2021
private const val DAY = "06"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

private const val GESTATION_TIME = 6
private const val GESTATION_TIME_FIRST = GESTATION_TIME + 2
private const val DAYS_TO_RUN_SIMULATION_FOR = 81
private const val DAYS_TO_RUN_SIMULATION_FOR_LONG = 257

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 1: \n")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "")
    workerPuzzle2.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 2: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "")
}

private val workerPuzzle1 = Worker { input ->
    val lanternfish = input.first().split(",").map { it.toInt() }

    generateSequence(lanternfish, List<Int>::simulateDay)
        .take(DAYS_TO_RUN_SIMULATION_FOR)
        .last()
        .count()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    val lanternfish = input.first().split(",").map { it.toInt() }
    val lanternfishGrouped =
        lanternfish
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toBigInteger() }

    generateSequence(lanternfishGrouped, Map<Int, BigInteger>::simulateDayGrouped)
        .take(DAYS_TO_RUN_SIMULATION_FOR_LONG)
        .last()
        .values
        .sum()
        .toString()
}

private fun List<Int>.simulateDay(): List<Int> {
    var newFishCount = 0
    val oldFish = map { lanternfishAge ->
        when {
            lanternfishAge > 0 -> lanternfishAge - 1
            else -> {
                newFishCount += 1
                GESTATION_TIME
            }
        }
    }
    return oldFish + List(newFishCount) { GESTATION_TIME_FIRST }
}

private fun Map<Int, BigInteger>.simulateDayGrouped(): Map<Int, BigInteger> {
    val nextIteration = mutableMapOf<Int, BigInteger>()
    this.forEach { (daysLeft, count) ->
        if (daysLeft == 0) {
            nextIteration[GESTATION_TIME] = nextIteration.getOrDefault(GESTATION_TIME, BigInteger.ZERO) + count
            nextIteration[GESTATION_TIME_FIRST] = count
        } else {
            nextIteration[daysLeft - 1] = nextIteration.getOrDefault(daysLeft - 1, BigInteger.ZERO) + count
        }
    }
    return nextIteration.toMap()
}
