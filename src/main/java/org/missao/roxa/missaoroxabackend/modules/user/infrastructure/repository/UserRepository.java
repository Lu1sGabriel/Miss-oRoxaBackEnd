package org.missao.roxa.missaoroxabackend.modules.user.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Optional<UserEntity> findByFullName_FullName(@Param("fullName") String fullName);

    @Query(value = "SELECT u FROM UserEntity u order by u.id ASC")
    Page<UserEntity> findAllByIdAsc(Pageable pageable);

    @Query(value = "SELECT u FROM UserEntity u order by u.id DESC")
    Page<UserEntity> findAllByIdDesc(Pageable pageable);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(DAY FROM u.birth_date) = :day
              AND EXTRACT(MONTH FROM u.birth_date) = :month
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfDay(@Param("day") int day, @Param("month") int month);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(MONTH FROM u.birth_date) = :month
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfMonth(@Param("month") int month);

    @Query(value = """
            SELECT *
            FROM users u
            WHERE EXTRACT(WEEK FROM u.birth_date) = EXTRACT(WEEK FROM CURRENT_DATE)
              AND EXTRACT(MONTH FROM u.birth_date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """, nativeQuery = true)
    List<UserEntity> findBirthdayOfWeek();

}