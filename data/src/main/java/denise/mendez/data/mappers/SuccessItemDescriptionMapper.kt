package denise.mendez.data.mappers

import android.util.Log
import denise.mendez.data.network.ApiResponse
import denise.mendez.data.network.ApiSuccessModelMapper
import denise.mendez.data.remote.models.ItemDescriptionDto
import denise.mendez.domain.models.ItemDescription

object SuccessItemDescriptionMapper : ApiSuccessModelMapper<ItemDescriptionDto, ItemDescription?> {
    override fun map(apiSuccessResponse: ApiResponse.Success<ItemDescriptionDto>): ItemDescription? {
        return try {
            apiSuccessResponse.data.mapToDomain()
        } catch (e: Exception) {
            Log.e("MyApp", "An exception occurred while mapping SuccessItemDescriptionMapper", e)
            null
        }
    }
}