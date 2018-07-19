package es.chewiegames.bloggie.model

import java.io.Serializable

data class User(
        var id: String = "",
        var userEmail: String = "",
        var userName: String = "",
        var internetStatus: Int = -1,
        var loginStatus: Int = -1,
        var avatar: String = "") : Serializable