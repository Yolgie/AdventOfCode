package at.cnoize.contest.adventOfCode2021.day13

import at.cnoize.contest.util.*
import at.cnoize.contest.util.Coordinate.Companion.toCoordinate
import at.cnoize.contest.util.IntGrid.Companion.toGrid

private const val YEAR = 2021
private const val DAY = "13"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

private const val PARSING_FOLDS_REGEX = """^fold along (?<direction>.+)=(?<location>.+)$"""

@Suppress("DuplicatedCode")
fun main() {
    val optionsForTestRun = WorkerOptions(title = "Test")
    val optionsForFullRun = WorkerOptions(title = "Full")

    println("Advent of Code $YEAR $DAY")
    println("Part 1:")
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE, optionsForFullRun)
    println("Part 2:")
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE, optionsForFullRun)
}

private val workerPuzzle1 = Worker { input: Iterable<String> ->
    val (grid, folds) = input.parseToGridAndFolds()

    grid.applyFold(folds.first())
        .coordinates
        .count()
        .toString()
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    val (grid, folds) = input.parseToGridAndFolds()

    folds.fold(grid, IntGrid::applyFold)
        .also { it.visualize(
            direction = Direction.DOWN to Direction.RIGHT,
            valueToChar = { _, _ -> '#' }
        ) }
        .toString()
}

private fun Iterable<String>.parseToGridAndFolds(): Pair<IntGrid, List<Fold>> {
    val (dotsString, foldsString) = this.only().splitOnEmptyLine()
    val dots = dotsString.splitOnNewLine()
        .map { it.split(",").map(String::toInt).toCoordinate() }
    val grid = IntGrid(dots.associateWith { 1 })
    val folds = foldsString.splitOnNewLine()
        .map(PARSING_FOLDS_REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toFold)

    return grid to folds
}

private fun MatchResult?.toFold(): Fold {
    val (direction, location) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into input")
    return Fold(directionMappingFromString[direction]!!, location.toInt())
}

private val directionMappingFromString = mapOf("x" to Direction.UP, "y" to Direction.LEFT)
private val directionMappingAccessCoordinate = mapOf(
    Direction.UP to { coordinate: Coordinate -> coordinate.x },
    Direction.LEFT to { coordinate: Coordinate -> coordinate.y }
)
private val directionMappingWriteCoordinate = mapOf(
    Direction.UP to { coordinate: Coordinate, newValue: Int -> coordinate.copy(x = newValue) },
    Direction.LEFT to { coordinate: Coordinate, newValue: Int -> coordinate.copy(y = newValue) }
)

private data class Fold(val direction: Direction, val location: Int)

private fun IntGrid.applyFold(fold: Fold): IntGrid {
    val coordinateAccessor = directionMappingAccessCoordinate[fold.direction]!!
    val coordinateWriter = directionMappingWriteCoordinate[fold.direction]!!

    val unchanged = nodes.filterKeys { coordinateAccessor(it) < fold.location }
    val folded = nodes
        .filterKeys { coordinateAccessor(it) > fold.location }
        .mapKeys { (coordinate, _) ->
            coordinateWriter(coordinate, fold.location - (coordinateAccessor(coordinate) - fold.location))
        }
    return (unchanged + folded).toGrid()
}



