package com.santaclose.lib.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import javax.persistence.AttributeConverter

class StringListConverter : AttributeConverter<List<String>?, String?> {
  companion object {
    private val mapper = ObjectMapper()
  }

  override fun convertToDatabaseColumn(attribute: List<String>?): String? {
    return attribute?.let { mapper.writeValueAsString(it) }
  }

  override fun convertToEntityAttribute(dbData: String?): List<String>? {
    return mapper.readValue(dbData, jacksonTypeRef<List<String>>())
  }
}
