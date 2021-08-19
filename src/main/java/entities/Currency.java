package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    String symbol;
    String quote;
    double rate;

    @Override
    public String toString() {
        return symbol + " " + quote + " " + rate + " ";
    }
}
