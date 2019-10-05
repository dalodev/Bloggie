package es.chewiegames.domain.koin

import es.chewiegames.domain.usecases.LoginUseCase
import org.koin.dsl.module.module

val domainModule = module {

    /**
     * Use cases
     */
    single { LoginUseCase.createInstance(loginRepository = get()) }
}