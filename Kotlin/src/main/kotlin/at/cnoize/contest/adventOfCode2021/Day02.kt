package at.cnoize.contest.adventOfCode2021.day02

import at.cnoize.contest.adventOfCode2021.day02.CommandTypePartOne.Companion.toCommandPartOneType
import at.cnoize.contest.adventOfCode2021.day02.CommandTypePartTwo.Companion.toCommandPartTwoType
import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.splitOnSpace

private const val YEAR = 2021
private const val DAY = "02"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    println("Advent of Code $YEAR $DAY")
    workerPuzzle1.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsLines(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    val commands = input
        .map(String::splitOnSpace)
        .map { (commandString, distanceString) ->
            CommandPartOne(
                commandString.toCommandPartOneType(),
                distanceString.toInt()
            )
        }

    commands
        .fold(PositionPartOne(0, 0)) { position, command ->
            command.command.positionTransformer.invoke(
                position,
                command.distance
            )
        }
        .result
}

private val workerPuzzle2 = Worker { input ->
    val commands = input
        .map(String::splitOnSpace)
        .map { (commandString, distanceString) ->
            CommandPartTwo(
                commandString.toCommandPartTwoType(),
                distanceString.toInt()
            )
        }

    commands
        .fold(PositionPartTwo(0, 0, 0)) { position, command ->
            command.command.positionTransformer.invoke(
                position,
                command.distance
            )
        }
        .result
}

private data class CommandPartOne(val command: CommandTypePartOne, val distance: Int)

private enum class CommandTypePartOne(
    val token: String,
    val positionTransformer: (position: PositionPartOne, distance: Int) -> PositionPartOne
) {
    Forward("forward", { pos, distance -> pos.copy(horizontalPosition = pos.horizontalPosition + distance) }),
    Down("down", { pos, distance -> pos.copy(depth = pos.depth + distance) }),
    Up("up", { pos, distance -> pos.copy(depth = pos.depth - distance) }),
    ;

    companion object {
        private val tokenMap = values().associateBy(CommandTypePartOne::token)

        fun String.toCommandPartOneType(): CommandTypePartOne =
            tokenMap[this]
                ?: throw IllegalArgumentException("Command $this does not exist")
    }
}

private data class PositionPartOne(val horizontalPosition: Int, val depth: Int) {
    val result: String = (horizontalPosition * depth).toString()
}

private data class CommandPartTwo(val command: CommandTypePartTwo, val distance: Int)

private enum class CommandTypePartTwo(
    val token: String,
    val positionTransformer: (position: PositionPartTwo, distance: Int) -> PositionPartTwo
) {
    Forward("forward", { pos, distance ->
        pos.copy(
            horizontalPosition = pos.horizontalPosition + distance,
            depth = pos.depth + distance * pos.aim
        )
    }),
    Down("down", { pos, distance -> pos.copy(aim = pos.aim + distance) }),
    Up("up", { pos, distance -> pos.copy(aim = pos.aim - distance) }),
    ;

    companion object {
        private val tokenMap = values().associateBy(CommandTypePartTwo::token)

        fun String.toCommandPartTwoType(): CommandTypePartTwo =
            tokenMap[this]
                ?: throw IllegalArgumentException("Command $this does not exist")
    }
}

private data class PositionPartTwo(val horizontalPosition: Int, val depth: Int, val aim: Int) {
    val result: String = (horizontalPosition * depth).toString()
}
