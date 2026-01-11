package org.example.bank_system.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {
    private final Random random;

    public Long generateAccountNumber() {
        return random.nextLong(1000,9999);
    }
}

