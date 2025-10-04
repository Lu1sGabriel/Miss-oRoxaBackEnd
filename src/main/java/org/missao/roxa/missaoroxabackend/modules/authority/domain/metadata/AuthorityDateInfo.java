package org.missao.roxa.missaoroxabackend.modules.authority.domain.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.missao.roxa.missaoroxabackend.core.common.entity.metadata.IEntityDateInfo;

import java.time.Instant;

@Embeddable
@Getter
public class AuthorityDateInfo implements IEntityDateInfo {

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private Instant deletedAt;

    public AuthorityDateInfo() {
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