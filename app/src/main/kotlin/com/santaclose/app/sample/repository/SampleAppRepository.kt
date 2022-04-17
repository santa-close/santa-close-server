package com.santaclose.app.sample.repository

import com.santaclose.app.sample.repository.dto.SampleNamePriceDto
import com.santaclose.lib.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleAppRepository : JpaRepository<Sample, Long> {
  fun findByIdIn(ids: List<Long>): List<SampleNamePriceDto>
}
