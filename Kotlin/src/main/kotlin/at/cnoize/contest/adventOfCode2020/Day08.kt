package at.cnoize.contest.adventOfCode2020.day08

import at.cnoize.contest.adventOfCode2020.day08.Operation.Companion.toOperation
import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.splitOnSpace
import at.cnoize.contest.util.takeWhileInclusive
import at.cnoize.contest.util.update

const val YEAR = 2020
const val DAY = "08"

const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
    val program = input
            .map(String::splitOnSpace)
            .map { it.toList() }
            .map { (operationString, argumentString) ->
                Instruction(operationString.toOperation(), argumentString.toInt())
            }

    generateSequence(MachineState(program), MachineState::execute)
            .takeWhile { machineState -> !machineState.currentInstructionInHistory }
            .last().accumulator.toString()
}

val workerPuzzle2 = Worker { input ->
    val program = input
            .map(String::splitOnSpace)
            .map { it.toList() }
            .map { (operationString, argumentString) ->
                Instruction(operationString.toOperation(), argumentString.toInt())
            }

    val allModifiedPrograms = sequence {
        program.forEachIndexed { index, instruction ->
            @Suppress("NON_EXHAUSTIVE_WHEN") // all other operations do not create a new checkable program
            when (instruction.operation) {
                Operation.Jump -> yield(program.update(index, program[index].copy(operation = Operation.NoOperation)))
                Operation.NoOperation -> yield(program.update(index, program[index].copy(operation = Operation.Jump)))
            }
        }
    }

    allModifiedPrograms
            .map { modifiedProgram ->
                generateSequence(MachineState(modifiedProgram), MachineState::execute)
                        .takeWhileInclusive { machineState ->
                            !machineState.currentInstructionInHistory && !machineState.finished
                        }
                        .last()
            }
            .takeWhileInclusive { machineState -> !machineState.finished }
            .last().accumulator.toString()
}

data class MachineState(
        val programm: List<Instruction>,
        val functionPointer: Int = 0,
        val accumulator: Int = 0,
        val instructionHistory: List<Int> = emptyList()
) {
    val currentInstruction = if (functionPointer >= programm.size) null else programm[functionPointer]
    val currentInstructionInHistory = functionPointer in instructionHistory
    val finished = functionPointer == programm.size

    private fun withUpdatedHistory(): MachineState =
            this.copy(instructionHistory = instructionHistory + functionPointer)

    fun execute(): MachineState {
        return currentInstruction?.operation?.machineAction
                ?.invoke(this.withUpdatedHistory(), currentInstruction.argument)
                ?: this
    }
}

data class Instruction(val operation: Operation, val argument: Int)

enum class Operation(
        val token: String,
        val machineAction: (machineState: MachineState, argument: Int) -> MachineState
) {
    Add("acc", { machineState, argument ->
        machineState.copy(
                accumulator = machineState.accumulator.plus(argument),
                functionPointer = machineState.functionPointer.inc()
        )
    }),
    Jump("jmp", { machineState, argument ->
        machineState.copy(functionPointer = machineState.functionPointer.plus(argument))
    }),
    NoOperation("nop", { machineState, _ ->
        machineState.copy(functionPointer = machineState.functionPointer.inc())
    }),
    ;

    companion object {
        private val tokenMap = values().associateBy(Operation::token)

        fun String.toOperation(): Operation =
                tokenMap[this]
                        ?: throw IllegalArgumentException("Operation $this does not exist")
    }
}
