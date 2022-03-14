package com.santaclose.lib.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0

  @CreatedDate @Column(nullable = false, updatable = false) lateinit var createdAt: LocalDateTime

  @LastModifiedDate @Column(nullable = false) lateinit var updatedAt: LocalDateTime
}
