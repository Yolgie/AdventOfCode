package at.cnoize.contest.adventOfCode2020.day06

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.splitOnEmptyLine
import at.cnoize.contest.util.splitOnNewLine

const val YEAR = 2020
const val DAY = "06"

const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
  input.first()
        .splitOnEmptyLine()
        .map(String::parseGroup)
        .sumBy { group -> group.allAnswers.count }
        .toString()
}

val workerPuzzle2 = Worker { input ->
    input.first()
        .splitOnEmptyLine()
        .map(String::parseGroup)
        .sumBy { group -> group.commonAnswers.count }
        .toString()
}

data class Group(val answers: Collection<Answer>) {
    val allAnswers = answers.reduce(Answer::collect)
    val commonAnswers = answers.reduce(Answer::coalesce)

}

fun String.parseGroup() = Group(this.splitOnNewLine().map(String::parseCount))

data class Answer(val answer: Set<Char>) {
    val count = answer.size

    fun collect(other: Answer) = Answer(answer + other.answer)
    fun coalesce(other: Answer) = Answer(answer.filter { it in other.answer }.toSet())
}

fun String.parseCount() = Answer(this.toSet())
