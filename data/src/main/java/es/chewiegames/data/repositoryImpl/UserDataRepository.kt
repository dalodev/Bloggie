package es.chewiegames.data.repositoryImpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import es.chewiegames.data.exceptions.UserException
import es.chewiegames.data.model.UserData
import es.chewiegames.data.repository.UserRepository
import es.chewiegames.data.utils.LOGIN_IN
import es.chewiegames.data.utils.LOGIN_OUT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class UserDataRepository(var mUserData: UserData, var mDatabaseUsers: DatabaseReference) : UserRepository {

    override fun storeUserInDatabase(user: FirebaseUser): Flow<UserData> = callbackFlow {
        mUserData.id = user.uid
        mUserData.userEmail = user.email.toString()
        mUserData.userName = user.displayName.toString()
        mUserData.loginStatus = LOGIN_IN
        mUserData.avatar = user.photoUrl.toString()
        mDatabaseUsers.child(user.uid).setValue(mUserData)
        offer(mUserData)
        channel.close()
        awaitClose()
    }

    override fun checkUserLogin(): Flow<Boolean> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                close(UserException("User not logged"))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val u: UserData? = dataSnapshot.getValue(UserData::class.java)
                if (u != null) {
                    mUserData.id = u.id
                    mUserData.userEmail = u.userEmail
                    mUserData.userName = u.userName
                    mUserData.loginStatus = u.loginStatus
                    mUserData.avatar = u.avatar
                    when (mUserData.loginStatus) {
                        LOGIN_IN -> {
                            offer(true)
                        }
                        LOGIN_OUT -> {
                            offer(false)
                        }
                    }
                } else {
                    offer(false)
                }
            }
        }
        if (FirebaseAuth.getInstance().currentUser != null) {
            mDatabaseUsers.child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(callback)
        } else {
            offer(false)
        }
        awaitClose { mDatabaseUsers.child(FirebaseAuth.getInstance().currentUser!!.uid).removeEventListener(callback) }
    }
}
