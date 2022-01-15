package com.santaclose.app.sample.repository

import com.santaclose.lib.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleAppRepository : JpaRepository<Sample, Long>
