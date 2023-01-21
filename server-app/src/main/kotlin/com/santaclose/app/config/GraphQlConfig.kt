package com.santaclose.app.config

import com.santaclose.lib.logger.logger
import graphql.GraphqlErrorException
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import jakarta.persistence.NoResultException
import jakarta.validation.ValidationException
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.ErrorType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.CompletableFuture

@Configuration
class GraphQlConfig {

    @Bean
    fun sourceBuilderCustomizer(): GraphQlSourceBuilderCustomizer =
        GraphQlSourceBuilderCustomizer { builder ->
            builder.configureRuntimeWiring { wiringBuilder ->
                wiringBuilder.scalar(datetimeScalar())
            }
            builder.configureGraphQl { graphQlBuilder ->
                graphQlBuilder.defaultDataFetcherExceptionHandler(DataFetcherExceptionHandler)
            }
        }

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
                "Data fetcher result $dataFetcherResult cannot be serialized to a String",
            )
        }
}

private object DataFetcherExceptionHandler : DataFetcherExceptionHandler {
    private val logger = logger()

    override fun handleException(
        handlerParameters: DataFetcherExceptionHandlerParameters,
    ): CompletableFuture<DataFetcherExceptionHandlerResult> {
        val exception = handlerParameters.exception
        val sourceLocation = handlerParameters.sourceLocation
        val path = handlerParameters.path
        val error = GraphqlErrorException
            .newErrorException()
            .cause(exception)
            .message(exception.message)
            .sourceLocation(sourceLocation)
            .path(path.toList())
            .errorClassification(
                when (exception) {
                    is NoResultException -> ErrorType.NOT_FOUND
                    is ValidationException -> ErrorType.BAD_REQUEST
                    else -> ErrorType.INTERNAL_ERROR
                },
            )
            .build()

        logger.error(exception.message, exception)

        return CompletableFuture.completedFuture(
            DataFetcherExceptionHandlerResult
                .newResult()
                .error(error)
                .build(),
        )
    }
}
