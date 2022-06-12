package com.santaclose.app.config

import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class GraphQlConfig {

    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer =
        RuntimeWiringConfigurer { builder -> builder.scalar(datetimeScalar()) }

    private fun datetimeScalar(): GraphQLScalarType =
        GraphQLScalarType
            .newScalar()
            .name("DateTime")
            .description("A type representing a formatted java.time.LocalDateTime")
            .coercing(DateTimeCoercing)
            .build()
}

private object DateTimeCoercing : Coercing<LocalDateTime, String> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun parseValue(input: Any): LocalDateTime =
        runCatching { LocalDateTime.parse(serialize(input), formatter) }.getOrElse {
            throw CoercingParseValueException("Expected valid DateTime but was $input")
        }

    override fun parseLiteral(input: Any): LocalDateTime =
        runCatching { LocalDateTime.parse(serialize(input), formatter) }.getOrElse {
            throw CoercingParseLiteralException("Expected valid DateTime literal but was $input")
        }

    override fun serialize(dataFetcherResult: Any): String =
        runCatching {
            (dataFetcherResult as? LocalDateTime)?.format(formatter) ?: dataFetcherResult.toString()
        }.getOrElse {
            throw CoercingSerializeException(
                "Data fetcher result $dataFetcherResult cannot be serialized to a String"
            )
        }
}
