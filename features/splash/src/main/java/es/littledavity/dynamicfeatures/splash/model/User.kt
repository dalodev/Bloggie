/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.splash.model

data class User(
    var id: String? = null,
    var email: String? = null,
    var name: String? = null,
    var internetStatus: Int = -1,
    var loginStatus: Int = -1,
    var avatar: String? = null
)
