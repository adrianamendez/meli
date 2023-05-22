package denise.mendez.data.mappers

import android.util.Log
import denise.mendez.data.network.ApiResponse
import denise.mendez.data.network.ApiSuccessModelMapper
import denise.mendez.data.remote.models.ProductDetailDto
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.utils.DATA_MODULE_TAG

object SuccessItemProductDetailMapper : ApiSuccessModelMapper<ProductDetailDto, ProductDetails?> {
    override fun map(apiSuccessResponse: ApiResponse.Success<ProductDetailDto>): ProductDetails? {
        return try {
            apiSuccessResponse.data.mapToDomain()
        } catch (e: Exception) {
            Log.e(DATA_MODULE_TAG, "An exception occurred while mapping SuccessItemProductDetailMapper", e)
            null
        }
    }
}