class Day02part1 : Puzzle<String>(2, 1) {
    override fun solve(input: List<String>): String {
        val containsExactlyTwoOfAnyLetter = { mapOfChars: Map<Char, Int> -> mapOfChars.containsValue(2) }
        val containsExactlyThreeOfAnyLetter = { mapOfChars: Map<Char, Int> -> mapOfChars.containsValue(3) }
        val mapOfChars = input.map { getMapOfChars(it) }
        val containsExactlyTwoOfAnyLetterCount = mapOfChars.filter(containsExactlyTwoOfAnyLetter).count()
        val containsExactlyThreeOfAnyLetterCount = mapOfChars.filter(containsExactlyThreeOfAnyLetter).count()
        return (containsExactlyThreeOfAnyLetterCount * containsExactlyTwoOfAnyLetterCount).toString()
    }

    private fun getMapOfChars(input: String): Map<Char, Int> {
        val charsMap = mutableMapOf<Char, Int>()
        input.forEach {
            charsMap[it] = charsMap.getOrDefault(it, 0) + 1
        }
        return charsMap.toMap()
    }
}

class Day02part2 : Puzzle<String>(2, 2) {
    override fun solve(input: List<String>): String {
        input.forEachIndexed { index: Int, one: String ->
            input.subList(index, input.size).forEach { other: String ->
                if (distanceOfOne(one, other)) {
                    return common(one, other)
                }
            }
        }
        throw IllegalStateException("No Christmas this year")
    }

    private fun distanceOfOne(one: String, other: String) = distance(one, other) == 1

    private fun distance(one: String, other: String): Int {
        if (one.length != other.length) {
            throw IllegalArgumentException("Inputs must be of the same lenght")
        }
        return one.zip(other).filter { pair -> pair.first != pair.second }.count()
    }

    private fun common(one: String, other: String): String {
        return one.zip(other).filter { pair -> pair.first == pair.second }.map { pair -> pair.first }.joinToString("")
    }
}

