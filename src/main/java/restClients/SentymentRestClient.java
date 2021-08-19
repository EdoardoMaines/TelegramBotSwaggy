package restClients;

import entities.Sentyment;
import org.springframework.web.reactive.function.client.WebClient;

import static constants.SentymentConstants.GET_SENTYMENT_BY_ID_V1;

public class SentymentRestClient {

    private WebClient webClient;

    public SentymentRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Sentyment retriveSentymentById(String Id) {
        return webClient.get().uri(GET_SENTYMENT_BY_ID_V1, Id)
                .retrieve()
                .bodyToMono(Sentyment.class)
                .block();
    }

}
