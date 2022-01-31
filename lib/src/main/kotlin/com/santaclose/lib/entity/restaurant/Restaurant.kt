package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Restaurant(
    @field:NotNull  
    var name: String,

    @Size(max = 100)  
    var description: String,

    @Convert(converter = StringListConverter::class)  
    var images: List<String>? = null,

    @OneToMany(mappedBy = "restaurant")  
    @OrderBy("id DESC")  
    var reviews: MutableSet<RestaurantReview> = mutableSetOf(),
) : BaseEntity() {
    fun addReview(review: RestaurantReview) {
        reviews.add(review)
        review.restaurant = this
    }
}
