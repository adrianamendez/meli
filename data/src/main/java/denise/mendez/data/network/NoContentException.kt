package denise.mendez.data.network

/**
 * @author skydoves (Jaewoong Eum)
 *
 * Thrown by various accessor methods to indicate that the response body being requested
 * does not exist. e.g., 204 (NoContent), 205 (ResetContent).
 *
 * The server has successfully fulfilled the request with the 2xx code
 * and that there is no additional content to send in the response payload body.
 *
 * If we want to handle empty body instead of throwing [NoContentException] for the 204 and 205 request case,
 * use the [com.skydoves.sandwich.interceptors.EmptyBodyInterceptor] for your interceptor.
 *
 * ```
 * OkHttpClient.Builder()
 *   .addInterceptor(EmptyBodyInterceptor())
 *   .build()
 * ```
 */
public class NoContentException constructor(
    public val code: Int,
    override val message: String? =
        "The server has successfully fulfilled the request with the code ($code) and that there is " +
            "no additional content to send in the response payload body."
) : Throwable(message)
