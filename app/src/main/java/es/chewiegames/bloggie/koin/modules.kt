package es.chewiegames.bloggie.koin

import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.bloggie.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

val appModule = module {

    viewModel { LoginViewModel(loginUseCase = get()) }
    viewModel { HomeViewModel(activity = get(), homeUseCase = get()) }

    // Coroutines
    factory<CoroutineContext> { Dispatchers.Main }
}