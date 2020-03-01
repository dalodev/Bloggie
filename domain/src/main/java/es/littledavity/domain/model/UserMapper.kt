/*
 * Copyright 2020 littledavity
 */
package es.littledavity.domain.model

import es.littledavity.core.api.responses.UserResponse
import es.littledavity.core.mapper.Mapper

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
class UserMapper : Mapper<UserResponse, User> {

    /**
     * Transform firebase response to [User].
     *
     * @param from Data user response.
     * @return user item.
     * @throws NoSuchElementException If the response results are empty.
     */
    @Throws(NoSuchElementException::class)
    override suspend fun map(from: UserResponse): User {
        return User(
            id = from.id,
            email = from.email,
            name = from.name,
            internetStatus = from.internetStatus,
            loginStatus = from.loginStatus,
            avatar = from.avatar
        )
    }

    /**
     * Reverse Transform to [UserResponse]
     *
     * @param from User response.
     * @return Data user response item.
     * @throws NoSuchElementException If the response results are empty.
     */
    override suspend fun reverseMap(from: User): UserResponse {
        return UserResponse(
            id = from.id,
            email = from.email,
            name = from.name,
            internetStatus = from.internetStatus,
            loginStatus = from.loginStatus,
            avatar = from.avatar
        )
    }
}
