/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
}
