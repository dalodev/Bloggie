/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.koin

import android.content.Context
import android.content.res.Resources
import es.littledavity.data.repository.CommentsRepository
import es.littledavity.data.repository.NewPostRepository
import es.littledavity.data.repository.PostRepository
import es.littledavity.data.repository.UserRepository
import es.littledavity.data.repositoryImpl.CommentsDataRepository
import es.littledavity.data.repositoryImpl.NewPostDataRepository
import es.littledavity.data.repositoryImpl.PostDataRepository
import es.littledavity.data.repositoryImpl.UserDataRepository
import org.koin.dsl.module.module

val dataModule = module {

    single<UserRepository> { UserDataRepository(mUserData = get(), mDatabaseUsers = get(userDatabaseReference)) }
    single<PostRepository> {
        PostDataRepository(
                mDatabasePostByUser = get(postsByUserDatabaseReference),
                mDatabaseAllPost = get(allPostsDatabaseReference),
                mDatabaseLikedPostByUser = get(likedPostsByUserDatabaseReference),
                mUserData = get()
        )
    }
    single<NewPostRepository> {
        NewPostDataRepository(
                mDatabasePosts = get(allPostsDatabaseReference),
                mDatabasePostByUser = get(postsByUserDatabaseReference),
                mStorageReference = get(),
                mUserData = get()
        )
    }
    single<CommentsRepository> {
        CommentsDataRepository(
                mDatabasePostByUser = get(postsByUserDatabaseReference),
                mDatabaseAllPost = get(allPostsDatabaseReference),
                mUserData = get()
        )
    }

    single { provideUser() }
    single(feedPosts) { provideFeedPosts() }
    single(likedPostsByUser) { provideLikedPostsByUser() }
    // Firebase database
    single { provideFirebaseDatabase() }
    factory(userDatabaseReference) { getUserDatabaseReference(firebaseDatabase = get()) }
    factory(postsByUserDatabaseReference) { providePostByUserReference(firebaseDatabase = get(), userData = get()) }
    factory(allPostsDatabaseReference) { provideAllPostsReference(firebaseDatabase = get()) }
    factory(likedPostsByUserDatabaseReference) { provideLikedPostByUserReference(firebaseDatabase = get(), userData = get()) }

    // Firebase storage
    factory { provideFirebaseStorage() }
    factory { provideStorageReference(firebaseStorage = get()) }

    // firebase auth
    factory { provideFirebaseAuth() }
}

fun provideResources(context: Context): Resources = context.resources

const val likedPostsByUser = "likedPostsByUser"
const val feedPosts = "feedPost"

const val userDatabaseReference = "userDatabaseReference"
const val postsByUserDatabaseReference = "postsByUserDatabaseReference"
const val allPostsDatabaseReference = "allPostsDatabaseReference"
const val likedPostsByUserDatabaseReference = "likedPostsByUserDatabaseReference"
