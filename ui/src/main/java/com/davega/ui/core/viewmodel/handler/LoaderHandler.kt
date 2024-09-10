package com.davega.ui.core.viewmodel.handler

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

interface LoaderHandler {

    var isLoading: Boolean

    suspend fun onLoading(listener: (isLoading: Boolean) -> Unit)
}

class DefaultLoaderHandler: LoaderHandler {

    private val _loader = MutableStateFlow(false)

    override var isLoading: Boolean
        get() = _loader.value
        set(value) {
            _loader.tryEmit(value)
        }

    override suspend fun onLoading(listener: (isLoading: Boolean) -> Unit) {
        _loader.collectLatest {
            listener(it)
        }
    }
}

fun loaderHandler(): LoaderHandler {
    return DefaultLoaderHandler()
}