/*
 * Copyright 2019 littledavity
 */
package es.littledavity.domain.koin

import es.littledavity.domain.usecases.comments.StoreCommentUseCase
import es.littledavity.domain.usecases.comments.StoreReplyUseCase
import es.littledavity.domain.usecases.feedpost.GetFeedPostUseCase
import es.littledavity.domain.usecases.feedpost.GetLikedPostsByUserUseCase
import es.littledavity.domain.usecases.feedpost.SubscribeFeedPostsUseCase
import es.littledavity.domain.usecases.feedpost.UpdateLikedPostUseCase
import es.littledavity.domain.usecases.newpost.StoreNewPostUseCase
import es.littledavity.domain.usecases.post.UpdatePostUseCase
import es.littledavity.domain.usecases.user.CheckUserLoginUseCase
import es.littledavity.domain.usecases.user.RegisterUserUseCase
import org.koin.dsl.module.module

val domainModule = module {

    /**
     * Use cases
     */
    single { CheckUserLoginUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { GetFeedPostUseCase(repository = get()) }
    single { GetLikedPostsByUserUseCase(repository = get()) }
    single { UpdateLikedPostUseCase(repository = get()) }
    single { StoreNewPostUseCase(repository = get()) }
    single { SubscribeFeedPostsUseCase(repository = get()) }
    single { UpdatePostUseCase(repository = get()) }
    single { StoreCommentUseCase(repository = get()) }
    single { StoreReplyUseCase(repository = get()) }
}
