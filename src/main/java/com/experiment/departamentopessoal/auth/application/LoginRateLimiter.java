package com.experiment.departamentopessoal.auth.application;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimiter {

    private static final int MAX_ATTEMPTS = 10;
    private static final Duration WINDOW = Duration.ofMinutes(15);

    private final Map<String, AttemptWindow> attempts = new ConcurrentHashMap<>();

    public void check(String key) {
        AttemptWindow window = attempts.get(key);
        if (window == null) {
            return;
        }
        if (window.isExpired()) {
            attempts.remove(key);
            return;
        }
        if (window.count >= MAX_ATTEMPTS) {
            throw new TooManyLoginAttemptsException();
        }
    }

    public void registerFailure(String key) {
        attempts.compute(key, (ignored, existing) -> {
            if (existing == null || existing.isExpired()) {
                return new AttemptWindow(1, Instant.now().plus(WINDOW));
            }
            return new AttemptWindow(existing.count + 1, existing.expiresAt);
        });
    }

    public void reset(String key) {
        attempts.remove(key);
    }

    private static final class AttemptWindow {
        private final int count;
        private final Instant expiresAt;

        private AttemptWindow(int count, Instant expiresAt) {
            this.count = count;
            this.expiresAt = expiresAt;
        }

        private boolean isExpired() {
            return Instant.now().isAfter(expiresAt);
        }
    }
}
