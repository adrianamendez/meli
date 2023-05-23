package denise.mendez.data.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import denise.mendez.data.remote.models.ItemDescriptionDto
import denise.mendez.domain.utils.EMPTY_STRING

class ItemDescriptionDtoJsonAdapter {
    @FromJson
    fun fromJson(jsonObject: Map<String, Any>): ItemDescriptionDto {
        val text = jsonObject["text"] as? String ?: EMPTY_STRING
        val plainText = jsonObject["plain_text"] as? String ?: EMPTY_STRING
        return ItemDescriptionDto(text, plainText)
    }

    @ToJson
    fun toJson(itemDescriptionDto: ItemDescriptionDto): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["text"] = itemDescriptionDto.text ?: EMPTY_STRING
        map["plain_text"] = itemDescriptionDto.plainText ?: EMPTY_STRING
        return map
    }
}

