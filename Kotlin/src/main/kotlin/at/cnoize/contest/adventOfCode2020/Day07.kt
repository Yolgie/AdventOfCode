package at.cnoize.contest.adventOfCode2020.day07

import at.cnoize.contest.util.Worker

const val YEAR = 2020
const val DAY = "07"

const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

val SHINY_GOLD_BAG = Bag("shiny gold")
val RULE_REGEX = """^(?<bag>.+?) bags contain (?<content>.+?)$""".toRegex()
val CONTENT_REGEX = """^(?<count>\d+?) (?<color>.+?) bags?.?$""".toRegex()

fun main() {
    workerPuzzle1.withInputFile(INPUT_FILE, title = "Answer Puzzle 1: \n")
    workerPuzzle2.withInputFile(INPUT_FILE, title = "Answer Puzzle 2: \n")
}

val workerPuzzle1 = Worker { input ->
    val rules = input
        .map(RULE_REGEX::matchEntire)
        .filterNotNull()
        .map(MatchResult::parseRule)

    rules
        .associate { rule -> rule to getChildrenRecursive(rules, rule.bag) }
        .filterValues { children -> SHINY_GOLD_BAG in children }
        .count()
        .toString()
}

val workerPuzzle2 = Worker { input ->
    val rules = input
        .map(RULE_REGEX::matchEntire)
        .filterNotNull()
        .map(MatchResult::parseRule)

    countChildrenRecursive(rules, SHINY_GOLD_BAG)
        .toString()
}

data class Rule(val bag: Bag, val content: Map<Bag, Int>)

data class Bag(val color: String)

fun getChildrenRecursive(rules: List<Rule>, bag: Bag, children: Set<Bag> = emptySet()): Set<Bag> {
    val newChildren = rules.find { it.bag == bag }
        ?.content?.keys
        ?: throw IllegalArgumentException("Bag $bag was not in the rules")
    val allChildren = children + newChildren

    return allChildren + (newChildren - children).flatMap { child -> getChildrenRecursive(rules, child, allChildren) }
}

fun countChildrenRecursive(rules: List<Rule>, bag: Bag): Int {
    return rules.find { it.bag == bag }?.content
        ?.map { (bag, count) -> count * (1 + countChildrenRecursive(rules, bag)) }
        ?.sum()
        ?: throw IllegalArgumentException("Bag $bag was not in the rules")
}

fun MatchResult?.parseRule(): Rule {
    val (bag, content) = this?.destructured
        ?: throw IllegalArgumentException("could not parse match result into rule")
    if (content == "no other bags.") {
        return Rule(Bag(bag), emptyMap())
    }
    return Rule(Bag(bag), content.parseContent())
}

fun String.parseContent(): Map<Bag, Int> {
    return this.split(",")
        .map(String::trim)
        .map(CONTENT_REGEX::matchEntire)
        .filterNotNull()
        .map { matchResult -> matchResult.groups as MatchNamedGroupCollection }
        .associate { matchGroups -> matchGroups.parseBagColor() to matchGroups.parseBagCount() }
}

fun MatchNamedGroupCollection.parseBagCount() = this["count"]!!.value.toInt()
fun MatchNamedGroupCollection.parseBagColor() = Bag(this["color"]!!.value)
