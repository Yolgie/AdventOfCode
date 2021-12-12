package at.cnoize.contest.adventOfCode2021.day11

import at.cnoize.contest.util.IntGrid
import at.cnoize.contest.util.IntGrid.Companion.toIntGrid
import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.takeUntil

private const val YEAR = 2021
private const val DAY = "11"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Answer Puzzle 2: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    val grid = input.toIntGrid()

    generateSequence(0 to grid) { (oldCount, currentGrid) ->
        val (currentCount, newGrid) = currentGrid.simulate()
        oldCount + currentCount to newGrid
    }
        .take(101)
        .last().first
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    val grid = input.toIntGrid()

    generateSequence(OctopusSimulation(0, 0, grid)) { (index, _, currentGrid) ->
        val (newCount, newGrid) = currentGrid.simulate()
        OctopusSimulation(index + 1, newCount, newGrid)
    }
        .takeUntil { it.count == 100 }
        .step
        .toString()
}

private data class OctopusSimulation(val step: Int, val count: Int, val grid: IntGrid)

private fun IntGrid.simulate(): Pair<Int, IntGrid> {
    return this.mapValues { _, value -> value + 1 } // increase all octopuses by 1
        .simulateFlashes(0)
}

private fun IntGrid.simulateFlashes(oldCount: Int): Pair<Int, IntGrid> {
    var count = oldCount
    var grid = this
    nodes.forEach { (coordinate, node) ->
        if (node > 9) {
            count += 1
            grid = grid.addOrUpdate(coordinate) { _ -> 0 }

            val newNeighborValues = coordinate.getNeighbors(true)
                .map { neighborCoordinate ->
                    val neighborValue = grid[neighborCoordinate]
                    if (neighborValue != null && neighborValue != 0)
                        neighborCoordinate to neighborValue + 1
                    else
                        neighborCoordinate to neighborValue
                }
                .filterNot { it.second == null }
                .associate { it.first to it.second!! }
            grid = grid.updateAllIfPresent(newNeighborValues)
        }
    }

    return if (count != oldCount) {
        grid.simulateFlashes(count) // if anything flashed, check again
    } else {
        count to grid // no new flashes -> we are done
    }
}
