package br.com.connectattoo.core

sealed class ResourceResult<T>(
   open val data: T?,
   open val error: Throwable? = null
) {
    data class Success<T>(
       override val data: T?
    ): ResourceResult<T>(data, null)

    data class Error<T>(
        override val data: T?,
        override val error: Throwable
    ): ResourceResult<T>(null, error)
}
