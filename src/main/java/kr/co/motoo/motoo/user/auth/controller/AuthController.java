package kr.co.motoo.motoo.user.auth.controller;

import kr.co.motoo.motoo.global.ApiResponse;
import kr.co.motoo.motoo.user.auth.service.PhoneVerificationService;
import kr.co.motoo.motoo.user.auth.dto.SendCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final PhoneVerificationService phoneAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SendCodeRequest sendCodeRequest) {
        phoneAuthService.sendCode(sendCodeRequest.getPhoneNumber());
        return ResponseEntity.ok(ApiResponse.success("모바일 인증번호 발급 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody SendCodeRequest sendCodeRequest) {
        phoneAuthService.sendCode(sendCodeRequest.getPhoneNumber());
        return ResponseEntity.ok(ApiResponse.success("모바일 인증번호 발급 성공"));
    }

}
