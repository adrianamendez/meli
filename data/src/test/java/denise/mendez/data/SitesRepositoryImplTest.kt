package denise.mendez.data

import denise.mendez.data.mappers.SuccessItemProductSearchedMapper
import denise.mendez.data.network.ApiResponse
import denise.mendez.data.network.SITE_ID
import denise.mendez.data.network.message
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.data.remote.models.ProductSearchedDto
import denise.mendez.data.repository.SitesRepositoryImpl
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
class SitesRepositoryImplTest {

    private val meliApi: MeliApi = mockk()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val repository = SitesRepositoryImpl(meliApi, mockLogger)

    @Test
    fun `getProducts should emit ResourceState Success when API call is successful`() =
        runBlocking {
            // given
            val response = Response.success(productSearchedDtoMockk)
            val apiResponse =
                ApiResponse.Success(response)// directly creating an instance of ApiResponse.Success
            coEvery { meliApi.searchProducts(SITE_ID, any(), any(), any()) } returns apiResponse

            // when
            val flow = repository.getProducts("query", 0, 10)

            // then
            val expectedProductList = SuccessItemProductSearchedMapper.map(apiResponse)
            val actualResourceState = flow.single()

            assertTrue(actualResourceState is ResourceState.Success)
            assertEquals(expectedProductList, (actualResourceState as ResourceState.Success).data)
        }

    @Test
    fun `getProducts should emit ResourceState Error when API call is failure`() = runBlocking {
        // given


        val errorResponseBody: ResponseBody? =
            ResponseBody.create("text/plain".toMediaTypeOrNull(), "")
        val errorResponse: Response<ProductSearchedDto> = Response.error(400, errorResponseBody)

        val apiResponse: ApiResponse<ProductSearchedDto> = ApiResponse.Failure.Error(errorResponse)


        coEvery { meliApi.searchProducts(SITE_ID, any(), any(), any()) } returns apiResponse

        // when
        val flow = repository.getProducts("query", 0, 10)

        // then
        val expectedErrorState = apiResponse as ApiResponse.Failure.Error

        val actualResourceState = flow.single()

        assertTrue(actualResourceState is ResourceState.Error)
        assertEquals(
            "HTTP Error: " + expectedErrorState.message(),
            (actualResourceState as ResourceState.Error).message
        )
    }

}


