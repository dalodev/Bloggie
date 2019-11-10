package es.chewiegames.domain.usecases

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import java.lang.Exception

abstract class UseCase<out T, in Params> {

    lateinit var job: Job
    fun executeAsync(scope: CoroutineScope,
                     params: Params,
                     onResult: (T) -> Unit,
                     onError: (Throwable) -> Unit,
                     onStart: () -> Unit) {
        scope.launch {
            try {
                runInBackground(params)
                        .onStart { onStart() }
                        .collect {
                            job = scope.launch(Dispatchers.Main) {
                                onResult(it)
                            }
                        }
            } catch (e: Exception) { onError(e) }
        }
    }

    protected abstract fun runInBackground(params: Params): Flow<T>

    fun cancelJob() = job.cancel()

    class None
}