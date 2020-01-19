/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.responses

import es.littledavity.core.annotations.OpenForTesting

/**
 * User Firebase database response item.
 *
 * @param id The unique ID of the user.
 * @param email The email of the user.
 * @param name The name of the user.
 * @param internetStatus if user has internet or not.
 * @param loginStatus if user has been logged or not.
 * @param avatar user image.
 */
@OpenForTesting
data class UserResponse(
    var id: String? = "",
    var email: String? = null,
    var name: String? = null,
    var internetStatus: Int = -1,
    var loginStatus: Int = -1,
    var avatar: String? = null
)
