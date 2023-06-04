package com.mazaady.portal.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

/* The `data class CoroutinesDispatcherProvider` is defining a class that holds three properties of
type `CoroutineDispatcher`: `main`, `computation`, and `io`. These properties represent the
dispatchers that will be used for executing coroutines on different threads. The `data` keyword is
used to automatically generate some standard methods such as `equals()`, `hashCode()`, and
`toString()`. This class can be used to provide different dispatchers for different contexts in a
Kotlin application. */
data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {

    @Inject
    /* `constructor() : this(Main, Default, IO)` is a secondary constructor that is calling the primary
    constructor of the `CoroutinesDispatcherProvider` class with three arguments: `Main`, `Default`,
    and `IO`. These arguments are default values for the `main`, `computation`, and `io` properties
    respectively. This secondary constructor is annotated with `@Inject`, which means that it can be
    used by a dependency injection framework to create instances of the
    `CoroutinesDispatcherProvider` class. */
    constructor() : this(Main, Default, IO)
}