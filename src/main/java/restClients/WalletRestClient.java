package restClients;

import entities.Account;
import entities.Wallet;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static constants.WalletConstants.GET_WALLET_BY_USERID_V1;

public class WalletRestClient {

    private WebClient webClient;

    public WalletRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Wallet> retriveWalletById(String userId, String walletType) {
        return webClient.get().uri(GET_WALLET_BY_USERID_V1, userId, walletType)
                .retrieve()
                .bodyToFlux(Wallet.class)
                .collectList()
                .block();
    }

}
