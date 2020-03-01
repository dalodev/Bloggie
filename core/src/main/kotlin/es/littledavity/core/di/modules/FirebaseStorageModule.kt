/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.di.modules

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class FirebaseStorageModule {

    /**
     * Create a provider method for [FirebaseStorage].
     *
     * @return Instance of firebase storage
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    /**
     * Create a provider method for reference of [StorageReference].
     *
     * @return Instance of firebase storage reference
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideStorageReference(firebaseStorage: FirebaseStorage) = firebaseStorage.reference
}
