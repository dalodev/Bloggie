package es.chewiegames.bloggie.di.module

import dagger.Module
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Provides
import javax.inject.Named
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.chewiegames.domain.model.Post
import es.chewiegames.data.model.UserData
import javax.inject.Singleton

@Module
class FirebaseDatabaseModule constructor(var posts : ArrayList<Post> = ArrayList(), var likedPostsByUser : ArrayList<Post> = ArrayList()){

    @Provides
    fun provideUsersReference(firebaseDatabase: FirebaseDatabase): DatabaseReference {
        var databaseReference = firebaseDatabase.getReference("users")
        databaseReference.keepSynced(true)//keep data synced
        return databaseReference
    }

    @Provides
    @Named("post by user")
    fun providePostByUserReference(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
        var databaseReference = firebaseDatabase.getReference("postsByUser").child(userData.id!!)
        databaseReference.keepSynced(true)//keep data synced
        return databaseReference
    }

    @Provides
    @Named("all posts")
    fun provideAllPosts(firebaseDatabase: FirebaseDatabase): DatabaseReference {
        var databaseReference = firebaseDatabase.getReference("posts")
        databaseReference.keepSynced(true)//keep data synced
        return databaseReference
    }

    @Provides
    @Named("liked posts by user")
    fun provideLikedPostByUser(firebaseDatabase: FirebaseDatabase, userData: UserData): DatabaseReference {
        var databaseReference = firebaseDatabase.getReference("likedPostsByUser").child(userData.id!!)
        databaseReference.keepSynced(true)
        return databaseReference
    }

    @Provides
    @Singleton
    fun provideFirebaseUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }


    @Provides
    @Singleton
    fun provideUser(): UserData {
        return UserData()
    }

    @Provides
    @Singleton
    fun providefeedPosts(): ArrayList<Post> {
        return posts
    }

    @Provides
    @Singleton
    @Named("liked posts")
    fun provideLikedPostsByUser(): ArrayList<Post> {
        return likedPostsByUser
    }

    @Provides
    fun providePost(): Post {
        return Post()
    }
}