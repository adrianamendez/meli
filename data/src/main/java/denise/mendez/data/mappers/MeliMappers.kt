package denise.mendez.data.mappers

import denise.mendez.data.remote.models.AddressDto
import denise.mendez.data.remote.models.AttributeDto
import denise.mendez.data.remote.models.CityDto
import denise.mendez.data.remote.models.CountryDto
import denise.mendez.data.remote.models.InstallmentsDto
import denise.mendez.data.remote.models.PagingDto
import denise.mendez.data.remote.models.ProductDto
import denise.mendez.data.remote.models.ProductSearchedDto
import denise.mendez.data.remote.models.SellerAddressDto
import denise.mendez.data.remote.models.SellerDto
import denise.mendez.data.remote.models.ShippingDto
import denise.mendez.data.remote.models.StateDto
import denise.mendez.domain.models.Address
import denise.mendez.domain.models.Attribute
import denise.mendez.domain.models.City
import denise.mendez.domain.models.Country
import denise.mendez.domain.models.Installments
import denise.mendez.domain.models.Paging
import denise.mendez.domain.models.Product
import denise.mendez.domain.models.ProductSearched
import denise.mendez.domain.models.Seller
import denise.mendez.domain.models.SellerAddress
import denise.mendez.domain.models.Shipping
import denise.mendez.domain.models.State
import denise.mendez.domain.utils.EMPTY_DOUBLE
import denise.mendez.domain.utils.EMPTY_INT
import denise.mendez.domain.utils.EMPTY_STRING


object SearchResponseMapper {
    fun map(from: ProductSearchedDto) =
        ProductSearched(
            siteId = from.siteId ?: EMPTY_STRING,
            query = from.query ?: EMPTY_STRING,
            paging = PagingMapper.map(from.paging ?: PagingDto()),
            results = from.results?.map { ResultMapper.map(it) } ?: listOf()
        )
}

object PagingMapper {
    fun map(from: PagingDto) =
        Paging(
            total = from.total ?: EMPTY_INT,
            offset = from.offset ?: EMPTY_INT,
            limit = from.limit ?: EMPTY_INT,
            primaryResults = from.primaryResults ?: EMPTY_INT
        )
}

object ResultMapper {
    fun map(from: ProductDto) =
        Product(
            id = from.id ?: EMPTY_STRING,
            siteId = from.siteId ?: EMPTY_STRING,
            title = from.title ?: EMPTY_STRING,
            seller = SellerMapper.map(from.seller ?: SellerDto()),
            price = from.price ?: EMPTY_INT,
            currencyId = from.currencyId ?: EMPTY_STRING,
            availableQuantity = from.availableQuantity ?: EMPTY_INT,
            soldQuantity = from.soldQuantity ?: EMPTY_INT,
            buyingMode = from.buyingMode ?: EMPTY_STRING,
            listingTypeId = from.listingTypeId ?: EMPTY_STRING,
            stopTime = from.stopTime ?: EMPTY_STRING,
            condition = from.condition ?: EMPTY_STRING,
            permalink = from.permalink ?: EMPTY_STRING,
            thumbnail = from.thumbnail ?: EMPTY_STRING,
            acceptsMercadopago = from.acceptsMercadopago ?: false,
            installments = InstallmentsMapper.map(from.installments ?: InstallmentsDto()),
            address = AddressMapper.map(from.address ?: AddressDto()),
            shipping = ShippingMapper.map(from.shipping ?: ShippingDto()),
            sellerAddress = SellerAddressMapper.map(from.sellerAddress ?: SellerAddressDto()),
            attributes = from.attributes?.map { AttributeMapper.map(it) } ?: listOf(),
            originalPrice = from.originalPrice?.toDouble() ?: EMPTY_DOUBLE,
            categoryId = from.categoryId ?: EMPTY_STRING,
            officialStoreId = from.officialStoreId ?: EMPTY_INT,
            catalogProductId = from.catalogProductId ?: EMPTY_STRING,
            tags = from.tags ?: listOf(),
            catalogListing = from.catalogListing ?: false
        )
}

object SellerMapper {
    fun map(from: SellerDto) =
        Seller(
            id = from.id ?: EMPTY_INT,
            powerSellerStatus = from.powerSellerStatus ?: EMPTY_STRING,
            carDealer = from.carDealer ?: false,
            realEstateAgency = from.realEstateAgency ?: false,
            tags = from.tags ?: listOf()
        )
}

object InstallmentsMapper {
    fun map(from: InstallmentsDto) =
        Installments(
            quantity = from.quantity ?: EMPTY_INT,
            amount = from.amount ?: EMPTY_DOUBLE,
            rate = from.rate ?: EMPTY_DOUBLE,
            currencyId = from.currencyId ?: EMPTY_STRING
        )
}

object AddressMapper {
    fun map(from: AddressDto) =
        Address(
            stateId = from.stateId ?: EMPTY_STRING,
            stateName = from.stateName ?: EMPTY_STRING,
            cityId = from.cityId ?: EMPTY_STRING,
            cityName = from.cityName ?: EMPTY_STRING
        )
}

object ShippingMapper {
    fun map(from: ShippingDto) =
        Shipping(
            freeShipping = from.freeShipping ?: false,
            mode = from.mode ?: EMPTY_STRING,
            tags = from.tags ?: listOf(),
            logisticType = from.logisticType ?: EMPTY_STRING,
            storePickUp = from.storePickUp ?: false
        )
}

object SellerAddressMapper {
    fun map(from: SellerAddressDto) =
        SellerAddress(
            country = CountryMapper.map(from.country ?: CountryDto()),
            state = StateMapper.map(from.state ?: StateDto()),
            city = CityMapper.map(from.city ?: CityDto()),
            latitude = from.latitude ?: EMPTY_STRING,
            longitude = from.longitude ?: EMPTY_STRING
        )
}

object CountryMapper {
    fun map(from: CountryDto) =
        Country(
            id = from.id ?: EMPTY_STRING,
            name = from.name ?: EMPTY_STRING
        )
}

object StateMapper {
    fun map(from: StateDto) =
        State(
            id = from.id ?: EMPTY_STRING,
            name = from.name ?: EMPTY_STRING
        )
}

object CityMapper {
    fun map(from: CityDto) =
        City(
            id = from.id ?: EMPTY_STRING,
            name = from.name ?: EMPTY_STRING
        )
}

object AttributeMapper {
    fun map(from: AttributeDto) =
        Attribute(
            name = from.name ?: EMPTY_STRING,
            valueId = from.valueId ?: EMPTY_STRING,
            valueName = from.valueName ?: EMPTY_STRING,
            valueStruct = from.valueStruct,
            attributeGroupId = from.attributeGroupId ?: EMPTY_STRING,
            attributeGroupName = from.attributeGroupName ?: EMPTY_STRING,
            source = from.source ?: EMPTY_INT,
            id = from.id ?: EMPTY_STRING
        )
}

