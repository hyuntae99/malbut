package com.hyunn.malBut.controller;

import com.hyunn.malBut.service.PronunciationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PronunciationController {

  private final PronunciationService pronunciationService;

  // 랜덤 대본을 제공하는 엔드포인트
  @GetMapping("/random-script")
  public ResponseEntity<Map<String, String>> getRandomScript(
      @RequestParam("level") String level) {
    Map<String, String> result = pronunciationService.getRandomScript(level);
    return ResponseEntity.ok(result); // 대본과 MP3 URL을 같이 반환
  }


  // 발음 평가 요청을 처리하는 엔드포인트
  @PostMapping("/evaluate-pronunciation")
  public ResponseEntity<Map<String, Object>> evaluatePronunciation(
      @RequestParam("audio") MultipartFile audioFile,
      @RequestParam("script") String script,
      @RequestHeader("x-api-key") String apiKey) {
    try {
      Map<String, Object> result = pronunciationService.evaluatePronunciation(audioFile, script, apiKey);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(null);
    }
  }
}
