package com.github.amrmsaraya.timer

import com.github.amrmsaraya.timer.Stopwatch.Companion.IDLE
import com.github.amrmsaraya.timer.Stopwatch.Companion.PAUSED
import com.github.amrmsaraya.timer.Stopwatch.Companion.RUNNING
import com.github.amrmsaraya.timer.Timer.Companion.IDLE
import com.github.amrmsaraya.timer.Timer.Companion.PAUSED
import com.github.amrmsaraya.timer.Timer.Companion.RUNNING
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow

/**
 *  Timer class is a simple class that provide all timer functionality,
 *  like [start], [pause], [reset] and [configure] the timer.
 *
 *  Also provide the status of the timer either it's [IDLE], [RUNNING] or [PAUSED]
 *
 *  Make sure to call [clear] to cancel coroutines when reach the end of
 *  lifecycle like Activity's onDestroyed() or ViewModel's onCleared()
 *
 *  @param scope the scope where all the work is done, default to CoroutineScope(Job()).
 *  You can pass any lifecycle scope like viewModelScope or lifecycleScope or a custom scope.
 */
class Timer(
    private val scope: CoroutineScope = CoroutineScope(Job())
) {
    companion object {
        /** The current status of timer is idle */
        const val IDLE = 0

        /** The current status of timer is running */
        const val RUNNING = 1

        /** The current status of timer is paused */
        const val PAUSED = 2
    }

    /** The channel responsible for sending actions to the [getTimer] flow */
    private val operationChannel = Channel<Operation>(Channel.UNLIMITED)

    /** The job which holds the actual timer coroutine */
    private var job: Job? = null

    /** The start time value as milliseconds */
    private var start = 0L

    /** The current timer value as milliseconds*/
    private var timer = 0L

    /** The configured delay between every [Time] emission*/
    private var delay = 10L

    /** The initial time to start counting down from*/
    var configuredTime = 1L
        private set

    /** The current status of stopwatch */
    var status = IDLE
        private set

    /**
     * Observe changes to timer
     * @param onFinish is lambda function which gets fired when the timer finish counting down
     * @return Flow of timer as [Time]
     */
    fun getTimer(onFinish: () -> Unit = {}) = flow {
        operationChannel.consumeAsFlow().collect {
            when (it) {
                Operation.START -> {
                    job?.cancel()
                    start = System.currentTimeMillis() + timer
                    job = scope.launch { startTimer(delay) }
                    status = RUNNING
                }
                Operation.PAUSE -> {
                    job?.cancel()
                    status = PAUSED
                }
                Operation.RESET -> {
                    job?.cancel()
                    timer = 0
                    status = IDLE
                }
                Operation.FINISH -> {
                    job?.cancel()
                    onFinish()
                }
                else -> Unit
            }
            emit(timer.toTime())
        }
    }

    /**
     * Starts an infinite loop that emit time within a delay of [delay]
     * @param delay the delay which actual timer emit the value
     */
    private suspend fun startTimer(delay: Long) = withContext(Dispatchers.IO) {
        while (start >= System.currentTimeMillis()) {
            timer = start - System.currentTimeMillis()
            operationChannel.send(Operation.EMIT)
            delay(delay)
        }
        operationChannel.send(Operation.FINISH)
    }

    /** Start the timer */
    suspend fun start() {
        operationChannel.send(Operation.START)
    }

    /** Pause the timer */
    suspend fun pause() {
        operationChannel.send(Operation.PAUSE)
    }

    /** Reset the timer */
    suspend fun reset() {
        operationChannel.send(Operation.RESET)
    }

    /** Configure the stopwatch, only works when status is IDLE
     * @param timeMillis the time to start counting down from in Milliseconds
     * @param delay the delay which timer emit time
     * */
    fun configure(timeMillis: Long, delay: Long = 10) {
        if (status == IDLE) {
            this.delay = delay
            configuredTime = timeMillis
            timer = configuredTime
        }
    }

    /** Cancel all coroutines */
    fun clear() {
        scope.cancel()
    }
}