package kr.co.motoo.motoo.user;

import kr.co.motoo.motoo.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse<Void>> checkUsername(@RequestParam String username) {
        if (userService.isDuplicateUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.fail("이미 사용 중인 아이디입니다."));
        }
        return ResponseEntity.ok(ApiResponse.success("사용 가능한 아이디입니다."));
    }
}
