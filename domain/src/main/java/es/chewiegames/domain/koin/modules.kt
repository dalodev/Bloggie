package es.chewiegames.domain.koin

import es.chewiegames.domain.usecases.feedpost.FeedPostUseCase
import es.chewiegames.domain.usecases.feedpost.UpdateLikedPostUseCase
import es.chewiegames.domain.usecases.newpost.StoreNewPostUseCase
import es.chewiegames.domain.usecases.user.CheckUserLoginUseCase
import es.chewiegames.domain.usecases.user.RegisterUserUseCase
import org.koin.dsl.module.module

val domainModule = module {

    /**
     * Use cases
     */
    single { CheckUserLoginUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { FeedPostUseCase(repository = get()) }
    single { UpdateLikedPostUseCase(repository = get()) }
    single { StoreNewPostUseCase(repository = get()) }
}