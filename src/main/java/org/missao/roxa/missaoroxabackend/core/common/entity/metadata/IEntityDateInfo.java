package org.missao.roxa.missaoroxabackend.core.common.entity.metadata;

import java.time.Instant;

public interface IEntityDateInfo {
    Instant getDeletedAt();
}