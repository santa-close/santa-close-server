package com.santaclose.app.mountainReview.repository

import com.santaclose.lib.entity.mountainReview.MountainReview
import org.springframework.data.jpa.repository.JpaRepository

interface MountainReviewAppRepository : JpaRepository<MountainReview, Long>
