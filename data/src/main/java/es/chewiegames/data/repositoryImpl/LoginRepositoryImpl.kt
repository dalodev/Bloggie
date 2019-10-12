package es.chewiegames.data.repositoryImpl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import es.chewiegames.data.model.User
import es.chewiegames.data.repository.LoginRespository
import es.chewiegames.data.utils.LOGIN_IN
import es.chewiegames.data.utils.LOGIN_OUT

class LoginRepositoryImpl(var mUser : User, var mDatabaseUsers: DatabaseReference) : LoginRespository {

    private val TAG = this.javaClass.name

    override fun checkUserLogin(listener: LoginRespository.OnLaunchResult) {
        if(FirebaseAuth.getInstance().currentUser !=null){
            mDatabaseUsers.child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val u : User? = dataSnapshot.getValue(User::class.java)
                    if(u !=null){
                        mUser.id = u.id
                        mUser.userEmail = u.userEmail
                        mUser.userName = u.userName
                        mUser.loginStatus = u.loginStatus
                        mUser.avatar = u.avatar
                        when (mUser.loginStatus){
                            LOGIN_IN -> {
                                listener.userLogged()
                            }
                            LOGIN_OUT ->{
                                listener.userNotLogged()
                            }
                        }
                    }else{
                        listener.userNotLogged()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, databaseError.message)
                    listener.userNotLogged()
                }
            })
        }else{
            listener.userNotLogged()
        }
    }

    override fun storeUserInDatabase(user: FirebaseUser, listener: LoginRespository.OnLoginFinishedListener) {
        mUser.id = user.uid
        mUser.userEmail = user.email.toString()
        mUser.userName = user.displayName.toString()
        mUser.loginStatus = LOGIN_IN
        mUser.avatar = user.photoUrl.toString()
        mDatabaseUsers.child(user.uid).setValue(mUser)
        listener.onSuccess()
    }
}