package kr.co.motoo.motoo.user.auth.controller;

import kr.co.motoo.motoo.global.ApiResponse;
import kr.co.motoo.motoo.user.auth.PhoneAuthService;
import kr.co.motoo.motoo.user.auth.dto.SendCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PhoneAuthService phoneAuthService;

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendCode(@RequestBody SendCodeRequest sendCodeRequest) {
        phoneAuthService.sendCode(sendCodeRequest.getPhoneNumber());
        return ResponseEntity.ok(ApiResponse.success("모바일 인증번호 발급 성공"));
    }
}
