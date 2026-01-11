package org.example.bank_system.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class NumberGenerator {

    private final Random random;

    public Long generateNumber() {
        return random.nextLong(1000,9999);
    }
}