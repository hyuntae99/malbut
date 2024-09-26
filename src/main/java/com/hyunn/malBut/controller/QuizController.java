package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.request.GradeRequest;
import com.hyunn.malBut.dto.response.GradeResponse;
import com.hyunn.malBut.dto.response.QuizResponse;
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
  public List<QuizResponse> startQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("level") String level) {
    return quizService.startQuiz(level, apiKey);
  }

  @PostMapping("/grade")
  public GradeResponse gradeQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody List<GradeRequest> answers) {
    int score = quizService.gradeQuiz(answers, apiKey);
    String grade = quizService.calculateGrade(score, apiKey);

    return GradeResponse.create(score, grade);
  }
}
