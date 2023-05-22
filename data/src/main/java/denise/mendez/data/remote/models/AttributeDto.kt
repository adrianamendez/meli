package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_INT
import denise.mendez.domain.utils.EMPTY_STRING

data class AttributeDto(
    val name: String? = EMPTY_STRING,
    @Json(name = "value_id")
    val valueId: String? = EMPTY_STRING,
    @Json(name = "value_name")
    val valueName: String? = EMPTY_STRING,
    @Json(name = "value_struct")
    val valueStruct: Any? = null,
    @Json(name = "attribute_group_id")
    val attributeGroupId: String? = EMPTY_STRING,
    @Json(name = "attribute_group_name")
    val attributeGroupName: String? = EMPTY_STRING,
    val source: Int? = EMPTY_INT,
    val id: String? = EMPTY_STRING
)
