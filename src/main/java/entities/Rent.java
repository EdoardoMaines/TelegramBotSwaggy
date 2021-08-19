package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    private final static BigDecimal DIVISION_FACTOR = BigDecimal.valueOf(100000000);

    String id;
    String userId;
    String machineName;
    String miningStartDate;
    String miningEndDate;
    double fraction;
    BigDecimal production;

    @Override
    public String toString() {
        return "MachineName: '" + machineName + "\n" +
                "MiningStartDate: '" + miningStartDate + "\n" +
                "MiningEndDate: '" + miningEndDate + "\n" +
                "Fraction: " + fraction + "\n" +
                "Production: " + production.divide(DIVISION_FACTOR) + "BTC";
    }
}
