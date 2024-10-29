package com.hyunn.malBut.service;

import com.hyunn.malBut.entity.User;
import com.hyunn.malBut.repository.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

  private final UserJpaRepository userJpaRepository;
  private final MailService mailService;

  @PostConstruct
  public void init() {
    // 애플리케이션 시작 시 즉시 실행
    sendEmails();
  }

  @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul") // 오전 9시에 실행
  public void sendEmails() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 시간 형식
    ZoneId koreaZoneId = ZoneId.of("Asia/Seoul"); // 대한민국 시간대
    ZonedDateTime todayDateTime = ZonedDateTime.now(koreaZoneId); // 오늘 날짜
    String todayDateString = todayDateTime.format(formatter); // 시간 형식 적용
    System.out.println(todayDateString);

    // 서비스 설정을 완료한 유저들에게 메세지 전송
    List<User> users = userJpaRepository.findAllByStatusTrue();
    for (User user : users) {
      mailService.sendMail(user.getEmail());
    }
  }
}
