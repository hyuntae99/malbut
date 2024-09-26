package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.response.PronunciationResponse;
import com.hyunn.malBut.dto.response.ScriptResponse;
import com.hyunn.malBut.service.PronunciationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PronunciationController {

  private final PronunciationService pronunciationService;

  // 랜덤 대본을 제공하는 엔드포인트
  @GetMapping("/random-script")
  public ResponseEntity<ScriptResponse> getRandomScript(
      @RequestParam("level") String level) {
    ScriptResponse scriptResponse = pronunciationService.getRandomScript(level);
    return ResponseEntity.ok(scriptResponse); // 대본과 MP3 URL을 같이 반환
  }


  // 발음 평가 요청을 처리하는 엔드포인트
  @PostMapping("/evaluate-pronunciation")
  public ResponseEntity<PronunciationResponse> evaluatePronunciation(
      @RequestParam("audio") MultipartFile audioFile,
      @RequestParam("script") String script,
      @RequestHeader("x-api-key") String apiKey) {
    try {
      PronunciationResponse response = pronunciationService.evaluatePronunciation(audioFile, script,
          apiKey);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(null);
    }
  }
}
