package es.chewiegames.bloggie.koin

import es.chewiegames.bloggie.viewmodel.HomeViewModel
import es.chewiegames.bloggie.viewmodel.LoginViewModel
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

val appModule = module {

    viewModel { LoginViewModel(checkUserLoginUseCase = get(), registerUserUseCase = get()) }
    viewModel { HomeViewModel(getFeedPostUseCase = get(), getLikedPostsByUserUseCase = get(), updateLikedPostUseCase = get(), subscribeFeedPostsUseCase = get()) }
    viewModel { NewPostViewModel(context = get(), storeNewPostUseCase = get()) }

    // Coroutines
    factory<CoroutineContext> { Dispatchers.Main }
}