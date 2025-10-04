package org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(
            value = "SELECT * FROM users WHERE unaccent(LOWER(full_name)) = unaccent(LOWER(:fullName))",
            nativeQuery = true
    )
    Optional<UserEntity> findByName(@Param(value = "fullName") String fullName);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(DAY FROM u.birth_date) = :day
              AND EXTRACT(MONTH FROM u.birth_date) = :month
              AND u.deleted_at IS NULL
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfDay(@Param("day") int day, @Param("month") int month);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(MONTH FROM u.birth_date) = :month
              AND u.deleted_at IS NULL
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfMonth(@Param("month") int month);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(WEEK FROM u.birth_date) = EXTRACT(WEEK FROM CURRENT_DATE)
              AND EXTRACT(MONTH FROM u.birth_date) = EXTRACT(MONTH FROM CURRENT_DATE)
              AND u.deleted_at IS NULL
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfWeek();

}