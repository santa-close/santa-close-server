package com.santaclose.app.sample.service

import com.santaclose.app.sample.repository.SampleAppRepository
import com.santaclose.lib.entity.sample.Sample
import org.springframework.stereotype.Service

@Service
class SampleAppMutationService(
    private val sampleAppRepository: SampleAppRepository,
) {
    fun create(sample: Sample) {
        sampleAppRepository.save(sample)
    }
}
