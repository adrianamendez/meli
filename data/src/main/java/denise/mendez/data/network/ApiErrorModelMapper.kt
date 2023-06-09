package denise.mendez.data.network

/**
 * @author skydoves (Jaewoong Eum)
 *
 * A mapper interface for mapping [ApiResponse.Failure.Error] response as a custom [V] instance model.
 *
 * @see [ApiErrorModelMapper](https://github.com/skydoves/sandwich#apierrormodelmapper)
 */
public fun interface ApiErrorModelMapper<V> {

    /**
     * maps the [ApiResponse.Failure.Error] to the [V] using the mapper.
     *
     * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
     * @return A custom [V] error response model.
     */
    public fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): V
}