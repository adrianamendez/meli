package denise.mendez.domain.models

data class Shipping(
    val freeShipping: Boolean,
    val mode: String,
    val tags: List<String>,
    val logisticType: String,
    val storePickUp: Boolean
)