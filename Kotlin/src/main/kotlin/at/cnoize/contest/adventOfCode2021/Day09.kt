package at.cnoize.contest.adventOfCode2021.day09

import at.cnoize.contest.util.Worker

private typealias Coordinate = Pair<Int, Int>
private typealias Grid = List<List<Int>>
private typealias CoordinatesWithValues = Map<Coordinate, Int>

private const val YEAR = 2021
private const val DAY = "09"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 1: \n")
    workerPuzzle1.withInputFile(INPUT_FILE, title = "")
    workerPuzzle2.withInputFile(INPUT_FILE_TEST, title = "Answer Puzzle 2: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "")
}

private val workerPuzzle1 = Worker { input ->
    val grid = input.map { it.toListOfInt() }

    grid.findLowpoints()
        .values
        .map { it + 1 }
        .sum()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    val grid = input.map { it.toListOfInt() }
    val lowpoints = grid.findLowpoints()
    val basins = grid.expandBasins(lowpoints)

    basins
        .map { it.size }
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc*i }
        .toString()
}

private fun Grid.expandBasins(lowpoints: CoordinatesWithValues): List<Set<Coordinate>> {
    val gridToCheck = this.deepCopy()

    return lowpoints.map { (startingCoordinates, _) ->
        gridToCheck.accumulateNeighbors(startingCoordinates)
    }
}

private fun Grid.accumulateNeighbors(startingCoordinates: Coordinate): Set<Coordinate> {
    fun accumulateNeighborsInternal(coordinate: Coordinate, neighborsFound: Set<Coordinate>): Set<Coordinate> {
        val allNeighbors = neighborsFound.toMutableSet()
        val directNeighbors = this.getNeighbors(coordinate.first, coordinate.second)

        directNeighbors.forEach { (coordinate, value) ->
            if (value != 9 && coordinate !in allNeighbors) {
                allNeighbors += coordinate
                allNeighbors.addAll(accumulateNeighborsInternal(coordinate, allNeighbors))
            }
        }
        return allNeighbors
    }
    return accumulateNeighborsInternal(startingCoordinates, setOf(startingCoordinates))
}


private fun String.toListOfInt(): List<Int> = toList().map { it.toString().toInt() }

private fun Grid.getNeighbors(x: Int, y: Int): CoordinatesWithValues {
    val neighbors = mutableMapOf<Coordinate, Int>()
    if (x > 0) {
        neighbors.put(x - 1 to y, this[x - 1][y])
    }
    if (x < this.size - 1) {
        neighbors.put(x + 1 to y, this[x + 1][y])
    }
    if (y > 0) {
        neighbors.put(x to y - 1, this[x][y - 1])
    }
    if (y < this.first().size - 1) {
        neighbors.put(x to y + 1, this[x][y + 1])
    }
    return neighbors.toMap()
}

private fun Grid.findLowpoints(): CoordinatesWithValues {
    val lowpoints = mutableMapOf<Coordinate, Int>()
    forEachIndexed { x, rows ->
        rows.forEachIndexed { y, current ->
            if (getNeighbors(x, y).all { neighbor -> neighbor.value > current }) {
                lowpoints.put(x to y, current)
            }
        }
    }
    return lowpoints.toMap()
}

private fun Grid.deepCopy(): Grid {
    val newGrid = mutableListOf<List<Int>>()
    this.forEach { oldRow -> newGrid += oldRow.map { it } }
    return newGrid.toList()
}
