package com.riopermana.common.helper

/**
 * Helper class to parse resource Id into appropriate data type of [T].
 */
interface ResourcesHelper<out T> {
    fun fromResId(resId: Int) : T
}