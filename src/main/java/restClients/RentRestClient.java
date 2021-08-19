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

    //ESEMPIO ID USER: 38e342d2-5c96-4096-b7da-61c846c3df73
    //ESEMPIO ID RENT: 41452e0a-fe52-4621-93cb-9c8c28bc5134
    //ESEMPIO ID MACHINE: fe08da47-8f64-4f7b-b5f4-7e99dd4ab33d

    public List<Rent> retriveRentByUserId(String userId) {
        return webClient.get().uri(GET_RENT_BY_USER_ID_V1, userId)
                .retrieve()
                .bodyToFlux(Rent.class)
                .collectList()
                .block();
    }


}
