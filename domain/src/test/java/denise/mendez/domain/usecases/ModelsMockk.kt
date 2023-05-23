package denise.mendez.domain.usecases

import denise.mendez.domain.models.Address
import denise.mendez.domain.models.Attribute
import denise.mendez.domain.models.City
import denise.mendez.domain.models.Country
import denise.mendez.domain.models.Installments
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.models.Picture
import denise.mendez.domain.models.Product
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.models.Seller
import denise.mendez.domain.models.SellerAddress
import denise.mendez.domain.models.Shipping
import denise.mendez.domain.models.State
import denise.mendez.domain.utils.EMPTY_STRING

val picturesListMockk = listOf(Picture(
    id = "771968-MLA48647552133_122021",
    url = "http://http2.mlstatic.com/D_771968-MLA48647552133_122021-I.jpg"),
    Picture(
        id = "618746-MLA48647499535_122021",
        url = "http://http2.mlstatic.com/D_771968-MLA48647552133_122021-I.jpg")
)

val itemDescriptionMockk = ItemDescription(
    text = EMPTY_STRING,
    plainText = "Traje de baño Van Como Piña\\n\\nTraje de baño importado - 100% Microfibra - Calidad Premium\\n\\n//////////////////////////////////////////////////////////////////////////////////////////////////////////\\n\\nMirá nuestras publicaciones para ver más modelos!!\\nTenemos Reputación Intachable!! Consultá lo que quieras\\n\\nSOMOS MERCADO LIDER GOLD - 100% CALIFICACIONES POSITIVAS.\\n\\nTODOS LOS PRODUCTOS QUE VENDEMOS SON DE EXCELENTE CALIDAD, NUEVOS Y ORIGINALES.\\n\\nTENEMOS TODAS LAS PROMOS DE MERCADO PAGO\\n\\nHACEMOS ENVÍOS A DOMICILIO A TODO EL PAÍS POR MEDIO DE MERCADO ENVÍOS (Correo Argentino). \\nTenemos publicaciones con ENVÍO GRATUITO y publicaciones con envío a cargo del comprador. \\n\\nO SI QUERÉS, PODÉS RETIRAR TU COMPRA POR CUALQUIERA DE NUESTROS 4 LOCALES COMERCIALES, uno de ellos abierto de corrido de lunes a sábado de 9:00hs a 20:30hs.\\n\\nESTAMOS EN SAN FERNANDO, BUENOS AIRES, A 5 CUADRAS DE CUALQUIERA DE LAS 2 ESTACIONES.\\n\\nEN EL SITIO LA ATENCIÓN ES PERSONALIZADA LAS 24hs.\\nEstamos a tu disposición para lo que necesites, todas las veces que haga falta!\\n\\nMUY IMPORTANTE (1): Si necesitás hacer un cambio por talle o de color, se te va a hacer sin ningún problema siempre y cuando haya stock. Los GASTOS por CAMBIOS, tanto de DEVOLUCIÓN y REENVÍO, corren por cuenta del COMPRADOR.\\n\\n\\n\\nAXASHOP",
)

val idProductMockk = "MLA1116923983"
val ProductDetailsMockk= ProductDetails(
id = idProductMockk,
    title = "Short Traje De Baño Vcp Garden (0150)",
    pictures = picturesListMockk,
    description = itemDescriptionMockk,
    permalink = "https://articulo.mercadolibre.com.ar/MLA-1116923983-short-traje-de-bano-vcp-garden-0150-_JM"
)

val installmentsMockk = Installments(
    quantity = 12,
    amount = 100000.0,
    rate = 0.0,
    currencyId = "COP"
)

val addressMockk = Address(
    stateId = "CO-DC",
    stateName = "Bogotá D.C.",
    cityId = "TUxNQ1NBTjMxMzBa",
    cityName = "Usaquén"
)

val shippingMockk = Shipping(
    freeShipping = true,
    mode = "me2",
    tags = listOf("fulfillment", "mandatory_free_shipping"),
    logisticType = "fulfillment",
    storePickUp = false
)
val countryMockk = Country(
    id = "CO",
    name = "Colombia"
)

val stateMockk = State(
    id = "TUxNQ1NBTjMxMzBa",
    name = "Bogotá D.C."
)

val cityMockk = City (
    id = "TUxNQ1NBTjMxMzBa",
    name = "Usaquén"
)

val sellerAddressMockk = SellerAddress(

    country = countryMockk,
    state = stateMockk,
    city = cityMockk,
    latitude = EMPTY_STRING,
    longitude = EMPTY_STRING
)

val attributesMockk = Attribute(
    id = "BRAND",
    name = "Marca",
    valueId = "410",
    valueName = "Van Como Piña",
    valueStruct = null,
    attributeGroupId = "OTHERS",
    attributeGroupName = "Otros",
    source = 2860837171021627
)

val listAttributesMockk = listOf(attributesMockk,attributesMockk )

val sellerMockk = Seller(
    id = 286083717,
    powerSellerStatus = "platinum",
    carDealer = false,
    realEstateAgency = false,
    tags = listOf("normal", "user_info_verified", "credits_profile", "messages_as_seller", "messages_as_buyer"),
)
val productMockk = Product(
    id = "MCO611234",
    siteId = "MCO",
    title = "Short Traje De Baño Vcp Garden (0150)",
    price = 100000.0,
    currencyId = "COP",
    availableQuantity = 1,
    soldQuantity = 0,
    buyingMode = "buy_it_now",
    listingTypeId = "gold_special",
    stopTime = "2040-12-20T04:00:00.000Z",
    condition = "new",
    permalink = "https://articulo.mercadolibre.com.co/MCO-611234-short-traje-de-bano-vcp-garden-0150-_JM",
    thumbnail = "http://http2.mlstatic.com/D_771968-MLA48647552133_122021-I.jpg",
    acceptsMercadopago = true,
    installments = installmentsMockk,
    address = addressMockk,
    shipping = shippingMockk,
    sellerAddress = sellerAddressMockk,
    attributes = listAttributesMockk,
    originalPrice = 100000.0,
    categoryId = "MCO1430",
    officialStoreId = 1,
    catalogProductId = "213123",
    tags = listOf("good_quality_thumbnail",
        "good_quality_picture",
        "immediate_payment",
        "cart_eligible",
        "best_seller_candidate",
        "shipping_guaranteed"),
    catalogListing = false,
    seller = sellerMockk
)

