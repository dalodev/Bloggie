package es.littledavity.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

abstract class UseCase<out T, in Params> {

    lateinit var job: Job
    fun executeAsync(
        scope: CoroutineScope,
        params: Params,
        onResult: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onStart: () -> Unit,
        onCompletion: () -> Unit
    ) {
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
