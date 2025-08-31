package org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}