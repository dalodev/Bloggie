package es.chewiegames.domain.usecases

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class UseCase<out T, in Params> {

    lateinit var job: Job
    fun executeAsync(scope: CoroutineScope,
                     params: Params,
                     onResult: (T) -> Unit,
                     onError: (Throwable) -> Unit,
                     onStart: () -> Unit,
                     onCompletion: () -> Unit) {
        job = runInBackground(params)
                .onStart { onStart() }
                .onEach { onResult(it) }
                .catch { onError(it) }
                .onCompletion { onCompletion() }
                .launchIn(scope)
    }

    protected abstract fun runInBackground(params: Params): Flow<T>

    fun cancelJob() = job.cancel()

    class None
}