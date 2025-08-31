package org.missao.roxa.missaoroxabackend.modules.authority.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.authority.domain.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, UUID> {

    Optional<AuthorityEntity> findByName_Name(String name);

}