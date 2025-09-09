package org.missao.roxa.missaoroxabackend.modules.municipality.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.municipality.domain.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity, UUID> {
}