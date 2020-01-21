/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.repositories

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import es.littledavity.core.api.responses.UserResponse
import es.littledavity.core.exceptions.UserException
import es.littledavity.core.utils.LOGIN_IN
import es.littledavity.core.utils.LOGIN_OUT
import es.littledavity.core.utils.ONLINE
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Repository module for handling user data from firebase.
 */
@ExperimentalCoroutinesApi
class UserRepository @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    @Named("users")
    internal val mDatabaseUsers: DatabaseReference
) {

    fun getCurrentUser() = run {
        FirebaseAuth.getInstance().currentUser?.let {
            mDatabaseUsers.child(it.uid)
        }
    }

    suspend fun storeUser(user: FirebaseUser): Flow<UserResponse> = callbackFlow {
        val newUser = UserResponse(
            user.uid,
            user.email.toString(),
            user.displayName.toString(),
            LOGIN_IN,
            ONLINE,
            user.photoUrl.toString()
        )
        mDatabaseUsers.child(user.uid).setValue(newUser)
        offer(newUser)
        channel.close()
        awaitClose()
    }

    /**
     * Check if firebase user is logged or not
     */
    suspend fun checkUserLogin() = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                close(UserException("User not logged"))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: UserResponse? = dataSnapshot.getValue(UserResponse::class.java)
                if (user != null) {
                    when (user.loginStatus) {
                        LOGIN_IN -> offer(true)
                        LOGIN_OUT -> offer(false)
                    }
                } else {
                    offer(false)
                }
            }
        }
        FirebaseAuth.getInstance().currentUser?.let {
            mDatabaseUsers.child(it.uid).addListenerForSingleValueEvent(callback)
            awaitClose { mDatabaseUsers.child(it.uid).removeEventListener(callback) }
        } ?: run {
            offer(false)
            awaitClose()
        }
    }
}
}