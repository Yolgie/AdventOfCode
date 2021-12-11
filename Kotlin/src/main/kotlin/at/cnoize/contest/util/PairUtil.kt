package at.cnoize.contest.util

fun <T> List<T>.toPair(): Pair<T, T> {
    require(this.size == 2) { "List is not of length 2!" }
    return Pair(this[0], this[1])
}

fun <T> Collection<T>.toPair(): Pair<T, T> {
    require(this.size == 2) { "Collection is not of length 2!" }
    return this.toList().toPair()
}

fun <T> List<T>.minAndMax(): Pair<T, T> where T : Comparable<T> {
    require(this.isNotEmpty()) { "List is empty" }
    return Pair(
        this.minOrNull() ?: throw IllegalArgumentException("No minimum found"),
        this.maxOrNull() ?: throw IllegalArgumentException("No maximum found")
    )
}

fun Pair<Int, Int>.abs(): Int = Math.abs(first - second)

fun Pair<Int, Int>.toRange(): IntRange = this.first..this.second

fun Pair<Int, Int>.multiply(): Int = this.first * this.second
