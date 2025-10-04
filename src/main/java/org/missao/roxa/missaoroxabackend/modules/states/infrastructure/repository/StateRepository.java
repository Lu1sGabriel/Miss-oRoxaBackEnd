package org.missao.roxa.missaoroxabackend.modules.states.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.states.domain.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, UUID> {
    List<StateEntity> findAllByOrderByNameAsc();

    @Query(value = "SELECT * FROM states WHERE unaccent(LOWER(name)) = unaccent(LOWER(:name))",
            nativeQuery = true)
    Optional<StateEntity> findByName(String name);

}