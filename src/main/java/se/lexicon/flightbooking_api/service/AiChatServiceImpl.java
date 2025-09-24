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
        this.chatClient = chatClient.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
        this.chatMemory = chatMemory;
        this.flightAssistantToolCalling = flightAssistantToolCalling;
    }

    @Override
    public String chatWithAi(final String query, final String conversationId) {
        if (query == null || conversationId == null) {
            throw new IllegalArgumentException("Query and ConversationId cannot be null");
        }

        ChatResponse chatResponse = chatClient.prompt().system("""
                        You are a helpful flight  assistant with the following capabilities:
                        1. Book flight for customer with flight id and passenger Name and passenger Email,  using 'bookFlight'
                        2. Cancel flight booking by flightId and passenger email using 'cancelFlight'
                        3. Get flight bookings by email using 'findBookingByEmailAi' 
                        4. Get all flights using but give only the flight number and flight destination getAvailableFlights
                        
                           Guidelines:
                           - Always use the appropriate tool for flight bookings operations
                           - If a request is not about flights and flight bookings, politely explain that you can only help with flight like bookings , cancel bookings , show bookings, and show available flights
                           - When displaying flights, present them in a clear, organized manner
                           - Confirm successful operations with brief, clear messages
                        """).user(query)

                .tools(flightAssistantToolCalling).
                options(OpenAiChatOptions.builder().
                        temperature(0.2).maxTokens(1000).
                        build()).advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId)).call().chatResponse();

        return chatResponse.getResult().getOutput().getText();
    }

}
