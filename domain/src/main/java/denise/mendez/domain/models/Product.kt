package denise.mendez.domain.models

data class Product(
    val id: String,
    val siteId: String,
    val title: String,
    val seller: Seller,
    val price: Double,
    val currencyId: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val buyingMode: String,
    val listingTypeId: String,
    val stopTime: String,
    val condition: String,
    val permalink: String,
    val thumbnail: String,
    val acceptsMercadopago: Boolean,
    val installments: Installments,
    val address: Address,
    val shipping: Shipping,
    val sellerAddress: SellerAddress,
    val attributes: List<Attribute>,
    val originalPrice: Double,
    val categoryId: String,
    val officialStoreId: Int,
    val catalogProductId: String,
    val tags: List<String>,
    val catalogListing: Boolean
) {
    enum class ItemStatus {
        NEW, USED, NOT_SPECIFIED
    }
}