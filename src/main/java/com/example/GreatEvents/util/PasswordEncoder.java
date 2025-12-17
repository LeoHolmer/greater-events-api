package com.example.GreatEvents.util;

import com.password4j.Password;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    public boolean verify(String rawPassword, String hashedPassword) {
        return Password.check(rawPassword, hashedPassword).withBcrypt();
    }
}
