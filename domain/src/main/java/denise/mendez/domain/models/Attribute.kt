package denise.mendez.domain.models

data class Attribute(
    val name: String,
    val valueId: String,
    val valueName: String,
    val valueStruct: Any?,
    val attributeGroupId: String,
    val attributeGroupName: String,
    val source: Long,
    val id: String
)