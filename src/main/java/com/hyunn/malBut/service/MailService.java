package com.hyunn.malBut.service;

import com.hyunn.malBut.entity.Proverb;
import com.hyunn.malBut.repository.ProverbJpaRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private String mailHost;

    private final JavaMailSender javaMailSender;
    private final ProverbJpaRepository proverbJpaRepository;

    /**
     * 인증 코드 전송하기 - 등록 이메일로
     */
    @Transactional
    public String sendAuthenticationMessage(String email, String code)
            throws IOException {
        // SMTP 설정
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // 발신자
        simpleMailMessage.setFrom(mailHost);

        // 수신자
        simpleMailMessage.setTo(email);

        // 제목
        String title = "[말벗] 이메일 등록을 위한 인증번호입니다.";
        simpleMailMessage.setSubject(title);

        // 내용
        String message = "인증번호는 " + code + "입니다.\n5분 이내에 입력해주시기 바랍니다.\n\n"
                + "The verification number is " + code + ".\nPlease enter it within 5 minutes.";
        simpleMailMessage.setText(message);

        try {
            javaMailSender.send(simpleMailMessage);
            return "이메일 전송 성공! " + email;
        } catch (MailException e) {
            return "이메일 전송 실패: " + e.getMessage();
        }
    }

    /**
     * 오늘의 속담 (구독 서비스)
     */
    @Transactional
    public String sendMail(String email) {
        // SMTP 설정
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // 발신자
        simpleMailMessage.setFrom(mailHost);

        // 수신자
        simpleMailMessage.setTo(email);

        // 제목
        String title = "[말벗] 오늘의 속담";
        simpleMailMessage.setSubject(title);

        // 내용
        String message = "";
        List<Proverb> proverbList = proverbJpaRepository.findRandomProverbs();
        for (Proverb proverb : proverbList) {
            message += proverb.getKorean() + " -> " + proverb.getEnglish() + "\n\n";
        }
        message += "자세한 내용은 사이트에서 확인해주세요!\nhttps://project.hyunn.site";
        System.out.println(email); // email 확인 로그
        simpleMailMessage.setText(message);

        try {
            javaMailSender.send(simpleMailMessage);
            return "이메일 전송 성공! " + email;
        } catch (MailException e) {
            return "이메일 전송 실패: " + e.getMessage();
        }
    }
}

