package at.cnoize.contest.util

import java.math.BigInteger

fun <T> List<T>.toPair(): Pair<T, T> {
    require(this.size == 2) { "List is not of length 2!" }
    return Pair(this.first(), this.second())
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

fun <K, V : Comparable<V>> Map<K, V>.minAndMaxValue(): Pair<V, V> {
    require(this.isNotEmpty()) { "Map is empty" }
    return Pair(
        this.minOf { it.value },
        this.maxOf { it.value }
    )
}

fun <K, V> Iterable<Pair<K, V>>.groupByFirst(): Map<K, List<V>> = this.groupBy(Pair<K, V>::first, Pair<K, V>::second)

fun Pair<Int, Int>.abs(): Int = kotlin.math.abs(first - second)

fun Pair<Int, Int>.toRange(): IntProgression {
    return if (first <= second) this.first..this.second
    else swap().toRange().reversed()
}

fun Pair<Int, Int>.multiply(): Int = this.first * this.second

fun Pair<Int, Int>.subtract(): Int = this.first - this.second

fun Pair<BigInteger, BigInteger>.subtract(): BigInteger = this.first - this.second

fun <A, B> Pair<A, B>.swap(): Pair<B, A> = second to first

fun <T> Pair<T, T>.contains(element: T): Boolean = first == element || second == element

fun <T> Pair<T, T>.joinToString(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)? = null
): String =
    this.toList().joinToString(separator, prefix, postfix, limit, truncated, transform)
