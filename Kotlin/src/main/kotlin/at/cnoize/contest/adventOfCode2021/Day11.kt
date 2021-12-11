package at.cnoize.contest.adventOfCode2021.day11

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.takeUntil

private const val YEAR = 2021
private const val DAY = "11"

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
    val grid = input.map { it.map { it.toString().toInt() } }

    generateSequence(0 to grid) { (oldCount, currentGrid) ->
        val (currentCount, newGrid) = currentGrid.simulate()
        oldCount + currentCount to newGrid
    }
        .take(101)
        .last()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    val grid = input.map { it.map { it.toString().toInt() } }

    generateSequence(OctupusSimulation(0, 0, grid)) { (index, _, currentGrid) ->
        val (newCount, newGrid) = currentGrid.simulate()
        OctupusSimulation(index + 1, newCount, newGrid)
    }
        .takeUntil { it.count == 100 }
        .toString()
}

private data class OctupusSimulation(val step: Int, val count: Int, val grid: Grid)

private typealias Grid = List<List<Int>>
private typealias MutableGrid = MutableList<MutableList<Int>>

private data class Coordinate(val x: Int, val y: Int)

private fun Grid.getNeighbors(x: Int, y: Int): Set<Coordinate> {
    val neighbors = mutableSetOf<Coordinate>()
    val dimension = this.size - 1
    if (x > 0) {
        neighbors.add(Coordinate(x - 1, y))
    }
    if (x < dimension) {
        neighbors.add(Coordinate(x + 1, y))
    }
    if (y > 0) {
        neighbors.add(Coordinate(x, y - 1))
    }
    if (y < dimension) {
        neighbors.add(Coordinate(x, y + 1))
    }
    if (x > 0 && y > 0) {
        neighbors.add(Coordinate(x - 1, y - 1))
    }
    if (x > 0 && y < dimension) {
        neighbors.add(Coordinate(x - 1, y + 1))
    }
    if (x < dimension && y < dimension) {
        neighbors.add(Coordinate(x + 1, y + 1))
    }
    if (x < dimension && y > 0) {
        neighbors.add(Coordinate(x + 1, y - 1))
    }
    return neighbors.toSet()
}

private fun Grid.simulate(): Pair<Int, Grid> {
    return this.map { it.map { it + 1 } } // all levels increased by one
        .simulateFlashes(0)
}

private fun Grid.simulateFlashes(oldCount: Int): Pair<Int, MutableGrid> {
    val grid = this.map { it.toMutableList() }.toMutableList()
    var count = oldCount

    this.forEachIndexed { x, rows ->
        rows.forEachIndexed { y, current ->
            if (current > 9) {
                grid[x][y] = 0
                count += 1
                val neighbors = grid.getNeighbors(x, y)
                neighbors.forEach { coordinate ->
                    if (grid[coordinate.x][coordinate.y] != 0) {
                        grid[coordinate.x][coordinate.y] += 1
                    }
                }
            }
        }
    }
    return if (count != oldCount) {
        grid.simulateFlashes(count) // if anything flashed, check again
    } else {
        count to grid // no new flashes -> we are done
    }
}

private fun Grid.visualize() = joinToString("\n") { it.joinToString("") }
