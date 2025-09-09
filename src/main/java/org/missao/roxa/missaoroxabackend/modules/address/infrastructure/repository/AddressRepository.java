package org.missao.roxa.missaoroxabackend.modules.address.infrastructure.repository;

import org.missao.roxa.missaoroxabackend.modules.address.domain.AddressEntity;
import org.missao.roxa.missaoroxabackend.modules.municipality.domain.MunicipalityEntity;
import org.missao.roxa.missaoroxabackend.modules.states.domain.StateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
    Page<AddressEntity> findByPostalCode_PostalCode(String postalCode, Pageable pageable);

    Page<AddressEntity> findByMunicipality(MunicipalityEntity municipality, Pageable pageable);

    Page<AddressEntity> findByMunicipality_State(StateEntity stateEntity, Pageable pageable);

}