package org.example.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class IntegrationConfig {
    @Bean
    public IntegrationFlow fileWriting() {
        return IntegrationFlows.from("requestChannel")
                .handle(Files.outboundAdapter(new File("logs/request.txt"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true)).get();
    }
}
