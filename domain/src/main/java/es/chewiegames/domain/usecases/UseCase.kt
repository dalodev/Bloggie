package es.chewiegames.domain.usecases

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.suspendCoroutine

abstract class UseCase<out T, in Params> {

    fun executeAsync(scope: CoroutineScope,
                     params: Params,
                     onResult: (T) -> Unit,
                     onError: (Throwable) -> Unit) {
        scope.launch {
            try {
                runInBackground(params).collect {
                    scope.launch(Dispatchers.Main){
                        onResult(it)
                    }
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected abstract fun runInBackground(params: Params): Flow<T>

    class None
}