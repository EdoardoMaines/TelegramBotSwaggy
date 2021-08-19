package restClients;

import entities.Currency;
import org.springframework.web.reactive.function.client.WebClient;

import static constants.CurrencyConstants.*;

public class CurrencyRestClient {

    private WebClient webClient;

    public CurrencyRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Currency retriveCurrencyBySymbol(String symbol, String quote) {
        return webClient.get().uri(GET_CURRENCY_BY_SYMBOL_V1, symbol, quote)
                .retrieve()
                .bodyToMono(Currency.class)
                .block();
    }
}
