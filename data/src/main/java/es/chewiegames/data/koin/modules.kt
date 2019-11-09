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

    single<UserRepository> { UserDataRepository(mUserData= get(), mDatabaseUsers = get(allPostsDatabaseReference) ) }
    single<PostRepository> {
        PostDataRepository(
                mDatabasePostByUser = get(postsByUserDatabaseReference),
                mDatabaseAllPost =get(allPostsDatabaseReference),
                mDatabaseLikedPostByUser = get(likedPostsByUserDatabaseReference),
                likedPosts = get(likedPostsByUser),
                posts = get(feedPosts),
                mUserData = get(user)
        )
    }
    single<NewPostRepository> {
        NewPostDataRepository(
                mDatabasePosts = get(allPostsDatabaseReference),
                mDatabasePostByUser = get(postsByUserDatabaseReference),
                mStorageReference = get(),
                mUserData = get(user)
        )
    }

    single { provideUser() }
    single(feedPosts) { provideFeedPosts() }
    single(likedPostsByUser) { provideLikedPostsByUser() }
    //Firebase database
    single { provideFirebaseDatabase() }
    factory(userDatabaseReference) { getUserDatabaseReference(firebaseDatabase = get()) }
    factory(postsByUserDatabaseReference) { providePostByUserReference(firebaseDatabase = get(), userData =  get()) }
    factory(allPostsDatabaseReference) { provideAllPostsReference(firebaseDatabase = get()) }
    factory(likedPostsByUserDatabaseReference) { provideLikedPostByUserReference(firebaseDatabase = get(),userData =  get(user)) }

    //Firebase storage
    factory { provideFirebaseStorage() }

    //firebase auth
    factory { provideFirebaseAuth() }
}

fun provideResources(context: Context): Resources = context.resources

const val likedPostsByUser = "likedPostsByUser"
const val feedPosts = "feedPost"
const val user = "user"

const val userDatabaseReference = "userDatabaseReference"
const val postsByUserDatabaseReference = "postsByUserDatabaseReference"
const val allPostsDatabaseReference = "allPostsDatabaseReference"
const val likedPostsByUserDatabaseReference = "likedPostsByUserDatabaseReference"