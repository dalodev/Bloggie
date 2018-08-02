package es.chewiegames.bloggie.model

import java.io.Serializable

data class User(
        var id: String? = null,
        var userEmail: String? = null,
        var userName: String? = null,
        var internetStatus: Int = -1,
        var loginStatus: Int = -1,
        var avatar: String? = null) : Serializable