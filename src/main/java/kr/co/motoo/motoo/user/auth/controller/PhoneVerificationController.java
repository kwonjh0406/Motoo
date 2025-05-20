package kr.co.motoo.motoo.user.auth.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.motoo.motoo.global.ApiResponse;
import kr.co.motoo.motoo.user.auth.dto.SendCodeRequest;
import kr.co.motoo.motoo.user.auth.dto.VerifyCodeRequest;
import kr.co.motoo.motoo.user.auth.service.PhoneVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PhoneVerificationController {

    private final PhoneVerificationService phoneVerificationService;

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendCode(@RequestBody SendCodeRequest sendCodeRequest) {
        phoneVerificationService.sendCode(sendCodeRequest.getPhoneNumber());
        return ResponseEntity.ok(ApiResponse.success("모바일 인증번호 발급 성공"));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest, HttpSession session) {
        if(phoneVerificationService.verifyCode(verifyCodeRequest.getPhoneNumber(), verifyCodeRequest.getCode())){
            session.setAttribute("verifiedPhoneNumber", verifyCodeRequest.getPhoneNumber());
            return ResponseEntity.ok(ApiResponse.success("모바일 인증번호 인증 성공"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("인증번호가 일치하지 않거나 만료되었습니다."));
    }
}
