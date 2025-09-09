package org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByCredentials_Email_Email(String email);

    Optional<AccountEntity> findByCredentials_PhoneNumber_Number(String phoneNumber);
}