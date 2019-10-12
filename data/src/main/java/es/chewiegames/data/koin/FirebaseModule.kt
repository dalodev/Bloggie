package es.chewiegames.data.koin

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

fun provideFirebaseStorage(): FirebaseStorage {
    return FirebaseStorage.getInstance()
}

fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference {
    return firebaseStorage.reference
}

fun provideFirebaseAuth() : FirebaseAuth {
    return FirebaseAuth.getInstance()
}