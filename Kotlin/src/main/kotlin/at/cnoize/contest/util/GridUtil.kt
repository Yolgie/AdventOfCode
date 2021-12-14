@file:Suppress("unused")

package at.cnoize.contest.util

import at.cnoize.contest.util.Coordinate.Companion.toCoordinate
import at.cnoize.contest.util.Grid.Companion.toGridNodes
import kotlin.math.abs
import kotlin.math.max

interface Grid<NODE> {
    val nodes: Map<Coordinate, NODE>
    val coordinates: Set<Coordinate>
        get() = nodes.keys

    // dimensions
    val xMinAndMax: Pair<Int, Int>
        get() = coordinates.map { it.x }.minAndMax()
    val yMinAndMax: Pair<Int, Int>
        get() = coordinates.map { it.y }.minAndMax()
    val xSize: Int
        get() = xMinAndMax.abs()
    val xMin: Int
        get() = xMinAndMax.first
    val xMax: Int
        get() = xMinAndMax.second
    val ySize: Int
        get() = yMinAndMax.abs()
    val yMin: Int
        get() = yMinAndMax.first
    val yMax: Int
        get() = yMinAndMax.second
    val dimensions: Coordinate
        get() = Coordinate(xSize, ySize)

    // array access
    operator fun get(coordinate: Coordinate) = nodes[coordinate]
    operator fun get(x: Int, y: Int) = nodes[Coordinate(x, y)]

    fun toCollection(blankValue: (Coordinate) -> NODE): List<List<NODE>> {
        val collection = mutableListOf<List<NODE>>()
        for (rowIndex in xMinAndMax.toRange()) {
            val row = mutableListOf<NODE>()
            for (colIndex in yMinAndMax.toRange()) {
                val coordinate = Coordinate(rowIndex, colIndex)
                if (coordinate in nodes) {
                    row.add(nodes[coordinate]!!)
                } else {
                    row.add(blankValue(coordinate))
                }
            }
            collection.add(row.toList())
        }
        return collection.toList()
    }

    fun visualize(
        direction: Pair<Direction, Direction> = Direction.UP to Direction.RIGHT,
        valueToChar: (Coordinate, NODE) -> Char = { _, value -> value.toString().first() },
        blankChar: Char = ' '
    ) {
        val horizontalRange =
            if (direction.contains(Direction.RIGHT)) xMinAndMax.toRange() else xMinAndMax.toRange().reversed()
        val verticalRange =
            if (direction.contains(Direction.DOWN)) yMinAndMax.toRange() else yMinAndMax.toRange().reversed()
        for (y in verticalRange) {
            for (x in horizontalRange) {
                val coordinate = Coordinate(x, y)
                if (coordinate in coordinates) {
                    print(valueToChar(coordinate, nodes[coordinate]!!))
                } else {
                    print(blankChar)
                }
                if (x == horizontalRange.last)
                    println()
            }
        }
    }

    companion object {
        fun <NODE> Iterable<Iterable<NODE>>.toGridNodes(): Map<Coordinate, NODE> =
            flatMapIndexed { x, row ->
                row.mapIndexed { y, value ->
                    Coordinate(x, y) to value
                }
            }.toMap()
    }
}

class IntGrid(override val nodes: Map<Coordinate, Int>) : Grid<Int> {

    // update functions
    fun addOrUpdate(coordinate: Coordinate, updater: (Int?) -> Int?): IntGrid {
        return IntGrid(
            nodes.toMutableMap()
                .update(coordinate, updater.invoke(nodes[coordinate]))
                .toMap()
        )
    }

    fun addOrUpdateAll(new: Map<Coordinate, Int>): IntGrid {
        val newNodes = nodes.toMutableMap()
        newNodes.putAll(new)
        return IntGrid(newNodes.toMap())
    }

    fun updateAllIfPresent(new: Map<Coordinate, Int>): IntGrid {
        val newNodes = nodes.toMutableMap()
        new.mapValues { (coordinate, value) ->
            if (coordinate in coordinates) {
                newNodes[coordinate] = value
            }
        }
        return IntGrid(newNodes.toMap())
    }

    fun updateIfPresent(coordinate: Coordinate, updater: (Int) -> Int?): IntGrid {
        return if (coordinate in coordinates) {
            addOrUpdate(coordinate) { value -> updater.invoke(value!!) }
        } else {
            this
        }
    }

