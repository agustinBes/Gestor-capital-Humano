package com.practical.consultora_rrhh.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class HashGeneratorConfig {

 /*   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner printHashes(PasswordEncoder encoder) {
        return args -> {
            System.out.println("JDOE -> " + encoder.encode("holamundo1234"));
            System.out.println("PSISTEMAS -> " + encoder.encode("contraseña1234#27"));
        };
    }*/
}