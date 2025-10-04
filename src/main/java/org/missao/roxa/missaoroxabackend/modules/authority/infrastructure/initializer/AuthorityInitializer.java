package org.missao.roxa.missaoroxabackend.modules.authority.infrastructure.initializer;

import lombok.RequiredArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.authority.domain.entity.AuthorityEntity;
import org.missao.roxa.missaoroxabackend.modules.authority.infrastructure.repository.AuthorityRepository;
import org.missao.roxa.missaoroxabackend.modules.authority.shared.utils.AuthorityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("authorities-creation")
@RequiredArgsConstructor
public class AuthorityInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final AuthorityRepository authorityRepository;
    private final ApplicationContext context;

    @SuppressWarnings("NullableProblems")
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Starting the creations of the authorities.");
        Arrays.stream(AuthorityUtils.getAllPermissions())
                .filter(authority -> authorityRepository.findByName_Name(authority.getName()).isEmpty())
                .forEach(authority -> authorityRepository.save(new AuthorityEntity(authority)));

        System.out.println("Authorities successfully initialized.");

        SpringApplication.exit(context, () -> 0);
    }

}