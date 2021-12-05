package at.cnoize.contest.adventOfCode2020.day13

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.multiply
import at.cnoize.contest.util.second
import at.cnoize.contest.util.takeWhileInclusive
import java.math.BigInteger

private const val YEAR = 2020
private const val DAY = "13"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

private val workerPuzzle1 = Worker { input ->
    val earliestDeparture = input
            .first()
            .toInt()
    val busTimes = input
            .second()
            .split(',')
            .filterNot { it == "x" }
            .map(String::toInt)

    busTimes
            .associateWith { busStart -> getNextBus(earliestDeparture, busStart) - earliestDeparture }
            .minByOrNull { it.value }
            ?.toPair()
            ?.multiply()
            ?.toString()
            ?: throw IllegalStateException("No Bustimes found")
}

private fun getNextBus(departure: Int, busInterval: Int): Int {
    return ((departure / busInterval) + 1) * busInterval
}

private fun getNextBus(departure: BigInteger, busInterval: BigInteger): BigInteger {
    return ((departure / busInterval) + BigInteger.ONE) * busInterval
}


private val workerPuzzle2 = Worker { input ->
    val busTimes = input
            .second()
            .split(',')
            .mapIndexed { index, busInterval -> index.toBigInteger() to busInterval }
            .associate { it }
            .filterValues { it != "x" }
            .mapValues { (_, busInterval) -> busInterval.toBigInteger() }

    val firstBus = busTimes.getOrElse(BigInteger.ZERO) { throw IllegalStateException("No Bustimes found") }

    generateSequence(getNextBus(BigInteger.TEN.pow(14), firstBus)) { it.plus(firstBus) }
            .onEach { candidate -> if (candidate.mod(BigInteger.TEN.pow(10)) == BigInteger.ZERO) println(candidate) }
            .takeWhileInclusive { candidate -> !isValidSolution(candidate, busTimes) }
            .last()
            .toString()
}

private fun isValidSolution(candidate: BigInteger, busTimes: Map<BigInteger, BigInteger>): Boolean {
    return busTimes
            .asSequence()
            .map { (busPosition, busInterval) -> (candidate + busPosition) % busInterval }
            .all { it == BigInteger.ZERO }
}

