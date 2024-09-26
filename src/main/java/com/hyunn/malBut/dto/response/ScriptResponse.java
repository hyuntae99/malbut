package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class ScriptResponse {

  private String script;

  private String mp3Url;

  public ScriptResponse(String script, String mp3Url) {
    this.script = script;
    this.mp3Url = mp3Url;
  }

  public static ScriptResponse create(String script, String mp3Url) {
    return new ScriptResponse(script, mp3Url);
  }

}