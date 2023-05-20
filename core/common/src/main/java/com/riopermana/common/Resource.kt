package com.riopermana.common

import androidx.annotation.StringRes

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(
        @StringRes
        val messageRes: Int,
        val data: T?,
        val throwable: Throwable,
    ) : Resource<T>

    object Loading : Resource<Nothing>
}