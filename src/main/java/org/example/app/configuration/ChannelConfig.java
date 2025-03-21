package org.example.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Канал, через который будут приниматься сообщения от пользователя
 */
@Configuration
public class ChannelConfig {
   @Bean
    public MessageChannel requestChannel() {
        return new DirectChannel();
    }
}
