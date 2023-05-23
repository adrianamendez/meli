package denise.mendez.data

import denise.mendez.data.mappers.SuccessItemDescriptionMapper
import denise.mendez.data.mappers.SuccessItemProductDetailMapper
import denise.mendez.data.network.ApiResponse
import denise.mendez.data.network.message
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.data.remote.models.ItemDescriptionDto
import denise.mendez.data.remote.models.ProductDetailDto
import denise.mendez.data.repository.ItemsRepositoryImpl
import denise.mendez.domain.ResourceState
import denise.mendez.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ItemsRepositoryImplTest {
    private val meliApi: MeliApi = mockk()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val repository = ItemsRepositoryImpl(meliApi, mockLogger)

    @Test
    fun `getItemDescription should emit ResourceState Success when API call is successful`() =
        runBlocking {
            // given

            val response = Response.success(itemDescriptionDtoMockk)
            val apiResponse = ApiResponse.Success(response)
            coEvery { meliApi.getItemDescription(idProductMockk) } returns apiResponse

            // when
            val flow = repository.getItemDescription(idProductMockk)

            // then
            val expectedItemDescription = SuccessItemDescriptionMapper.map(apiResponse)
            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Success)
            assertEquals(
                expectedItemDescription,
                (actualResourceState as ResourceState.Success).data
            )
        }

    @Test
    fun `getItemDescription should emit ResourceState Error when API call is failure`() =
        runBlocking {
            // given
            val errorResponseBody: ResponseBody? =
                ResponseBody.create("text/plain".toMediaTypeOrNull(), "")
            val errorResponse: Response<ItemDescriptionDto> = Response.error(400, errorResponseBody)
            val apiResponse: ApiResponse<ItemDescriptionDto> =
                ApiResponse.Failure.Error(errorResponse)
            coEvery { meliApi.getItemDescription(idProductMockk) } returns apiResponse

            // when
            val flow = repository.getItemDescription(idProductMockk)

            // then
            val expectedErrorState = apiResponse as ApiResponse.Failure.Error
            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Error)
            assertEquals(
                "HTTP Error: " + expectedErrorState.message(),
                (actualResourceState as ResourceState.Error).message
            )
        }

    @Test
    fun `getItemProductDetail should emit ResourceState Success when API call is successful`() =
        runBlocking {
            // given

            val response = Response.success(ProductDetailsDtoMockk)
            val apiResponse = ApiResponse.Success(response)
            coEvery { meliApi.getProductDetails(idProductMockk) } returns apiResponse

            // when
            val flow = repository.getItemProductDetail(idProductMockk)

            // then
            val expectedProductDetails = SuccessItemProductDetailMapper.map(apiResponse)
            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Success)
            assertEquals(
                expectedProductDetails,
                (actualResourceState as ResourceState.Success).data
            )
        }

    @Test
    fun `getItemProductDetail should emit ResourceState Error when API call is failure`() =
        runBlocking {
            // given

            val errorResponseBody: ResponseBody? =
                ResponseBody.create("text/plain".toMediaTypeOrNull(), "")
            val errorResponse: Response<ProductDetailDto> = Response.error(400, errorResponseBody)
            val apiResponse: ApiResponse<ProductDetailDto> =
                ApiResponse.Failure.Error(errorResponse)
            coEvery { meliApi.getProductDetails(idProductMockk) } returns apiResponse

            // when
            val flow = repository.getItemProductDetail(idProductMockk)

            // then
            val expectedErrorState = apiResponse as ApiResponse.Failure.Error
            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Error)
            assertEquals(
                "HTTP Error: " + expectedErrorState.message(),
                (actualResourceState as ResourceState.Error).message
            )
        }

    @Test
    fun `getItemProductDetailWithDescription should emit ResourceState Success when both API calls are successful`() =
        runBlocking {
            // given

            val productDetailsResponse = Response.success(ProductDetailsDtoMockk)
            val productDetailsApiResponse = ApiResponse.Success(productDetailsResponse)
            coEvery { meliApi.getProductDetails(idProductMockk) } returns productDetailsApiResponse

            val itemDescriptionResponse = Response.success(itemDescriptionDtoMockk)
            val itemDescriptionApiResponse = ApiResponse.Success(itemDescriptionResponse)
            coEvery { meliApi.getItemDescription(idProductMockk) } returns itemDescriptionApiResponse

            // when
            val flow = repository.getItemProductDetailWithDescription(idProductMockk)

            // then
            val expectedProductDetails =
                SuccessItemProductDetailMapper.map(productDetailsApiResponse)
            val expectedItemDescription =
                SuccessItemDescriptionMapper.map(itemDescriptionApiResponse)
            val expectedCombinedDetail =
                expectedProductDetails?.apply { this.description = expectedItemDescription }

            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Success)
            assertEquals(
                expectedCombinedDetail,
                (actualResourceState as ResourceState.Success).data
            )
        }

    @Test
    fun `getItemProductDetailWithDescription should emit ResourceState Success when only product details API call is successful`() =
        runBlocking {
            // given

            val productDetailsResponse = Response.success(ProductDetailsDtoMockk)
            val productDetailsApiResponse = ApiResponse.Success(productDetailsResponse)
            coEvery { meliApi.getProductDetails(idProductMockk) } returns productDetailsApiResponse

            val errorResponseBody: ResponseBody? =
                ResponseBody.create("text/plain".toMediaTypeOrNull(), "")
            val itemDescriptionResponse: Response<ItemDescriptionDto> =
                Response.error(400, errorResponseBody)
            val itemDescriptionApiResponse: ApiResponse<ItemDescriptionDto> =
                ApiResponse.Failure.Error(itemDescriptionResponse)
            coEvery { meliApi.getItemDescription(idProductMockk) } returns itemDescriptionApiResponse

            // when
            val flow = repository.getItemProductDetailWithDescription(idProductMockk)

            // then
            val expectedProductDetails =
                SuccessItemProductDetailMapper.map(productDetailsApiResponse)

            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Success)
            assertEquals(
                expectedProductDetails,
                (actualResourceState as ResourceState.Success).data
            )
        }
}
