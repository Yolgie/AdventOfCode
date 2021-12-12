package at.cnoize.adventOfCode2019.day03

import at.cnoize.contest.util.Coordinate
import at.cnoize.contest.util.Direction
import at.cnoize.contest.util.Worker
import kotlin.math.max

private const val YEAR = 2019
private const val DAY = "03"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    val (wireA, wireB) = input.map { wireString ->
        wireString.split(',').map(String::toWirePath)
    }

    val origin = Coordinate(0, 0)
    val allWiresA = getAllCoordinatesForWire(origin, wireA)
    val allWiresB = getAllCoordinatesForWire(origin, wireB)

    val wireboard = Wireboard()
        .addOrUpdateAllCoordinates(allWiresA) { node -> node.copy(wireA = true) }
        .addOrUpdateAllCoordinates(allWiresB) { node -> node.copy(wireB = true) }

    wireboard.visualize()

    input.toString()
}

private fun getAllCoordinatesForWire(
    origin: Coordinate,
    wirePath: List<WirePath>
): Set<Coordinate> {
    return getPathCoordinates(origin, wirePath)
        .zipWithNext()
        .flatMap { (start, end) -> (start..end) }
        .toSet()
}

private val workerPuzzle2 = Worker { input ->
    input.toString()
}

private fun getPathCoordinates(start: Coordinate, wire: List<WirePath>): Sequence<Coordinate> {
    return sequence {
        yield(start)
        var current = start
        for (nextWirePath in wire) {
            current = current.step(nextWirePath.direction, nextWirePath.distance)
            yield(current)
        }
    }
}

private data class WirePath(val direction: Direction, val distance: Int)

private fun WirePath.getDestinationCoordinate(start: Coordinate): Coordinate {
    return when (direction) {
        Direction.UP -> Coordinate(start.x, start.y + distance)
        Direction.DOWN -> Coordinate(start.x, start.y - distance)
        Direction.LEFT -> Coordinate(start.x - distance, start.y)
        Direction.RIGHT -> Coordinate(start.x + distance, start.y)
    }
}

private fun String.toWirePath(): WirePath {
    return WirePath(directionMap[this.substring(0, 1)]!!, this.substring(1).toInt())
}

private val directionMap = mapOf(
    "U" to Direction.UP,
    "L" to Direction.LEFT,
    "D" to Direction.DOWN,
    "R" to Direction.RIGHT
)

private data class Wireboard(override val nodes: Map<Coordinate, WireboardElement> = emptyMap()) :
    EndlessGrid<WireboardElement> {
    override val coordinates: Set<Coordinate> = nodes.keys

    override fun visualize() {
        require(coordinates.isNotEmpty()) { "Empty board can not be visualized" }
        val fullBoard = MinimalSquare(coordinates, padding = 1)
        val fullBoardWithFrame = MinimalSquare(fullBoard, padding = 1)
        for (coordinate in fullBoardWithFrame) {
            val node: Node? = when (coordinate) {
                !in fullBoard -> FrameNode()
                else -> nodes[coordinate]
            }
            print(node?.gridChar ?: ' ')
            if (coordinate.x == fullBoardWithFrame.maxX) {
                println()
            }
        }
    }

    fun addOrUpdateNode(
        coordinate: Coordinate,
        updater: (WireboardElement) -> WireboardElement
    ): Wireboard {
        val newNodes = this.nodes.toMutableMap()
        newNodes[coordinate] = newNodes.getOrDefault(coordinate, WireboardElement()).run(updater)
        return Wireboard(newNodes.toMutableMap())
    }

    fun addOrUpdateAllCoordinates(
        coordinates: Collection<Coordinate>,
        updater: (WireboardElement) -> WireboardElement
    ): Wireboard {
        val newNodes = this.nodes.toMutableMap()
        coordinates.forEach { coordinate ->
            newNodes[coordinate] = newNodes.getOrDefault(coordinate, WireboardElement()).run(updater)
        }
        return Wireboard(newNodes.toMutableMap())
    }
}

private data class WireboardElement(val wireA: Boolean = false, val wireB: Boolean = false) : Node {
    override val gridChar: Char = when {
        wireA && wireB -> 'X'
        wireA && !wireB -> 'A'
        !wireA && wireB -> 'B'
        else -> '.'
    }
}

private data class FrameNode(override val gridChar: Char = '#') : Node

private interface EndlessGrid<NODE : Node> {
    val coordinates: Set<Coordinate>
    val nodes: Map<Coordinate, NODE>
    fun visualize()
}

private interface Node {
    val gridChar: Char
}

private class ReadingDirectionCoordinateIterator(val minimalSquare: MinimalSquare) : Iterator<Coordinate> {
    var next = minimalSquare.topLeft

    override fun hasNext(): Boolean {
        return minimalSquare.contains(next)
    }

    override fun next(): Coordinate {
        if (!hasNext())
            throw NoSuchElementException()
        val current = next
        next = when {
            next.x < minimalSquare.maxX -> Coordinate(next.x + 1, next.y)
            else -> Coordinate(minimalSquare.minX, next.y - 1)
        }
        return current
    }
}

private class SpiralCoordinateSequence(val start: Coordinate, val endInclusive: Coordinate) : Sequence<Coordinate> {
    override fun iterator(): Iterator<Coordinate> {
        return SpiralCoordinateIterator(start, endInclusive)
    }
}

// to do write test for this
// to do use this to find the closest crossing to origin
private class SpiralCoordinateIterator(val start: Coordinate, val endInclusive: Coordinate) : Iterator<Coordinate> {
    var next = start
    var top = start.y
    var bottom = start.y
    var left = start.x
    var right = start.x
    var direction = Direction.RIGHT
    var hasNext = true

    override fun hasNext(): Boolean {
        return hasNext
    }

    override fun next(): Coordinate {
        if (!hasNext())
            throw NoSuchElementException()

        val current = next
        if (current == endInclusive) {
            hasNext = false
        }
        if (current.x !in left..right || current.y !in bottom..top) {
            direction = direction.nextClockwise()
        }
        top = max(top, next.y)
        bottom = kotlin.math.min(bottom, next.y)
        left = kotlin.math.min(left, next.x)
        right = max(right, next.x)

        next = next.step(direction)

        return current
    }
}

private class MinimalSquare(coordinates: Collection<Coordinate>, padding: Int = 0) : Iterable<Coordinate> {
    constructor(minimalSquare: MinimalSquare, padding: Int = 0) : this(
        minimalSquare.bottomRight,
        minimalSquare.topLeft,
        padding = padding
    )

    constructor(vararg coordinates: Coordinate, padding: Int = 0) : this(coordinates.toList(), padding)

    val minX = coordinates.minOf { it.x } - padding
    val minY = coordinates.minOf { it.y } - padding
    val maxY = coordinates.maxOf { it.y } + padding
    val maxX = coordinates.maxOf { it.x } + padding

    val topLeft = Coordinate(minX, maxY)
    val topRight = Coordinate(maxX, maxY)
    val bottomLeft = Coordinate(minX, minY)
    val bottomRight = Coordinate(maxX, minY)

    override fun iterator(): Iterator<Coordinate> {
        return ReadingDirectionCoordinateIterator(this)
    }

    fun contains(element: Coordinate): Boolean {
        return (minX..maxX).contains(element.x) && (minY..maxY).contains(element.y)
    }
}
