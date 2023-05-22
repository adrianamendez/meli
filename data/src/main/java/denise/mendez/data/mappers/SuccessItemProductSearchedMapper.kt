package denise.mendez.data.mappers

import android.util.Log
import denise.mendez.data.network.ApiResponse
import denise.mendez.data.network.ApiSuccessModelMapper
import denise.mendez.data.remote.models.ProductSearchedDto
import denise.mendez.domain.models.Product
import denise.mendez.domain.utils.DATA_MODULE_TAG

object SuccessItemProductSearchedMapper :
    ApiSuccessModelMapper<ProductSearchedDto, List<Product>?> {
    override fun map(apiSuccessResponse: ApiResponse.Success<ProductSearchedDto>): List<Product>? {
        return try {
            apiSuccessResponse.data.results?.map {
                ResultMapper.map(it)
            }
        } catch (e: Exception) {
            Log.e(
                DATA_MODULE_TAG,
                "An exception occurred while mapping SuccessItemProductSearchedMapper",
                e
            )
            null
        }
    }
}