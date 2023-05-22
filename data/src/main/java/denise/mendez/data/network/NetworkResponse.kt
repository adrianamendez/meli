package denise.mendez.data.network

import okhttp3.Headers
import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    data class Success<T : Any>(
        val body: T,
        val headers: Headers? = null
    ) : NetworkResponse<T, Nothing>()

    fun wasSuccessful(): Boolean = this is Success

    interface Error {
        val error: Throwable
    }

    data class ServerError<U : Any>(
        val body: U?,
        val code: Int,
        val headers: Headers? = null
    ) : NetworkResponse<Nothing, U>(), Error {
        override val error = IOException("Network server error: $code \n$body")
    }

    data class NetworkError(override val error: IOException) :
        NetworkResponse<Nothing, Nothing>(), Error

    data class UnknownError(
        override val error: Throwable,
        val code: Int? = null,
        val headers: Headers? = null
    ) : NetworkResponse<Nothing, Nothing>(), Error
}