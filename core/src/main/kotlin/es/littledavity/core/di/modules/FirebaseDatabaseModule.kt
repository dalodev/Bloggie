/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import es.littledavity.core.api.repositories.UserRepository
import es.littledavity.core.api.responses.UserResponse
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class FirebaseDatabaseModule {

    /**
     * Create a provider method for [FirebaseDatabase].
     *
     * @return Instance of firebase database
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance().apply {
        setPersistenceEnabled(false) // enable offline conection
    }

    /**
     * Create a provider method for user [DatabaseReference].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @Singleton
    @Provides
    @Named("users")
    fun provideUserDatabase(
        firebaseDatabase: FirebaseDatabase,
        user: UserResponse
    ): DatabaseReference {
        val userDatabase = firebaseDatabase.getReference("users").child(user.id)
        userDatabase.keepSynced(false)
        return userDatabase
    }

    /**
     * Create a provider method for user [UserRepository].
     *
     * @return Instance of user database reference
     * @see Provides
     */
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideUserRepository(
        userDatabase: DatabaseReference
    ) = UserRepository(userDatabase)
}
