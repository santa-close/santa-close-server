package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import org.springframework.format.annotation.NumberFormat

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

data class CreateRestaurantAppInput(
  @field:NumberFormat(style = NumberFormat.Style.NUMBER) val mountainId: ID,
)
