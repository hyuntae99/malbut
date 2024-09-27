package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.request.EvaluateProverbRequest;
import com.hyunn.malBut.dto.request.GradeWordRequest;
import com.hyunn.malBut.dto.response.EvaluateProverbResponse;
import com.hyunn.malBut.dto.response.GradeWordResponse;
import com.hyunn.malBut.dto.response.QuizProverbResponse;
import com.hyunn.malBut.dto.response.QuizWordResponse;
import com.hyunn.malBut.service.QuizService;
import java.util.List;
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

  @GetMapping("/start-word")
  public List<QuizWordResponse> startWordQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("level") String level) {
    return quizService.startWordQuiz(level, apiKey);
  }

  @PostMapping("/grade-word")
  public GradeWordResponse gradeWordQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody List<GradeWordRequest> answers) {
    int score = quizService.gradeWordQuiz(answers, apiKey);
    String grade = quizService.calculateWordGrade(score, apiKey);

    return GradeWordResponse.create(score, grade);
  }

  @GetMapping("/start-proverb")
  public List<QuizProverbResponse> startProverbQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("level") String level) {
    return quizService.startProverbQuiz(level, apiKey);
  }

  @PostMapping("/evaluate-proverb")
  public List<EvaluateProverbResponse> evaluateProverbQuiz(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody List<EvaluateProverbRequest> answers) {
    return quizService.gradeProverbQuiz(answers, apiKey);
  }
}

