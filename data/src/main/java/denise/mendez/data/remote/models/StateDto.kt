package denise.mendez.data.remote.models

import denise.mendez.domain.utils.EMPTY_STRING

data class StateDto(
    val id: String? = EMPTY_STRING,
    val name: String? = EMPTY_STRING
)