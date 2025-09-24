package se.lexicon.flightbooking_api.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.flightbooking_api.service.AiChatService;

@RestController
@RequestMapping("/api/chat")
public class FlightAssistantAiController {
    private final AiChatService aiChatService;

    @Autowired
    public FlightAssistantAiController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @GetMapping("/ask")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question sent success")
    })
    public ResponseEntity<String> ask(@RequestParam
                                      @NotNull(message = "Question cannot be null")
                                      @NotBlank(message = "Question cannot be blank")
                                      String question) {
        System.out.println("question = " + question);
        return ResponseEntity.ok    (aiChatService.chatMemory(question, "123"));
    }
}
