package denise.mendez.data.remote.apis

import denise.mendez.data.network.ApiResponse
import denise.mendez.data.remote.models.ItemDescriptionDto
import denise.mendez.data.remote.models.ProductDetailDto
import denise.mendez.data.remote.models.ProductSearchedDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliApi {
    @GET("sites/{idSite}/search")
    suspend fun searchProducts(
        @Path("idSite") idSite: String,
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ApiResponse<ProductSearchedDto>

    @GET("items/{idProduct}")
    suspend fun getProductDetails(
        @Path("idProduct") idItem: String
    ): ApiResponse<ProductDetailDto>

    @GET("items/{idItem}/description")
    suspend fun getItemDescription(
        @Path("idItem") idItem: String
    ): ApiResponse<ItemDescriptionDto>
}