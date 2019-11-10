package es.chewiegames.domain.model

import es.chewiegames.data.model.UserData
import java.io.Serializable

data class User(
        var id: String? = null,
        var userEmail: String? = null,
        var userName: String? = null,
        var internetStatus: Int = -1,
        var loginStatus: Int = -1,
        var avatar: String? = null) : Serializable

fun mapToUserData(user: User) = UserData(user.id, user.userEmail, user.userEmail, user.internetStatus, user.loginStatus, user.avatar)
fun mapToUser(user: UserData) = User(user.id, user.userEmail, user.userName, user.internetStatus, user.loginStatus, user.avatar)