package restClients;

import entities.Rent;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static constants.RentConstants.*;


public class RentRestClient {

    private WebClient webClient;

    public RentRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Rent> retriveRentByUserId(String userId) {
        return webClient.get().uri(GET_RENT_BY_USER_ID_V1, userId)
                .retrieve()
                .bodyToFlux(Rent.class)
                .collectList()
                .block();
    }


}
