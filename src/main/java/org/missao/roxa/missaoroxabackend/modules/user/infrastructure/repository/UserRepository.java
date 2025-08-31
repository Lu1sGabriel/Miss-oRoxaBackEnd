package org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(
            value = "SELECT * FROM users WHERE unaccent(LOWER(full_name)) = unaccent(LOWER(:fullName))",
            nativeQuery = true
    )
    Optional<UserEntity> findByFullName_FullName(@Param("fullName") String fullName);

}