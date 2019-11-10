package es.chewiegames.domain.usecases

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import java.lang.Exception

abstract class SubscribeUseCase<out T, in Params> {

    lateinit var job: Job

    fun subscribe(scope: CoroutineScope,
                  callback: Params,
                  onResult: (T) -> Unit,
                  onError: (Throwable) -> Unit) {
        try {
            val backgroundJob = scope.async { subscribe(callback) }
            scope.launch { onResult(backgroundJob.await()) }
        } catch (e: Exception) {
            onError(e)
        }

    }

    protected abstract fun subscribe(callback: Params) : T

    protected abstract fun setListener(listener: Params)

    fun cancelJob() = job.cancel()

    class None
}