package com.santaclose.app.sample.repository

import com.santaclose.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleAppRepository : JpaRepository<Sample, Long>
