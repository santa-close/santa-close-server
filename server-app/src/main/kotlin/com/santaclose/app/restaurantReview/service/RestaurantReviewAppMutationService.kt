package com.santaclose.app.restaurantReview.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.has
import com.santaclose.app.restaurantReview.controller.dto.CreateRestaurantReviewAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.web.exception.DomainError
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RestaurantReviewAppMutationService(
    private val restaurantRepository: RestaurantAppRepository,
    private val em: EntityManager,
) {

    @Transactional
    fun register(input: CreateRestaurantReviewAppInput, userId: Long): Either<DomainError, Unit> = either {
        val found = restaurantRepository.has(input.restaurantId.toLong()).bind()

        ensure(found) { DomainError.NotFound("유효하지 않은 restaurantId 입니다: ${input.restaurantId}") }

        val review = RestaurantReview(
            title = input.title,
            content = input.content,
            rating = input.rating.toEntity(),
            images = input.images.toMutableList(),
            restaurant = em.getReference(Restaurant::class.java, input.restaurantId.toLong()),
            appUser = em.getReference(AppUser::class.java, userId),
            priceAverage = input.priceAverage,
            priceComment = input.priceComment,
        )

        Either.catchDB { em.persist(review) }.bind()
    }
}
