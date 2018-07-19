package es.chewiegames.bloggie.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.google.firebase.storage.StorageReference
import com.google.firebase.database.FirebaseDatabase

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference {
        return firebaseStorage.reference
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)//enable offline conection
        return FirebaseDatabase.getInstance()
    }
}