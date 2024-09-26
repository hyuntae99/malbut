package com.hyunn.malBut.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  @GetMapping()
  public String main(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "index";
  }

  @GetMapping("/chat")
  public String chatPage(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "chat";
  }

  @GetMapping("/pronunciation")
  public String pronunciationPage(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "pronunciation";
  }

  @GetMapping("/quiz")
  public String quizPage(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "quiz";
  }

  @GetMapping("/subscribe")
  public String subscribe(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "subscribe";
  }

  @GetMapping("/withdraw")
  public String withdraw(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "withdraw";
  }
}

