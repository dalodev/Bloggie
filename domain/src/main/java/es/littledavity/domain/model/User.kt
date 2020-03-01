/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import es.littledavity.core.annotations.OpenForTesting

/**
 * Model view to display on the screen.
 *
 * @param id user identifier
 * @param email user email
 * @param name user name
 * @param internetStatus save if the user internet status
 * @param loginStatus save if user was logged or not
 * @param avatar user avatar image
 */
@OpenForTesting
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val internetStatus: Int = -1,
    val loginStatus: Int = -1,
    val avatar: String = ""
)
