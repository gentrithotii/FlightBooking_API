package se.lexicon.flightbooking_api.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("se.lexicon.*")
public class AppConfig {

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository){
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }

//    @Bean
//    public ChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate){
//        return JdbcChatMemoryRepository.builder()
//                .jdbcTemplate(jdbcTemplate)
//                .dialect(new MysqlChatMemoryRepositoryDialect())
//                .build();
//    }
}
