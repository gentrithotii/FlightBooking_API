package se.lexicon.flightbooking_api.service;

public interface AiChatService {
    String chatMemory(final String query, final String conversationId);
}
