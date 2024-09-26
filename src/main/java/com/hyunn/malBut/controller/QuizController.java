package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.quiz.Answer;
import com.hyunn.malBut.dto.quiz.Question;
import com.hyunn.malBut.service.QuizService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

  private final QuizService quizService;

  @GetMapping("/start")
  public List<Question> startQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("level") String level) {
    return quizService.startQuiz(level, apiKey);
  }

  @PostMapping("/grade")
  public Map<String, Object> gradeQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody List<Answer> answers) {
    int score = quizService.gradeQuiz(answers, apiKey);
    String grade = quizService.calculateGrade(score, apiKey);

    return Map.of("score", score, "grade", grade);
  }
}
