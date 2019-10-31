package es.chewiegames.data.koin

import android.content.Context
import android.content.res.Resources
import es.chewiegames.data.repository.NewPostRepository
import es.chewiegames.data.repository.PostRepository
import es.chewiegames.data.repository.UserRepository
import es.chewiegames.data.repositoryImpl.NewPostDataRepository
import es.chewiegames.data.repositoryImpl.PostDataRepository
import es.chewiegames.data.repositoryImpl.UserDataRepository
import org.koin.dsl.module.module

val dataModule = module {

    single<UserRepository> { UserDataRepository(get(), getUserDatabaseReference()) }
    single<PostRepository> {
        PostDataRepository(
                providePostByUserReference(get(), get()),
                provideAllPosts(get()),
                provideLikedPostByUser(get(), get()),
                provideLikedPostsByUser(),
                providefeedPosts(),
                provideUser()
        )
    }
    single<NewPostRepository> {
        NewPostDataRepository(
                providePost(),
                provideAllPosts(get()),
                providePostByUserReference(get(), get()),
                provideStorageReference(get()),
                provideUser()
        )
    }

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
