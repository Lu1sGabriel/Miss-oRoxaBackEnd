package org.missao.roxa.missaoroxabackend.modules.account.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.account.domain.AccountEntity;
import org.missao.roxa.missaoroxabackend.modules.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByUser(UserEntity user);

    Optional<AccountEntity> findByCredentials_Email_Email(String email);

    Optional<AccountEntity> findByCredentials_PhoneNumber_Number(String phoneNumber);

}