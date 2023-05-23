package denise.mendez.data.network

import denise.mendez.domain.utils.EMPTY_STRING

data class MessageExceptionInfo(
    val title: String = EMPTY_STRING,
    val message: String = EMPTY_STRING,
    val error: Throwable,
    val params: HashMap<String, Any>? = null
)