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

class UserDataRepository(var mUserData : UserData, var mDatabaseUsers: DatabaseReference) : UserRepository {

    override fun storeUserInDatabase(user: FirebaseUser) {
        mUserData.id = user.uid
        mUserData.userEmail = user.email.toString()
        mUserData.userName = user.displayName.toString()
        mUserData.loginStatus = LOGIN_IN
        mUserData.avatar = user.photoUrl.toString()
        mDatabaseUsers.child(user.uid).setValue(mUserData)
    }

    override fun checkUserLogin() : Boolean {
        var userLogged = false
        if(FirebaseAuth.getInstance().currentUser !=null){
            mDatabaseUsers.child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val u : UserData? = dataSnapshot.getValue(UserData::class.java)
                    if(u !=null){
                        mUserData.id = u.id
                        mUserData.userEmail = u.userEmail
                        mUserData.userName = u.userName
                        mUserData.loginStatus = u.loginStatus
                        mUserData.avatar = u.avatar
                        when (mUserData.loginStatus){
                            LOGIN_IN -> {
                                 userLogged = true
                            }
                            LOGIN_OUT ->{
                                throw UserException("User not logged")
                            }
                        }
                    }else{
                        throw UserException("User not logged")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    throw UserException("User not logged")
                }
            })
        }else{
            throw UserException("User not logged")
        }
        return userLogged
    }
}