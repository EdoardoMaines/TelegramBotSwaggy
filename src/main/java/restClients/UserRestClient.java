package restClients;

import entities.Authentication;
import entities.User;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public JSONObject retriveAtheByJson() {


        String requestBody = "'{" +
                "  \"username\": \"mainesedoardo@gmail.com\"," +
                "  \"password\": \"Tirocinio99Swag!\"," +
                "  \"skipOtpCheck\": true" +
                "}'";

        Authentication authentication = new Authentication();

//        return webClient.post()
//                .uri("http://localhost:8444/public/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .onStatus(HttpStatus::isError, clientResponse -> {
//                    return Mono.error(new Exception("error"));
//                })
//                .bodyToMono(JSONObject.class);

        JSONObject json = WebClient.builder().build()
                .post()
                .uri("http://localhost:8444/public/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), Authentication.class)
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(authentication), Authentication.class)
//                .body(BodyInserters.fromValue(authentication))
//                .body(Mono.just(authentication), Authentication.class)
//                .bodyValue(authentication)
//                .retrieve()
//                .bodyToMono(JSONObject.class)
//                .block();

         return json;
    }
}
