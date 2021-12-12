package at.cnoize.contest.util

operator fun <T> ((T) -> Boolean).not() = { e: T -> !this(e) }
