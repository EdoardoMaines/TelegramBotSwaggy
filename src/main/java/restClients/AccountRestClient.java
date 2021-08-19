package restClients;

import entities.Account;
import entities.Currency;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static constants.AccountConstants.GET_BALANCE_BY_USERID_V1;

public class AccountRestClient {
    private WebClient webClient;

    public AccountRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Account> retriveAccountById(String userId, String type) {
        return webClient.get().uri(GET_BALANCE_BY_USERID_V1, userId, type)
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList()
                .block();
    }
}
