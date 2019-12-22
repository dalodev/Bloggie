/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.model

import java.io.Serializable

data class UserData(
    var id: String? = null,
    var userEmail: String? = null,
    var userName: String? = null,
    var internetStatus: Int = -1,
    var loginStatus: Int = -1,
    var avatar: String? = null
) : Serializable
