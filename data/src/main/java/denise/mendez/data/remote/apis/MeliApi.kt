package denise.mendez.data.remote.apis


import denise.mendez.data.remote.models.ItemDescriptionDto
import denise.mendez.data.remote.models.ProductDetailDto
import denise.mendez.data.remote.models.ProductSearchedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliApi {
    @GET("sites/{idSite}/search")
    fun searchProducts(
        @Path("idSite") idSite: String,
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<ProductSearchedDto>

    @GET("items/{idProduct}")
    suspend fun getProductDetails(
        @Path("idProduct") idItem: String,
    ): Response<ProductDetailDto>

    @GET("items/{idItem}/description")
    suspend fun getItemDescription(
        @Path("idItem") idItem: String,
    ): Response<ItemDescriptionDto>
}