package es.chewiegames.data.koin

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.chewiegames.data.model.Post
import es.chewiegames.data.model.User

fun provideUser(): User {
    return User()
}

fun providePost(): Post {
    return Post()
}

fun providefeedPosts(): ArrayList<Post> {
    return arrayListOf()
}

fun provideLikedPostsByUser(): ArrayList<Post> {
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

fun providePostByUserReference(firebaseDatabase: FirebaseDatabase, user: User): DatabaseReference {
    var databaseReference = firebaseDatabase.getReference("postsByUser").child(user.id!!)
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun provideAllPosts(firebaseDatabase: FirebaseDatabase): DatabaseReference {
    var databaseReference = firebaseDatabase.getReference("posts")
    databaseReference.keepSynced(true)//keep data synced
    return databaseReference
}

fun provideLikedPostByUser(firebaseDatabase: FirebaseDatabase, user: User): DatabaseReference {
    var databaseReference = firebaseDatabase.getReference("likedPostsByUser").child(user.id!!)
    databaseReference.keepSynced(true)
    return databaseReference
}