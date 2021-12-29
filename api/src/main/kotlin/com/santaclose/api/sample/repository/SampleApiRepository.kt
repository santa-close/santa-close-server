package com.santaclose.api.sample.repository

import com.santaclose.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleApiRepository : JpaRepository<Sample, Long>
