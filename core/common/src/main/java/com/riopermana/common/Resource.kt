package com.riopermana.common

/**
 * A class wrapper that encapsulates different states of a resource.
 * This class is commonly used for handling state management in asynchronous operations,
 * such as network requests or data fetching.
 * It defines three possible states:
 *
 * [Success]: Represents a successful state with associated data.
 * [Error]: Represents an error state with/without any associated data.
 * [Loading]: Represents a loading state, indicating that the resource is currently being fetched or processed.
 */
sealed interface Resource<out T> {
    /**
     * Represents a successful state with associated [data].
     */
    data class Success<T>(val data: T) : Resource<T>

    /**
     *  Represents an error state with/without any associated [data].
     */
    data class Error<T>(
        val message: String,
        val data: T? = null,
        val throwable: Throwable? = null,
    ) : Resource<T>

    /**
     * Represents a loading state, indicating that the resource is currently being fetched or processed.
     */
    object Loading : Resource<Nothing>
}

/**
 * Syntactic sugar call to handle `when` expression on [Resource] class.
 */
inline fun <reified T> Resource<T>.doWhen(
    success: (T) -> Unit = {},
    error: (String, T?, Throwable?) -> Unit = { _, _, _ -> },
    loading: () -> Unit = {},
) {
    when (this) {
        is Resource.Success<T> -> success(this.data)
        is Resource.Error<T> -> error(message, data, throwable)
        is Resource.Loading -> loading()
    }
}

val <T>Resource<T>.isLoading: Boolean
    get() = this is Resource.Loading

val <T>Resource<T>.isSuccess: Boolean
    get() = this is Resource.Success<*>