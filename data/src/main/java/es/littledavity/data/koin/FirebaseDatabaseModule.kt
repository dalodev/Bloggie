package es.littledavity.data.koin

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.littledavity.data.model.UserData

fun provideFirebaseDatabase(): FirebaseDatabase {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    firebaseDatabase.setPersistenceEnabled(true) // enable offline conection
    return firebaseDatabase
}

fun getUserDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("users")
    databaseReference.keepSynced(true) // keep data synced
    return databaseReference
}

fun providePostByUserReference(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("postsByUser").child(userData.id!!)
    databaseReference.keepSynced(true) // keep data synced
    return databaseReference
}

fun provideAllPostsReference(firebaseDatabase: FirebaseDatabase): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("posts")
    databaseReference.keepSynced(true) // keep data synced
    return databaseReference
}

fun provideLikedPostByUserReference(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("likedPostsByUser").child(userData.id!!)
    databaseReference.keepSynced(true)
    return databaseReference
}
