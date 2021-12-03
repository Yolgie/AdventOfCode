package at.cnoize.contest.adventOfCode2021.day02

import at.cnoize.contest.adventOfCode2021.day02.CommandTypePartOne.Companion.toCommandPartOneType
import at.cnoize.contest.adventOfCode2021.day02.CommandTypePartTwo.Companion.toCommandPartTwoType
import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.splitOnSpace

//const val INPUT_FILE = "Day02.input.test"
const val INPUT_FILE ="Day02.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
    val commands = input
        .map(String::splitOnSpace)
        .map { (commandString, distanceString) -> CommandPartOne(commandString.toCommandPartOneType(), distanceString.toInt()) }

    commands
        .fold(PositionPartOne(0, 0)) { position, command ->
            command.command.positionTransformer.invoke(
                position,
                command.distance
            )
        }
        .result
}

val workerPuzzle2 = Worker { input ->
    val commands = input
        .map(String::splitOnSpace)
        .map { (commandString, distanceString) -> CommandPartTwo(commandString.toCommandPartTwoType(), distanceString.toInt()) }

    commands
        .fold(PositionPartTwo(0, 0, 0)) { position, command ->
            command.command.positionTransformer.invoke(
                position,
                command.distance
            )
        }
        .result
}

data class CommandPartOne(val command: CommandTypePartOne, val distance: Int)

enum class CommandTypePartOne(val token: String, val positionTransformer: (position: PositionPartOne, distance: Int) -> PositionPartOne) {
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

data class PositionPartOne(val horizontalPosition: Int, val depth: Int) {
    val result: String = (horizontalPosition * depth).toString()
}

data class CommandPartTwo(val command: CommandTypePartTwo, val distance: Int)

enum class CommandTypePartTwo(val token: String, val positionTransformer: (position: PositionPartTwo, distance: Int) -> PositionPartTwo) {
    Forward("forward", { pos, distance -> pos.copy(
        horizontalPosition = pos.horizontalPosition + distance,
        depth = pos.depth + distance * pos.aim
    ) }),
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

data class PositionPartTwo(val horizontalPosition: Int, val depth: Int, val aim: Int) {
    val result: String = (horizontalPosition * depth).toString()
}

