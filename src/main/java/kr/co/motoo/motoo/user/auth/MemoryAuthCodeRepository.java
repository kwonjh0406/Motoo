package kr.co.motoo.motoo.user.auth;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryAuthCodeRepository {

    private final ConcurrentHashMap<String, AuthCode> store = new ConcurrentHashMap<>();

    // 인증번호 저장
    public void save(String phoneNumber, String code, long ttlSeconds) {
        store.put(phoneNumber, new AuthCode(code, ttlSeconds));
    }

    // 인증번호 조회
    public AuthCode get(String phoneNumber) {
        AuthCode authCode = store.get(phoneNumber);
        if (authCode != null && authCode.isExpired()) {
            store.remove(phoneNumber);
            return null;
        }
        return authCode;
    }

    // 인증번호 삭제
    public void remove(String phoneNumber) {
        store.remove(phoneNumber);
    }
}
