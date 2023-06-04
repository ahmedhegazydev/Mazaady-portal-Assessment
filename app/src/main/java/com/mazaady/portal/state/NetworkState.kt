package com.mazaady.portal.state

/* The above class is a sealed class in Kotlin that represents different states of network requests,
including empty, loading, success with data, and error with a message. */
sealed class NetworkState<T>(val data: T? = null, val message: String? = null) {
    /* The class `Empty` is a generic subclass of `NetworkState` in Kotlin. */
    class Empty<T> : NetworkState<T>()
    /* The class Loading is a subclass of NetworkState that represents a loading state for a generic
    type T. */
    class Loading<T> : NetworkState<T>()
    /* The class represents a successful network state with data of type T. */
    class Success<T>(data: T) : NetworkState<T>(data, null)
    @Suppress("UNUSED_PARAMETER")
    /* The class Error represents a network state with an error message and optional data. */
    class Error<T>(message: String, data: T? = null) : NetworkState<T>(null, message)
}