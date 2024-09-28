package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.response.PronunciationResponse;
import com.hyunn.malBut.dto.response.ScriptResponse;
import com.hyunn.malBut.service.PronunciationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PronunciationController {

  private final PronunciationService pronunciationService;

  @Operation(
      summary = "랜덤 대본 제공",
      description = "주어진 난이도에 따라 랜덤 대본과 MP3 URL을 반환합니다.")
  @GetMapping("/random-script")
  public ResponseEntity<ScriptResponse> getRandomScript(
      @Parameter(description = "난이도 수준", required = true)
      @RequestParam("level") String level) {

    ScriptResponse scriptResponse = pronunciationService.getRandomScript(level);
    return ResponseEntity.ok(scriptResponse);
  }

  @Operation(
      summary = "발음 평가 요청",
      description = "업로드된 오디오 파일과 스크립트를 기반으로 발음을 평가합니다.")
  @PostMapping("/evaluate-pronunciation")
  public ResponseEntity<PronunciationResponse> evaluatePronunciation(
      @Parameter(description = "오디오 파일", required = true)
      @RequestParam("audio") MultipartFile audioFile,

      @Parameter(description = "대본", required = true)
      @RequestParam("script") String script,

      @Parameter(description = "API 키", required = true)
      @RequestHeader("x-api-key") String apiKey) {

    try {
      PronunciationResponse response = pronunciationService.evaluatePronunciation(audioFile, script, apiKey);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body(null);
    }
  }
}
