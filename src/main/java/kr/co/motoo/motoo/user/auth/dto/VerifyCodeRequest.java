package kr.co.motoo.motoo.user.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequest {
    private String phoneNumber;
    private String code;
}
