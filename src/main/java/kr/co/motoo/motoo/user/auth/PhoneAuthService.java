package kr.co.motoo.motoo.user.auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneAuthService {

    private final MemoryAuthCodeRepository memoryAuthCodeRepository;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    public void sendCode(String phoneNumber) {
        String code = generateCode();
        memoryAuthCodeRepository.save(phoneNumber, code, 180);
        sendSms(phoneNumber, code);
    }

    private void sendSms(String phoneNumber, String code) {
        String formattedNumber = formatPhoneNumber(phoneNumber);
        String message = "[모투] 인증번호는 [" + code + "] 입니다.";

        SnsClient snsClient = SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .phoneNumber(formattedNumber)
                .build();

        snsClient.publish(request);
        log.info("SMS 전송 완료: {} -> {}", code, phoneNumber);
    }

    private String generateCode() {
        int number = new Random().nextInt(900000) + 100000;
        return String.valueOf(number);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+82" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }

    public boolean verifyCode(String phoneNumber, String inputCode) {
        AuthCode authCode = memoryAuthCodeRepository.get(phoneNumber);
        if (authCode != null && authCode.getCode().equals(inputCode)) {
            memoryAuthCodeRepository.remove(phoneNumber);
            return true;
        }
        return false;
    }
}
