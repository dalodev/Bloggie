/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.koin

import es.littledavity.bloggie.viewmodel.CommentsViewModel
import es.littledavity.bloggie.viewmodel.DetailPostViewModel
import es.littledavity.bloggie.viewmodel.HomeViewModel
import es.littledavity.bloggie.viewmodel.LoginViewModel
import es.littledavity.bloggie.viewmodel.NewPostViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { LoginViewModel(checkUserLoginUseCase = get(), registerUserUseCase = get()) }
    viewModel { HomeViewModel(getFeedPostUseCase = get(), getLikedPostsByUserUseCase = get(), updateLikedPostUseCase = get(), subscribeFeedPostsUseCase = get()) }
    viewModel { NewPostViewModel(context = get(), storeNewPostUseCase = get()) }
    viewModel { DetailPostViewModel(context = get(), updatePostUseCase = get()) }
    viewModel { CommentsViewModel(storeCommentUseCase = get(), storeReplyUseCase = get()) }

    // Coroutines
    factory<CoroutineContext> { Dispatchers.Main }
}
