package kr.co.motoo.motoo.user.auth.service;

import kr.co.motoo.motoo.user.auth.repository.MemoryAuthCodeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneAuthServiceTest {

    private final MemoryAuthCodeRepository memoryAuthCodeRepository = new MemoryAuthCodeRepository();
    private final PhoneVerificationService phoneAuthService = new PhoneVerificationService(memoryAuthCodeRepository);

    @Test
    void verifyCode() {
        String phone = "01012345678";
        String code = "123456";
        memoryAuthCodeRepository.save(phone, code, 180); // 5분 유효

        boolean result = phoneAuthService.verifyCode(phone, "123456");

        assertTrue(result);
        assertNull(memoryAuthCodeRepository.get(phone));
    }
}