package at.cnoize.contest.adventOfCode2021.day17

import at.cnoize.contest.util.*

private const val YEAR = 2021
private const val DAY = "17"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"
private const val REGEX = """^target area: x=(?<xFrom>-?\d+?)..(?<xTo>-?\d+?), y=(?<yFrom>-?\d+?)..(?<yTo>-?\d+?)$"""

private typealias ProbePosition = Coordinate
private typealias Velocity = Coordinate
private typealias TargetArea = Pair<Coordinate, Coordinate>

private val START: ProbePosition = Coordinate(0, 0)

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
    val targetArea = input.parseToTargetArea()

    getPossibleValuesForX(targetArea)
        .map { possibleX ->
            generateAllSequences(possibleX, targetArea)
                .mapKeys { (possibleY, _) -> Coordinate(possibleX, possibleY) }
        }
        .mergeMaps()
        .flatMapValues()
        .maxOfOrNull { it.y }
        .toString()
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    val targetArea = input.parseToTargetArea()

    getPossibleValuesForX(targetArea)
        .map { possibleX ->
            generateAllSequences(possibleX, targetArea)
                .mapKeys { (possibleY, _) -> Coordinate(possibleX, possibleY) }
        }
        .mergeMaps()
        .count()
        .toString()
}

private fun Iterable<String>.parseToTargetArea(): TargetArea =
    this.map(REGEX.toRegex()::matchEntire).map(MatchResult?::toTargetArea).only()

private fun MatchResult?.toTargetArea(): TargetArea {
    val (fromX, toX, fromY, toY) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into input")
    return Coordinate(fromX.toInt(), fromY.toInt()) to Coordinate(toX.toInt(), toY.toInt())
}

private fun getPossibleValuesForX(targetArea: TargetArea): List<Int> {
    fun Int.inTargetArea() = this in targetArea.toRangeInX()
    return (1..targetArea.getMaxX())
        .map { xCandidate ->
            xCandidate to (1 until xCandidate).reversed().runningFold(xCandidate) { acc, i -> acc + i }
        }
        .filter { (_, xCoordinates) -> xCoordinates.any(Int::inTargetArea) }
        .map { (xCandidate, _) -> xCandidate }
}

private fun Pair<ProbePosition, Velocity>.calculateNext(): Pair<ProbePosition, Velocity> {
    val (probePosition, velocity) = this
    return probePosition + velocity to velocity.applyGravity()
}

private fun Velocity.applyGravity(): Velocity {
    return Coordinate(this.x.stepTowards(0), this.y - 1)
}

private fun generateAllSequences(
    initialXVelocity: Int,
    targetArea: TargetArea
): Map<Int, List<ProbePosition>> {
    val result = mutableMapOf<Int, List<ProbePosition>>()

    for (initialYVelocity in targetArea.getMinY()..200) { // to do find a better upper bound
        val sequenceForYVelocity =
            (START to Velocity(initialXVelocity, initialYVelocity))
                .generatePositions()
                .takeWhile { position -> position.y >= targetArea.getMinY() }
                .toList()

        if (sequenceForYVelocity.any { it.inTargetArea(targetArea) })
            result[initialYVelocity] = sequenceForYVelocity
    }
    return result
}

private fun Pair<ProbePosition, Velocity>.generateSequence() =
    generateSequence(this) { it.calculateNext() }

private fun Pair<ProbePosition, Velocity>.generatePositions() =
    generateSequence().map { (position, _) -> position }

private fun ProbePosition.inTargetArea(targetArea: TargetArea): Boolean =
    this.x in targetArea.first.x..targetArea.second.x && this.y in targetArea.first.y..targetArea.second.y

private fun TargetArea.getMinY() = toList().minOfOrNull { it.y }!!
private fun TargetArea.getMaxX() = toList().maxOfOrNull { it.x }!!
