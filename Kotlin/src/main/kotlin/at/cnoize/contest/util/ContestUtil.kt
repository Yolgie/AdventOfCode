@file:Suppress("unused")

package at.cnoize.contest.util

import kotlin.math.roundToInt

operator fun <T> ((T) -> Boolean).not() = { e: T -> !this(e) }

fun Int.stepTowards(target: Int, stepSize: Int = 1): Int {
    return when {
        this > target -> this-stepSize
        this < target -> this+stepSize
        else -> target
    }
}

fun Int.additorial() : Int = (this*(this+1))/2

fun Int.divBy2RoundUp(): Int = (this/2.0).roundToInt()
