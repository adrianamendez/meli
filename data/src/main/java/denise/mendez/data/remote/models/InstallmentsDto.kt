package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_DOUBLE
import denise.mendez.domain.utils.EMPTY_INT
import denise.mendez.domain.utils.EMPTY_STRING

data class InstallmentsDto(
    val quantity: Int? = EMPTY_INT,
    val amount: Double? = EMPTY_DOUBLE,
    val rate: Double? = EMPTY_DOUBLE,
    @Json(name = "currency_id")
    val currencyId: String? = EMPTY_STRING
)
