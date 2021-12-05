package at.cnoize.contest.adventOfCode2021.day05

import at.cnoize.contest.util.Worker
import kotlin.math.abs
import kotlin.math.max

private const val YEAR = 2021
private const val DAY = "05"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

private const val PARSING_REGEX = """^(?<x1>\d+?),(?<y1>\d+?) -> (?<x2>\d+?),(?<y2>\d+?)$"""

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

private val workerPuzzle1 = Worker { input ->
    input
        .map(PARSING_REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toLine)
        .flatMap(Line::getAllLineCoordinates)
        .groupingBy { it }.eachCount()
        .count { it.value > 1 }
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    input
        .map(PARSING_REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toLine)
        .flatMap(Line::getAllLineCoordinatesWithDiagonals)
        .groupingBy { it }.eachCount()
        .count { it.value > 1 }
        .toString()
}

private fun MatchResult?.toLine(): Line {
    val (x1, y1, x2, y2) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into input")
    val one = Coordinate(x1.toInt(), y1.toInt())
    val two = Coordinate(x2.toInt(), y2.toInt())
    if (one > two)
        return Line(two, one)
    else
        return Line(one, two)
}

private data class Line(val start: Coordinate, val end: Coordinate) {
    val lenght = max(abs(start.x - end.x), abs(start.y - end.y))

    fun getAllLineCoordinates(): List<Coordinate> {
        return if (start.x == end.x || start.y == end.y) {
            val allCoordinates = mutableListOf<Coordinate>()
            for (y in start.y..end.y) {
                for (x in start.x..end.x) {
                    allCoordinates.add(Coordinate(x, y))
                }
            }
            allCoordinates
        } else {
            emptyList()
        }
    }

    fun getAllLineCoordinatesWithDiagonals(): List<Coordinate> {
        return if (start.x != end.x && start.y != end.y) {
            val allDiagonals = mutableListOf<Coordinate>()
            allDiagonals.add(start)
            for (i in 1..lenght) {
                var (x, y) = allDiagonals.last()
                x = when {
                    end.x > x -> x + 1
                    else -> x - 1
                }
                y = when {
                    end.y > y -> y + 1
                    else -> y - 1
                }
                allDiagonals.add(Coordinate(x, y))
            }
            allDiagonals
        } else {
            getAllLineCoordinates()
        }
    }
}

private data class Coordinate(val x: Int, val y: Int) {
    override fun toString(): String = "($x,$y)"
    operator fun compareTo(other: Coordinate): Int {
        return compareBy<Coordinate> { it.x }.thenBy { it.y }.compare(this, other)
    }
}
