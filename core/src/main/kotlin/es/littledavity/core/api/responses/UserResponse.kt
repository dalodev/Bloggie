/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.responses

import com.google.firebase.database.IgnoreExtraProperties
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
@IgnoreExtraProperties
@OpenForTesting
data class UserResponse(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val internetStatus: Int = -1,
    val loginStatus: Int = -1,
    val avatar: String = ""
)
