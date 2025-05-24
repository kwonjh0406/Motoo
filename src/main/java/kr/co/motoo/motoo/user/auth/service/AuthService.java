package kr.co.motoo.motoo.user.auth.service;

import kr.co.motoo.motoo.user.User;
import kr.co.motoo.motoo.user.UserRepository;
import kr.co.motoo.motoo.user.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByNickname(signupRequest.getNickname())) {
            return;
        }
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return;
        }
        User user = User.builder()
                .nickname(signupRequest.getNickname())
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .phoneNumber(signupRequest.getPhoneNumber())
                .build();

        userRepository.save(user);
    }


}
