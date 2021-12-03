package at.cnoize.contest.adventOfCode2020.day02

import at.cnoize.contest.util.Worker

const val YEAR = 2020
const val DAY = "02"

//const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"
const val REGEX = """^(?<from>\d+?)-(?<to>\d+?) (?<char>\w): (?<password>.*)$"""

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE)
    workerPuzzle2.withInputFile(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    input
        .map(REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toInput)
        .count(Input::isValid1)
        .toString()
}

val workerPuzzle2 = Worker { input ->
    input
        .map(REGEX.toRegex()::matchEntire)
        .map(MatchResult?::toInput)
        .count(Input::isValid2)
        .toString()
}

fun MatchResult?.toInput() : Input {
    val (from, to, char, password) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into input")
    return Input(Rule(from.toInt(), to.toInt(), char.single()), password)
}

data class Input(val rule: Rule, val password: String) {
    val isValid1 = rule.validate1(password)
    val isValid2 = rule.validate2(password)
}

data class Rule(val first: Int, val second: Int, val char: Char) {
    fun validate1(input: String) : Boolean {
        return input.count { it == char } in first..second
    }

    fun validate2(input: String) : Boolean {
        return input.filterIndexed{ index, _ -> first == index+1 || second == index+1 }
            .count { it == char } == 1
    }
}
