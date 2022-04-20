package com.santaclose.app.restaurant.service

import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppRepository
import javax.persistence.*
import org.springframework.stereotype.Service

@Service
class RestaurantAppMutationService(
  private val restaurantReviewAppRepository: RestaurantReviewAppRepository,
  private val restaurantRepository: RestaurantAppRepository,
) {

  fun create(input: CreateRestaurantAppInput, userId: Long) {
    //    val isRestaurantExist = restaurantRepository.existsById(input.restaurantId.toLong())
    //    if (!isRestaurantExist) {
    //      throw NoResultException("유효하지 않은 restaurantId 입니다.")
    //    }

    //      @Entity
    //      class Restaurant(
    //          @field:NotNull var name: String,
    //          @Column(length = 100) var description: String,
    //          @Convert(converter = StringListConverter::class)
    //          var images: MutableList<String> = mutableListOf(),
    //          @Enumerated(EnumType.STRING) @field:NotNull var foodType: FoodType,
    //          @ManyToOne(fetch = FetchType.LAZY) @field:NotNull var appUser: AppUser,
    //          @OneToOne(fetch = FetchType.LAZY) var location: Location,
    //      ) : BaseEntity()

    //    Restaurant(name = input.name).apply { restaurantReviewAppRepository.save(this) }
  }
}
