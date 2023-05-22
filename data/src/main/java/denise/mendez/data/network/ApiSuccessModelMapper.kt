package denise.mendez.data.network

/**
 * @author skydoves (Jaewoong Eum)
 *
 * A mapper interface for mapping [ApiResponse.Success] response as a custom [V] instance model.
 *
 * @see [ApiSuccessModelMapper](https://github.com/skydoves/sandwich#apisuccessmodelmapper)
 */
public fun interface ApiSuccessModelMapper<T, V> {

    /**
     * maps the [ApiResponse.Success] to the [V] using the mapper.
     *
     * @param apiSuccessResponse The [ApiResponse.Success] response from the network request.
     * @return A custom [V] success response model.
     */
    public fun map(apiSuccessResponse: ApiResponse.Success<T>): V
}
