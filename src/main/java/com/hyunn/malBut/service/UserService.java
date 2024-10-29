package com.hyunn.malBut.service;

import com.hyunn.malBut.entity.User;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.exception.UnauthorizedException;
import com.hyunn.malBut.exception.UserNotFoundException;
import com.hyunn.malBut.repository.UserJpaRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.security.x-api-key}")
    private String xApiKey;

    private final UserJpaRepository userJpaRepository;
    private final MailService mailService;

    /**
     * 이메일 등록
     */
    @Transactional
    public String registerEmail(String email, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        // 유저 탐색
        Optional<User> user = Optional.ofNullable(
                userJpaRepository.findUserByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException("유저 정보를 가져오지 못했습니다.")));
        User existUser = user.get();
        existUser.registerUser();
        userJpaRepository.save(existUser);
        return "이메일이 등록되었습니다.\n " + email;
    }

    /**
     * 유저 삭제
     */
    @Transactional
    public String deleteUser(String email, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        // 유저 탐색
        Optional<User> user = Optional.ofNullable(
                userJpaRepository.findUserByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException("유저 정보를 가져오지 못했습니다.")));
        User existUser = user.get();

        // 유저 삭제 및 저장
        userJpaRepository.delete(existUser);
        return "Termination completed.";
    }

    /**
     * 유저 인증 메세지
     */
    @Transactional
    public String sendAuthentication(String email, String apiKey) throws IOException {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        String message = "";

        // 이미 등록된 이메일 처리
        if (userJpaRepository.existsUserByEmail(email)) {
            Optional<User> user = Optional.ofNullable(
                    userJpaRepository.findUserByEmail(email)
                            .orElseThrow(() -> new UserNotFoundException("유저 정보를 가져오지 못했습니다.")));
            User existUser = user.get();

            // 인증 번호 부여 및 저장
            existUser.updateCode(generateRandomNumber());
            userJpaRepository.save(existUser);

            // 유저에게 인증 번호 발송
            message = mailService.sendAuthenticationMessage(email, existUser.getCode());
        } else {
            User newUser = User.createUser(email);
            newUser.updateCode(generateRandomNumber());
            userJpaRepository.save(newUser);

            // 유저에게 인증 번호 발송
            message = mailService.sendAuthenticationMessage(email, newUser.getCode());
        }

        return message;
    }

    /**
     * 랜덤 인증 코드 생성기
     */
    public String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // 6자리 숫자 생성 (100000 ~ 999999)
        return String.valueOf(randomNumber);
    }

    /**
     * 인증 번호 확인 로직
     */
    @Transactional
    public String authentication(String email, String code, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        // 유저 탐색
        Optional<User> user = Optional.ofNullable(
                userJpaRepository.findUserByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException("유저 정보를 가져오지 못했습니다.")));

        // 인증 코드가 같지 않다면 오류 처리
        if (!user.get().getCode().equals(code)) {
            throw new UnauthorizedException("인증 코드가 올바르지 않습니다.");
        }
        return "인증 완료";
    }
}
