package org.missao.roxa.missaoroxabackend.modules.products.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT * FROM products WHERE unaccent(LOWER(name)) = unaccent(LOWER(:name))",
            nativeQuery = true)
    Optional<ProductEntity> findByName(@Param(value = "name") String name);

}