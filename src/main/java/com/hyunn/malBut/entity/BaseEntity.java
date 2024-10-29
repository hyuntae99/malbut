package com.hyunn.malBut.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    // 수정 시간
    @LastModifiedDate
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        this.date = ZonedDateTime.now(koreaZoneId).toLocalDateTime();
    }
}
