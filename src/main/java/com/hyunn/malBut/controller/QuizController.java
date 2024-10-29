package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.request.EvaluateProverbRequest;
import com.hyunn.malBut.dto.request.GradeWordRequest;
import com.hyunn.malBut.dto.response.EvaluateProverbResponse;
import com.hyunn.malBut.dto.response.GradeWordResponse;
import com.hyunn.malBut.dto.response.QuizProverbResponse;
import com.hyunn.malBut.dto.response.QuizWordResponse;
import com.hyunn.malBut.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            summary = "단어 퀴즈 시작",
            description = "주어진 난이도에 따라 단어 퀴즈를 시작합니다.")
    @GetMapping("/start-word")
    public List<QuizWordResponse> startWordQuiz(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "난이도 수준", required = true)
            @RequestParam("level") String level) {

        return quizService.startWordQuiz(level, apiKey);
    }

    @Operation(
            summary = "단어 퀴즈 채점",
            description = "제출된 단어 퀴즈 답안을 채점하고 점수와 등급을 반환합니다.")
    @PostMapping("/grade-word")
    public GradeWordResponse gradeWordQuiz(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "단어 퀴즈 답안 목록",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    type = "array",
                                    implementation = GradeWordRequest.class
                            )
                    )
            )
            @RequestBody List<GradeWordRequest> answers) {

        int score = quizService.gradeWordQuiz(answers, apiKey);
        String grade = quizService.calculateWordGrade(score, apiKey);
        return GradeWordResponse.create(score, grade);
    }

    @Operation(
            summary = "속담 퀴즈 시작",
            description = "주어진 난이도에 따라 속담 퀴즈를 시작합니다.")
    @GetMapping("/start-proverb")
    public List<QuizProverbResponse> startProverbQuiz(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "난이도 수준", required = true)
            @RequestParam("level") String level) {

        return quizService.startProverbQuiz(level, apiKey);
    }

    @Operation(
            summary = "속담 퀴즈 평가",
            description = "제출된 속담 퀴즈 답안을 평가하고 피드백을 반환합니다.")
    @PostMapping("/evaluate-proverb")
    public List<EvaluateProverbResponse> evaluateProverbQuiz(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "속담 퀴즈 답안 목록",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    type = "array",
                                    implementation = EvaluateProverbRequest.class
                            )
                    )
            )
            @RequestBody List<EvaluateProverbRequest> answers) {

        return quizService.gradeProverbQuiz(answers, apiKey);
    }
}
