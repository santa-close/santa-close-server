package com.santaclose.app.sample.service

import arrow.core.Option
import com.santaclose.app.sample.repository.SampleAppRepository
import com.santaclose.app.sample.resolver.dto.SampleDto
import org.springframework.stereotype.Service

@Service
class SampleAppService(val sampleAppRepository: SampleAppRepository) {
    fun findByPrice(price: Int): Option<SampleDto> {
        return sampleAppRepository.findByPrice(price)
    }
}
