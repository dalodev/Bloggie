/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.mapper

import com.google.firebase.auth.FirebaseUser
import es.littledavity.core.api.responses.UserResponse
import es.littledavity.core.utils.LOGIN_IN
import es.littledavity.core.utils.ONLINE

class UserResponseMapper : Mapper<FirebaseUser, UserResponse> {

    /**
     * Transform firebase response to [UserResponse].
     *
     * @param from User firebase response.
     * @return List of parsed characters items.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: FirebaseUser) = UserResponse(
        id = from.uid,
        email = from.email.toString(),
        name = from.displayName.toString(),
        internetStatus = ONLINE,
        loginStatus = LOGIN_IN,
        avatar = from.photoUrl.toString()
    )
}