    fun mapValues(updater: (Coordinate, Int) -> Int?): IntGrid {
        return IntGrid(
            nodes.toMutableMap()
                .mapValues { (coordinate, value) -> updater(coordinate, value) }
                .filterValues { it != null } // remove values set to null
                .mapValues { (_, value) -> value!! }
                .toMap()
        )
    }

    fun transpose() = IntGrid(nodes.mapKeys { (coordinate, _) -> coordinate.toPair().swap().toCoordinate() })

    companion object {
        // conversion helpers
        fun Iterable<Iterable<Int>>.toGrid() = IntGrid(this.toGridNodes())
        fun Iterable<String>.toIntGrid() = IntGrid(this.map(String::toListOfInt).toGridNodes())
        fun Map<Coordinate, Int>.toGrid() = IntGrid(this)
    }
}

data class Coordinate(
    val x: Int,
    val y: Int,
    private val comparator: Comparator<Coordinate> = compareByXThenByY,
    private val digitLength: Int = max(x.toString().length, y.toString().length)
) : Comparable<Coordinate> {

    fun getManhattanDistance(other: Coordinate): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    fun step(direction: Direction, distance: Int = 1): Coordinate {
        return when (direction) {
            Direction.UP -> Coordinate(x, y + distance)
            Direction.DOWN -> Coordinate(x, y - distance)
            Direction.LEFT -> Coordinate(x - distance, y)
            Direction.RIGHT -> Coordinate(x + distance, y)
        }
    }

    fun getNeighbors(inclDiagonal: Boolean = false): Set<Coordinate> {
        return setOf(
            this.step(Direction.UP),
            this.step(Direction.RIGHT),
            this.step(Direction.DOWN),
            this.step(Direction.LEFT)
        ) + if (inclDiagonal) setOf(
            this.step(Direction.UP).step(Direction.LEFT),
            this.step(Direction.UP).step(Direction.RIGHT),
            this.step(Direction.DOWN).step(Direction.LEFT),
            this.step(Direction.DOWN).step(Direction.RIGHT)
        ) else emptySet()
    }

    fun toPair(): Pair<Int, Int> = x to y

    override fun toString(): String {
        return """[${x.zeroPad(digitLength)},${y.zeroPad(digitLength)}]"""
    }

    operator fun rangeTo(that: Coordinate) = CoordinateRange(this, that)

    override fun compareTo(other: Coordinate): Int = comparator.compare(this, other)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate
        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    companion object {
        val compareByXThenByY: Comparator<Coordinate> =
            compareBy<Coordinate> { it.x }.thenBy { it.y }
        val compareByManhattanDistance: Comparator<Coordinate> =
            compareBy<Coordinate> { it.getManhattanDistance(Coordinate(0, 0)) }

        private val parsingRegex = Regex("""\[(-?\d*?),(-?\d*?)]""")

        fun String.toCoordinate(regex: Regex = parsingRegex): Coordinate {
            if (!this.matches(regex))
                throw IllegalArgumentException("Could not parse $this into a coordinate")
            val (x, y) = regex.find(this)!!.destructured
            return Coordinate(x.toInt(), y.toInt())
        }

        fun List<Int>.toCoordinate(): Coordinate = toPair().toCoordinate()
        fun Pair<Int, Int>.toCoordinate(): Coordinate = Coordinate(first, second)
    }
}

enum class Direction {
    UP, LEFT, DOWN, RIGHT;

    fun nextClockwise(): Direction {
        return valueArray[(this.ordinal + valueArray.size - 1) % valueArray.size]
    }

    fun nextCounterClockwise(): Direction {
        return valueArray[(this.ordinal + 1) % valueArray.size]
    }

    companion object {
        private val valueArray = values()
    }
}

class CoordinateRange(override val start: Coordinate, override val endInclusive: Coordinate) : ClosedRange<Coordinate>,
    Iterable<Coordinate> {

    override fun iterator(): Iterator<Coordinate> {
        return EasyCoordinateIterator(start, endInclusive)
    }
}

private class EasyCoordinateIterator(val start: Coordinate, val endInclusive: Coordinate) : Iterator<Coordinate> {
    var next = start

    override fun hasNext(): Boolean {
        return next <= endInclusive
    }

    override fun next(): Coordinate {
        if (!hasNext())
            throw NoSuchElementException()
        val current = next
        next = when {
            next.y < endInclusive.y -> Coordinate(next.x, next.y + 1)
            else -> Coordinate(next.x + 1, start.y)
        }
        return current
    }
}
