package com.davega.data.auth.utils

import com.davega.data.auth.local.data_source.AuthLocalDataSource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionTimer @Inject constructor(
    private val local: AuthLocalDataSource
) {

    private val minutes = 2L

    private val eventChannel = Channel<Session>(Channel.BUFFERED)

    operator fun invoke(): Flow<Session> {
        return eventChannel.receiveAsFlow()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun login() = GlobalScope.launch {
        delay(minutes * 60000)
        local.removeAuth()
        eventChannel.send(Session())
    }

    class Session

}