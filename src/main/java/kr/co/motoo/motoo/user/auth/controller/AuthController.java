package kr.co.motoo.motoo.user.auth.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.motoo.motoo.global.ApiResponse;
import kr.co.motoo.motoo.user.UserService;
import kr.co.motoo.motoo.user.auth.service.AuthService;
import kr.co.motoo.motoo.user.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignupRequest signupRequest, HttpSession session) {
        String verifiedPhoneNumber = (String) session.getAttribute("verifiedPhoneNumber");

        if (verifiedPhoneNumber == null || !verifiedPhoneNumber.equals(signupRequest.getPhoneNumber())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("휴대폰 번호 인증이 필요하거나, 인증된 번호와 일치하지 않습니다."));
        }

        authService.registerUser(signupRequest);
        return ResponseEntity.ok(ApiResponse.success("회원가입에 성공하였습니다."));
    }

}
