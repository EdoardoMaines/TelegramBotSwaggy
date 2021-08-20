package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class BotConfig {

    @Value("${bot.userName}")
    public String botUserName;

    @Value("${bot.token}")
    public String botToken;
}
