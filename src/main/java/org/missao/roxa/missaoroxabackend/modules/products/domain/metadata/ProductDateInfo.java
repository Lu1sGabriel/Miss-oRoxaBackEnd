package org.missao.roxa.missaoroxabackend.modules.products.domain.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.missao.roxa.missaoroxabackend.core.common.entity.metadata.IEntityDateInfo;

import java.time.Instant;

@Embeddable
@Getter
public class ProductDateInfo implements IEntityDateInfo {

    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public ProductDateInfo() {
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