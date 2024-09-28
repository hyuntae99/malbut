package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ScriptResponse {

  @Schema(description = "대본 내용", example = "오늘 날씨는 어떨까요?", required = true)
  private String script;

  @Schema(description = "대본에 대한 MP3 URL", example = "http://example.com/audio/script123.mp3", required = true)
  private String mp3Url;

  public ScriptResponse(String script, String mp3Url) {
    this.script = script;
    this.mp3Url = mp3Url;
  }

  public static ScriptResponse create(String script, String mp3Url) {
    return new ScriptResponse(script, mp3Url);
  }

}