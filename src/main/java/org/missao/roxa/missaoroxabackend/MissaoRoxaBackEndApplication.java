package org.missao.roxa.missaoroxabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
@EnableCaching
public class MissaoRoxaBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissaoRoxaBackEndApplication.class, args);
    }

}
