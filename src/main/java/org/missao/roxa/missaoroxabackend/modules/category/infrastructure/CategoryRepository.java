package org.missao.roxa.missaoroxabackend.modules.category.infrastructure;

import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    @Query(value = "SELECT  * FROM categories WHERE unaccent(LOWER(name)) = unaccent(LOWER(:name))",
            nativeQuery = true)
    Optional<CategoryEntity> findByName(@Param(value = "name") String name);


    @Query(value = """
                SELECT * FROM categories
                WHERE parent_id IS NULL AND deleted_at IS NULL
                ORDER BY name
            """, nativeQuery = true)
    List<CategoryEntity> findAllWhereParentIsNull();


}