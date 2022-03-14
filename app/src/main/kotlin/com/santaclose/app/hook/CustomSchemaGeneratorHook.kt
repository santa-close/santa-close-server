package com.santaclose.app.hook

import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KType

class CustomSchemaGeneratorHooks(override val wiringFactory: KotlinDirectiveWiringFactory) :
  SchemaGeneratorHooks {
  override fun willGenerateGraphQLType(type: KType): GraphQLType? =
    when (type.classifier) {
      LocalDateTime::class -> graphqlDateTimeType
      else -> null
    }
}

internal val graphqlDateTimeType =
  GraphQLScalarType.newScalar()
    .name("DateTime")
    .description("A type representing a formatted java.time.LocalDateTime")
    .coercing(DateTimeCoercing)
    .build()

private object DateTimeCoercing : Coercing<LocalDateTime, String> {
  private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  override fun parseValue(input: Any): LocalDateTime =
    runCatching { LocalDateTime.parse(serialize(input), formatter) }.getOrElse {
      throw CoercingParseValueException("Expected valid DateTime but was $input")
    }

  override fun parseLiteral(input: Any): LocalDateTime {
    return runCatching { LocalDateTime.parse(serialize(input), formatter) }.getOrElse {
      throw CoercingParseLiteralException("Expected valid DateTime literal but was $input")
    }
  }

  override fun serialize(dataFetcherResult: Any): String =
    runCatching {
      (dataFetcherResult as? LocalDateTime)?.format(formatter) ?: dataFetcherResult.toString()
    }
      .getOrElse {
        throw CoercingSerializeException(
          "Data fetcher result $dataFetcherResult cannot be serialized to a String"
        )
      }
}
