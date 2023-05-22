package denise.mendez.domain.models

data class Installments(
    val quantity: Int,
    val amount: Double,
    val rate: Double,
    val currencyId: String
)