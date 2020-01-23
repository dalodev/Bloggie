/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.api.responses

import es.littledavity.core.annotations.OpenForTesting

/**
 * Generic network response for any type data [T].
 *
 * @param data The results returned by the call.
 */
@OpenForTesting
data class BaseResponse<T>(
    val data: DataResponse<T>
)
