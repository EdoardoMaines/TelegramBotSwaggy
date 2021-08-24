package config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotConfig {

    @Value("${bot.userName}")
    public String botUserName;
    

    @Value("${bot.token}")
    public String botToken;

//    public String getBotUserName() {
//        return botUserName;
//    }
//
//    public String getBotToken() {
//        return botToken;
//    }
}
