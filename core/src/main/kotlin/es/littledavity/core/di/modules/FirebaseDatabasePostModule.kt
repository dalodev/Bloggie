/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import es.littledavity.core.api.repositories.PostRepository
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class FirebaseDatabasePostModule {

    /**
     * Create a provider method for all posts [DatabaseReference].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @Singleton
    @Provides
    @Named("posts")
    fun providePostsDatabase(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        val userDatabase = firebaseDatabase.getReference("posts")
        userDatabase.keepSynced(false)
        return userDatabase
    }

    /**
     * Create a provider method for posts by user [DatabaseReference].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @Singleton
    @Provides
    @Named("postsByUser")
    fun providePostByUserDatabase(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        val userDatabase = firebaseDatabase
            .getReference("postsByUser")
            .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
        userDatabase.keepSynced(false)
        return userDatabase
    }

    /**
     * Create a provider method for liked posts by user [DatabaseReference].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @Singleton
    @Provides
    @Named("likedPostsByUser")
    fun provideLikedPostByUserDatabase(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        val userDatabase = firebaseDatabase
            .getReference("likedPostsByUser")
            .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
        userDatabase.keepSynced(false)
        return userDatabase
    }

    /**
     * Create a provider method for user [PostRepository].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun providePostRepository(
        mDatabasePosts: DatabaseReference,
        mDatabasePostByUser: DatabaseReference,
        mDatabaseLikedPostByUser: DatabaseReference
    ) = PostRepository(mDatabasePosts, mDatabasePostByUser, mDatabaseLikedPostByUser)
}
