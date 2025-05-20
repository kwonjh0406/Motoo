package kr.co.motoo.motoo.user.auth.repository;

import lombok.Getter;

public class AuthCode {

    @Getter
    private final String code;

    private final long expiresAt;

    public AuthCode(String code, long ttlSeconds) {
        this.code = code;
        this.expiresAt = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }
}
