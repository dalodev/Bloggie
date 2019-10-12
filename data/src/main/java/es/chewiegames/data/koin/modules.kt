package es.chewiegames.data.koin

import android.content.Context
import android.content.res.Resources
import es.chewiegames.data.repository.HomeRepository
import es.chewiegames.data.repository.LoginRespository
import es.chewiegames.data.repositoryImpl.HomeRepositoryImpl
import es.chewiegames.data.repositoryImpl.LoginRepositoryImpl
import org.koin.dsl.module.module

val dataModule = module {

    single<LoginRespository>{LoginRepositoryImpl(get(),get())}
    single<HomeRepository>{HomeRepositoryImpl(
            providePostByUserReference(get(), get()),
            provideAllPosts(get()),
            provideLikedPostByUser(get(), get()),
            provideLikedPostsByUser(),
            providefeedPosts(),
            provideUser()
    )}

    single { provideUser() }
    single { providePost() }
    single { providefeedPosts() }
    factory { provideFirebaseDatabase() }
    factory { getUserDatabaseReference() }
    factory { providePostByUserReference(get(), get()) }
    factory { provideAllPosts(get()) }
    factory { provideLikedPostByUser(get(), get()) }
    factory { provideLikedPostsByUser() }
}

fun provideResources(context: Context): Resources = context.resources
