package at.cnoize.contest.adventOfCode2021.day12

import at.cnoize.contest.adventOfCode2021.day12.CaveMap.Companion.toCaveMap
import at.cnoize.contest.util.*

private const val YEAR = 2021
private const val DAY = "12"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE_TEST_MEDIUM = "adventOfCode$YEAR/Day$DAY.input.test_medium"
private const val INPUT_FILE_TEST_LARGE = "adventOfCode$YEAR/Day$DAY.input.test_large"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    println("Advent of Code $YEAR $DAY")
    println("Puzzle 1:")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Test"))
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST_MEDIUM, WorkerOptions(title = "TMed"))
    workerPuzzle1.withInputFileAsLines(INPUT_FILE_TEST_LARGE, WorkerOptions(title = "TLrg"))
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Full"))
    println("Puzzle 2:")
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST, WorkerOptions(title = "Test"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST_MEDIUM, WorkerOptions(title = "TMed"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE_TEST_LARGE, WorkerOptions(title = "TLrg"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Full"))
}

private val workerPuzzle1 = Worker { input ->
    input.parseToCaveMap()
        .getAllPathsVisitingSmallCavesOnce()
        .count()
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    input.parseToCaveMap()
        .getAllPathsVisitingOneSmallCaveTwice()
        .count()
        .toString()
}

private fun Iterable<String>.parseToCaveMap(): CaveMap = this
    .map { inputLine -> inputLine.split("-").map(::Node).toPair() }
    .toCaveMap()

private data class Node(
    val name: String
) {
    val isStart: Boolean = this == START
    val isEnd: Boolean = this == END
    val isLarge: Boolean = name.first().isUpperCase()
    val isSmall: Boolean = name.first().isLowerCase() && !isStart && !isEnd

    companion object {
        val START = Node("start")
        val END = Node("end")
    }
}

private data class CaveMap(
    val connections: Map<Node, Set<Node>>
) {
    fun getConnections(node: Node): Set<Node> = connections[node] ?: emptySet()

    fun getConnectionsVisitingSmallCavesOnlyOnce(path: Path): Set<Node> =
        connections[path.last()]
            ?.minus(path.getSmall())
            ?: emptySet()

    fun getConnectionsVisitingOneSmallCaveTwice(path: Path): Set<Node> {
        val allConnections = connections[path.last()] ?: emptySet()

        return when {
            path.hasAnySmallDuplicates -> allConnections - path.getSmall()
            else -> allConnections
        }
    }

    fun getAllPathsVisitingSmallCavesOnce(): List<Path> {
        return getAllPathsSelectingBy(::getConnectionsVisitingSmallCavesOnlyOnce)
    }

    fun getAllPathsVisitingOneSmallCaveTwice(): List<Path> {
        return getAllPathsSelectingBy(::getConnectionsVisitingOneSmallCaveTwice)
    }

    private fun getAllPathsSelectingBy(selector: (Path) -> Set<Node>): List<Path> {
        fun getAllPaths(currentPath: Path): List<Path> {
            if (currentPath.last().isEnd)
                return listOf(currentPath)

            return selector(currentPath)
                .map { nextNode -> currentPath + nextNode }
                .flatMap(::getAllPaths)
        }

        return getAllPaths(Path(Node.START))
    }

    companion object {
        fun List<Pair<Node, Node>>.toCaveMap(): CaveMap = CaveMap(
            (this + this.map { it.swap() }) // add both directions
                .groupByFirst()
                .filterNot { it.key.isEnd } // remove paths starting from end
                .mapValues { it.value.filterNot(Node::isStart) } // remove paths going to start
                .mapValues { it.value.toSet() }
        )
    }
}

private data class Path(
    val nodes: List<Node>
) {
    val hasAnySmallDuplicates = nodes
        .filter(Node::isSmall)
        .eachCount()
        .any { (_, count) -> count > 1 }

    constructor(node: Node) : this(listOf(node))

    fun getSmall() = nodes.filter(Node::isSmall).toSet()
    fun last() = nodes.last()
    operator fun plus(nextNode: Node) = Path(nodes + nextNode)

    override fun toString(): String {
        return "Path: ${nodes.map(Node::name).joinToString(" -> ")}\n"
    }
}
