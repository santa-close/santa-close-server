package com.santaclose.api.sample.repository

import com.santaclose.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleApiRepository : JpaRepository<Sample, Long>
