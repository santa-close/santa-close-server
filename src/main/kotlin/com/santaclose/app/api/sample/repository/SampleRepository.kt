package com.santaclose.app.api.sample.repository

import com.santaclose.app.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : JpaRepository<Sample, Long>
