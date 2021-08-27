package restClients;

import entities.User;
import org.springframework.web.reactive.function.client.WebClient;

import static constants.UserConstants.GET_USER_BY_EMAIL_V1;

public class UserRestClient {

    private WebClient webClient;

    public UserRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public User retriveUserByEmail(String email) {
        return webClient.get().uri(GET_USER_BY_EMAIL_V1, email)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
