package at.cnoize.contest.adventOfCode2021.day15

import at.cnoize.contest.util.*
import at.cnoize.contest.util.IntGrid.Companion.toGrid
import at.cnoize.contest.util.IntGrid.Companion.toIntGrid
import kotlin.math.min

private const val YEAR = 2021
private const val DAY = "15"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    val optionsForTestRun = WorkerOptions(title = "Test")
    val optionsForFullRun = WorkerOptions(title = "Full")

    println("Advent of Code $YEAR $DAY")
    println("Part 1:")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
    println("Part 2:")
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, optionsForFullRun)
}

private val workerPuzzle1 = Worker { input: Iterable<String> ->
    val grid = input.toIntGrid()
    grid.calculateTotalRisk().toString()
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    val initialGrid = input.toIntGrid()

    initialGrid.generateLargerVersion()
//        .also { it.transpose().visualizeTight(direction = Direction.DOWN to Direction.RIGHT) }
        .calculateTotalRisk()
        .toString()


    // not 3012
}

private fun IntGrid.calculateTotalRisk(): Int {
    val riskGrid = calculateRiskGrid()
//        .also { it.transpose().visualize(direction = Direction.DOWN to Direction.RIGHT) }
    val totalRisk = riskGrid[riskGrid.coordinates.maxOrNull()!!]!!
    val riskOfStart = riskGrid[Coordinate(0, 0)]!!
    return (totalRisk - riskOfStart)
}

private fun IntGrid.calculateRiskGrid(): IntGrid {
    val originalGrid = this
    fun debugPrint(current: Coordinate, currentGrid: Map<Coordinate, Int>): String {
        return "$current : ${originalGrid[current]} + min(" +
                "${currentGrid[current.step(Direction.LEFT)]}, ${currentGrid[current.step(Direction.DOWN)]}) [[" +
                "${currentGrid[current.step(Direction.RIGHT)]}, ${currentGrid[current.step(Direction.UP)]}]]"
    }

    fun MutableMap<Coordinate, Int>.initializeRiskNodes(current: Coordinate): MutableMap<Coordinate, Int> {
        val addedRisk = listOf(Direction.DOWN, Direction.LEFT)
            .map(current::step)
            .mapNotNull(this::get)
            .minOrNull() ?: 0
        this[current] = originalGrid[current]!! + addedRisk
        return this
    }

    fun MutableMap<Coordinate, Int>.addRiskNodes(current: Coordinate): MutableMap<Coordinate, Int> {
//        if (current.x == 499 || current.y == 499) {
//            println(debugPrint(current, this))
//        }
        val addedRisk = min(this[current.step(Direction.DOWN)]!!, this[current.step(Direction.LEFT)]!!)
        require(addedRisk >= 0) { debugPrint(current, this) }
        this[current] = originalGrid[current]!! + addedRisk
        return this
    }

    val initialGrid = this.coordinates
        .filter { it.x == 0 || it.y == 0 }
        .fold(mutableMapOf<Coordinate, Int>()) { grid, current ->
            grid.initializeRiskNodes(current)
        }

    return originalGrid.coordinates
        .filterNot { it.x == 0 || it.y == 0 }
//        .sortedWith(Coordinate.compareByManhattanDistance)
        .fold(initialGrid) { grid, current ->
            grid.addRiskNodes(current)
        }
        .toGrid()
}

private fun IntGrid.generateLargerVersion(): IntGrid {
    fun IntGrid.duplicateCountTimes(direction: Direction, count: Int): IntGrid {
        val gridsToAppend = (1..count)
            .map { increase ->
                this.mapValues { _, oldValue ->
                    if (oldValue + increase > 9) (oldValue + increase) % 10 + 1 else oldValue + increase
                }
            }

        return gridsToAppend
            .fold(this) { current, new -> current.appendGrid(direction, new) }
    }
    return this.duplicateCountTimes(Direction.LEFT, 4)
        .duplicateCountTimes(Direction.DOWN, 4)
}

private fun IntGrid.appendGrid(direction: Direction, appendedGrid: IntGrid): IntGrid {
    if (direction == Direction.LEFT) {
        val translatexBy = this.xMax
        val translatedGrid = appendedGrid.nodes
            .mapKeys { (coordinate, _) -> coordinate.copy(x = coordinate.x + 1 + translatexBy) }
        return this.addOrUpdateAll(translatedGrid)
    } else if (direction == Direction.DOWN) {
        val translateyBy = this.yMax
        val translatedGrid = appendedGrid.nodes
            .mapKeys { (coordinate, _) -> coordinate.copy(y = coordinate.y + 1 + translateyBy) }
        return this.addOrUpdateAll(translatedGrid)
    } else throw NotImplementedError()
}
