package denise.mendez.data.network

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author skydoves (Jaewoong Eum)
 *
 * CoroutinesResponseCallAdapterFactory is an coroutines call adapter factory for creating [ApiResponse].
 *
 * Adding this class to [Retrofit] allows you to return on [ApiResponse] from service method.
 *
 * ```
 * @GET("DisneyPosters.json")
 * suspend fun fetchDisneyPosterList(): ApiResponse<List<Poster>>
 * ```
 */
public class ApiResponseCallAdapterFactory private constructor(
    private val coroutineScope: CoroutineScope
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                val rawType = getRawType(callType)
                if (rawType != ApiResponse::class.java) {
                    return null
                }

                val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                return ApiResponseCallAdapter(resultType, coroutineScope)
            }
            Deferred::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                val rawType = getRawType(callType)
                if (rawType != ApiResponse::class.java) {
                    return null
                }

                val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                return ApiResponseDeferredCallAdapter<Any>(resultType, coroutineScope)
            }
            else -> return null
        }
    }

    public companion object {
        @JvmStatic
        public fun create(
            coroutineScope: CoroutineScope = SandwichInitializer.sandwichScope
        ): ApiResponseCallAdapterFactory = ApiResponseCallAdapterFactory(coroutineScope)
    }
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * ApiResponseCallAdapter is an call adapter for creating [ApiResponse] by executing Retrofit's service methods.
 *
 * Request API network call asynchronously and returns [ApiResponse].
 */
internal class ApiResponseCallAdapter constructor(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType(): Type {
        return resultType
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCallDelegate(call, coroutineScope)
    }
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * ApiResponseCallAdapter is an call adapter for creating [ApiResponse] by executing Retrofit's service methods.
 *
 * Request API network call asynchronously and returns [Deferred] of [ApiResponse].
 */
internal class ApiResponseDeferredCallAdapter<T> constructor(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope
) : CallAdapter<T, Deferred<ApiResponse<T>>> {

    override fun responseType(): Type {
        return resultType
    }

    @Suppress("DeferredIsResult")
    override fun adapt(call: Call<T>): Deferred<ApiResponse<T>> {
        val deferred = CompletableDeferred<ApiResponse<T>>().apply {
            invokeOnCompletion {
                if (isCancelled && !call.isCanceled) {
                    call.cancel()
                }
            }
        }

        coroutineScope.launch {
            try {
                val response = call.awaitResponse()
                val apiResponse = ApiResponse.of { response }
                deferred.complete(apiResponse)
            } catch (e: Exception) {
                val apiResponse = ApiResponse.error<T>(e)
                deferred.complete(apiResponse)
            }
        }

        return deferred
    }
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * ApiResponseCallDelegate is a delegate [Call] proxy for handling and transforming normal generic type [T]
 * as [ApiResponse] that wrapping [T] data from the network responses.
 */
internal class ApiResponseCallDelegate<T>(
    proxy: Call<T>,
    private val coroutineScope: CoroutineScope
) : CallDelegate<T, ApiResponse<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) {
        coroutineScope.launch {
            try {
                val response = proxy.awaitResponse()
                val apiResponse = ApiResponse.of { response }
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(apiResponse))
            } catch (e: Exception) {
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(ApiResponse.error(e)))
            }
        }
    }

    override fun executeImpl(): Response<ApiResponse<T>> =
        runBlocking(coroutineScope.coroutineContext) {
            val response = proxy.execute()
            val apiResponse = ApiResponse.of { response }
            Response.success(apiResponse)
        }

    override fun cloneImpl() = ApiResponseCallDelegate(proxy.clone(), coroutineScope)
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * CallDelegate is a delegate [Call] proxy for handling and transforming one to another generic types
 * between the two different types of [Call] requests.
 */
internal abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun execute(): Response<TOut> = executeImpl()
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled
    override fun timeout(): Timeout = SandwichInitializer.sandwichTimeout ?: proxy.timeout()

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun executeImpl(): Response<TOut>
    abstract fun cloneImpl(): Call<TOut>
}
