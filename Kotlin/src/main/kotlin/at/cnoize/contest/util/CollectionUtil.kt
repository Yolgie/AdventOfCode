@file:Suppress("unused")

package at.cnoize.contest.util

import java.math.BigInteger

fun <T> Iterable<T>.update(index: Int, element: T) =
    take(index) + element + drop(index + 1)

/** updates the map by creating and returning a modified copy, setting to null removes the value from the map */
fun <K : Any, V : Any> Map<K, V>.update(key: K, value: V?): Map<K, V> {
    val newMap = this.toMutableMap()
    if (value == null) {
        newMap.remove(key)
    } else {
        newMap[key] = value
    }
    return newMap
}

fun Collection<BigInteger>.sum(): BigInteger =
    this.sumOf { it }

fun <T> Iterable<T>.second(): T = this.drop(1).first()

fun <E> List<E>.middle(): E {
    val middleIndex = this.size / 2
    return this[middleIndex]
}

fun <T> Iterable<T>.only(): T {
    val list = this.toList()
    require(list.size == 1) { "Does not contain only one element" }
    return list.first()
}

inline fun <T> Iterable<T>.only(predicate: (T) -> Boolean): T {
    val list = this.filter(predicate)
    require(list.size == 1) { "Does not contain only one element" }
    return list.first()
}

fun <T> Iterable<Iterable<T>>.transpose(): List<List<T>> {
    val matrix = this.map(Iterable<T>::toList)
    val cols = matrix.first().size
    val transposed = Array(cols) { emptyList<T>().toMutableList() }
    for (row in this) {
        for ((j, element) in row.withIndex()) {
            transposed[j].add(element)
        }
    }
    return transposed.toList()
}

fun <T> Iterable<T>.zipWithNext(count: Int): List<List<T>> {
    return if (count == 1) {
        this.zipWithNext().map { pair -> pair.toList() }
    } else {
        this.zipWithNext(count - 1)
            .zip(this.drop(count))
            .map { (pair, single) -> pair.toList() + single }
    }
}

fun List<Int?>.joinToInt(): Int = this.joinToString("").toInt()

fun <K, V> Map<K, V>.swapKeysAndValues(): Map<V, K> = this.entries.associate { (key, value) -> value to key }

/** helps to find int overflow situations */
fun List<Int>.validateNoNegatives(): List<Int> =
    this.map { if (it < 0) throw IllegalArgumentException("Contains values <0 : $it") else it }

fun Sequence<Int>.validateNoNegatives(): Sequence<Int> =
    this.map { if (it < 0) throw IllegalArgumentException("Contains values <0 : $it") else it }

fun List<Int>.multiply(): Int = this.reduce { acc, i -> acc * i }

fun <T> Iterable<T>.eachCount(): Map<T, Int> = this.groupingBy { it }.eachCount()
