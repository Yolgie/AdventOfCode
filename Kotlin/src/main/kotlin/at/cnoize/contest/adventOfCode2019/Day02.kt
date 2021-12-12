package at.cnoize.adventOfCode2019.day02

import at.cnoize.contest.util.Worker

private const val YEAR = 2019
private const val DAY = "02"

//private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsLines(INPUT_FILE)
    workerPuzzle2.withInputFileAsLines(INPUT_FILE)
}

private val workerPuzzle1 = Worker { input ->
    val intcode = input.first().split(",").map(String::toInt).toMutableList()

    // initialize
    intcode[1] = 12
    intcode[2] = 2

    // run program
    runIntcodeProgram(intcode)

    return@Worker intcode[0].toString()
}

private fun runIntcodeProgram(intcode: MutableList<Int>): Int {
    var instructionPointer = 0
    while (intcode[instructionPointer] != OPCODES.STOP.opcode) {
        when (intcode[instructionPointer]) {
            OPCODES.ADD.opcode -> intcode[intcode[instructionPointer + 3]] = intcode[intcode[instructionPointer + 2]] + intcode[intcode[instructionPointer + 1]]
            OPCODES.MUL.opcode -> intcode[intcode[instructionPointer + 3]] = intcode[intcode[instructionPointer + 2]] * intcode[intcode[instructionPointer + 1]]
            else -> throw IllegalStateException("OPCODE ${intcode[instructionPointer]} not valid")
        }
        instructionPointer += step
    }
    return intcode[0]
}

private val workerPuzzle2 = Worker { input ->
    val originalIntcode = input.first().split(",").map(String::toInt)

    for (noun in 1..99) {
        for (verb in 1..99) {
            val intcode = originalIntcode.toMutableList()
            intcode[1] = noun
            intcode[2] = verb

            if (runIntcodeProgram(intcode) == 19690720) {
                return@Worker (noun*100+verb).toString()
            }
        }
    }

    throw IllegalStateException("no noun/verb combination found")
}

private const val step = 4

private enum class OPCODES(val opcode: Int) {
    ADD(1), //add
    MUL(2), // multiply
    STOP(99) //finished


}

private class Instruction(val opcode: OPCODES, parameterCount: Int) {
    val totalSize = parameterCount+1
}
