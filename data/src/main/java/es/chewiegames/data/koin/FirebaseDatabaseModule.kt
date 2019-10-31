package es.chewiegames.data.koin

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData

fun provideUser(): UserData {
    return UserData()
}

fun providePost(): PostData {
    return PostData()
}

fun providefeedPosts(): ArrayList<PostData> {
    return arrayListOf()
}

fun provideLikedPostsByUser(): ArrayList<PostData> {
    return arrayListOf()
}

fun provideFirebaseDatabase(): FirebaseDatabase {
    FirebaseDatabase.getInstance().setPersistenceEnabled(true)//enable offline conection
    return FirebaseDatabase.getInstance()
}

fun getUserDatabaseReference(): DatabaseReference {
    val databaseReference = FirebaseDatabase.getInstance().getReference("users")
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun providePostByUserReference(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("postsByUser").child(userData.id!!)
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun provideAllPosts(firebaseDatabase: FirebaseDatabase): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("posts")
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun provideLikedPostByUser(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
    val databaseReference = firebaseDatabase.getReference("likedPostsByUser").child(userData.id!!)
    databaseReference.keepSynced(true)
    return databaseReference
}