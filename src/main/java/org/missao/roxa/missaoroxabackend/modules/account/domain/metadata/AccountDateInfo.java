package org.missao.roxa.missaoroxabackend.modules.account.domain.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.missao.roxa.missaoroxabackend.core.common.entity.metadata.IEntityDateInfo;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Embeddable
@Getter
public class AccountDateInfo implements IEntityDateInfo {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public AccountDateInfo() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void update() {
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.deletedAt = Instant.now();
    }

    public void activate() {
        this.deletedAt = null;
    }

}