package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_INT
import denise.mendez.domain.utils.EMPTY_STRING

data class ProductDto(
    val id: String? = EMPTY_STRING,
    @Json(name = "site_id")
    val siteId: String = EMPTY_STRING,
    val title: String? = EMPTY_STRING,
    val seller: SellerDto? = null,
    val price: Int? = EMPTY_INT,
    @Json(name = "currency_id")
    val currencyId: String? = EMPTY_STRING,
    @Json(name = "available_quantity")
    val availableQuantity: Int? = EMPTY_INT,
    @Json(name = "sold_quantity")
    val soldQuantity: Int? = EMPTY_INT,
    @Json(name = "buying_mode")
    val buyingMode: String? = EMPTY_STRING,
    @Json(name = "listing_type_id")
    val listingTypeId: String? = EMPTY_STRING,
    @Json(name = "stop_time")
    val stopTime: String? = EMPTY_STRING,
    val condition: String? = EMPTY_STRING,
    val permalink: String? = EMPTY_STRING,
    val thumbnail: String? = EMPTY_STRING,
    @Json(name = "accepts_mercadopago")
    val acceptsMercadopago: Boolean? = false,
    val installments: InstallmentsDto? = InstallmentsDto(),
    val address: AddressDto? = AddressDto(),
    val shipping: ShippingDto? = ShippingDto(),
    @Json(name = "seller_address")
    val sellerAddress: SellerAddressDto? = SellerAddressDto(),
    val attributes: List<AttributeDto>? = emptyList(),
    @Json(name = "original_price")
    val originalPrice: Int? = EMPTY_INT,
    @Json(name = "category_id")
    val categoryId: String? = EMPTY_STRING,
    @Json(name = "official_store_id")
    val officialStoreId: Int? = EMPTY_INT,
    @Json(name = "catalog_product_id")
    val catalogProductId: String? = EMPTY_STRING,
    val tags: List<String>? = emptyList(),
    @Json(name = "catalog_listing")
    val catalogListing: Boolean? = false
)