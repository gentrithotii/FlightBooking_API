package se.lexicon.flightbooking_api.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiChatServiceImpl implements AiChatService {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final FlightAssistantToolCalling flightAssistantToolCalling;

    @Autowired
    public AiChatServiceImpl(ChatClient.Builder chatClient, ChatMemory chatMemory, FlightAssistantToolCalling flightAssistantToolCalling) {
        this.chatClient = chatClient.defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build()
        ).build();
        this.chatMemory = chatMemory;
        this.flightAssistantToolCalling = flightAssistantToolCalling;
    }

    @Override
    public String chatMemory(final String query, final String conversationId) {
        if (query == null || conversationId == null) {
            throw new IllegalArgumentException("Query and ConversationId cannot be null");
        }

        ChatResponse chatResponse = chatClient.prompt()
                .system("""
                        You are a helpful flight  assistant with the following capabilities:
                        1. Show all available flights using 'getAvailableFlights'
                   
                     
         
                        """)
                .user(query)

                .tools(flightAssistantToolCalling)
                .options(OpenAiChatOptions.builder().temperature(0.2).maxTokens(1000).build())
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .chatResponse();

        return chatResponse.getResult().getOutput().getText();
    }

}
