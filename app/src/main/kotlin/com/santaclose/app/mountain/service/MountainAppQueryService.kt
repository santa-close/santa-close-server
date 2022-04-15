package com.santaclose.app.mountain.service

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.repository.MountainAppQueryRepository
import com.santaclose.app.mountain.resolver.dto.MountainAppDetail
import com.santaclose.app.mountainReview.repository.MountainReviewAppRepository
import com.santaclose.lib.web.toLong
import org.springframework.stereotype.Service

@Service
class MountainAppQueryService(
  private val mountainAppQueryRepository: MountainAppQueryRepository,
  private val mountainReviewAppRepository: MountainReviewAppRepository
) {
  fun findDetail(id: ID): MountainAppDetail {
    val mountain = mountainAppQueryRepository.findOneWithLocation(id.toLong())
    val mountainReview = mountainReviewAppRepository.findByMountainId(id.toLong())
    return MountainAppDetail("d", "d")
  }
}
