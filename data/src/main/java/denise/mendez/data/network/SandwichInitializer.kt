package denise.mendez.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okio.Timeout

/**
 * @author skydoves (Jaewoong Eum)
 *
 * SandwichInitializer is a rules and strategies initializer of the network response.
 */
public object SandwichInitializer {

    /**
     * @author skydoves (Jaewoong Eum)
     *
     * determines the success code range of network responses.
     *
     * if a network request is successful and the response code is in the [successCodeRange],
     * its response will be a [ApiResponse.Success].
     *
     * if a network request is successful but out of the [successCodeRange] or failure,
     * the response will be a [ApiResponse.Failure.Error].
     * */
    @JvmStatic
    public var successCodeRange: IntRange = 200..299

    /**
     * @author skydoves (Jaewoong Eum)
     *
     * A global Operator that operates on [ApiResponse]s globally on each response.
     *
     * [com.skydoves.sandwich.operators.ApiResponseOperator] which allows you to handle success and error response instead of
     * the [ApiResponse.onSuccess], [ApiResponse.onError], [ApiResponse.onException] transformers.
     * [com.skydoves.sandwich.operators.ApiResponseSuspendOperator] can be used for suspension scope.
     *
     * Via setting a [sandwichOperator], we don't need to set operator for every [ApiResponse].
     */
    @Deprecated(
        message = "sandwichOperator has been deprecated. Use `sandwichOperators` instead.",
        replaceWith = ReplaceWith(expression = "SandwichInitializer.sandwichOperators")
    )
    @JvmStatic
    public var sandwichOperator: SandwichOperator? = null

    /**
     * @author skydoves (Jaewoong Eum)
     *
     * A list of global operators that is executed by [ApiResponse]s globally on each response.
     *
     * [com.skydoves.sandwich.operators.ApiResponseOperator] which allows you to handle success and error response instead of
     * the [ApiResponse.onSuccess], [ApiResponse.onError], [ApiResponse.onException] transformers.
     * [com.skydoves.sandwich.operators.ApiResponseSuspendOperator] can be used for suspension scope.
     *
     * Via setting [sandwichOperators], you don't need to set operator for every [ApiResponse].
     */
    @JvmStatic
    public var sandwichOperators: MutableList<SandwichOperator> = mutableListOf()

    /**
     * @author skydoves (Jaewoong Eum)
     *
     * A [CoroutineScope] for executing and operating the overall Retrofit network requests.
     */
    @JvmSynthetic
    public var sandwichScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * @author skydoves (Jaewoong Eum)
     *
     * A global [Timeout] for operating the [com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory].
     *
     * Returns a timeout that spans the entire call: resolving DNS, connecting, writing the request
     * body, server processing, and reading the response body. If the call requires redirects or
     * retries all must complete within one timeout period.
     */
    @JvmStatic
    public var sandwichTimeout: Timeout? = null
}