package entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    String id;
    String userId;
    String walletType;
    String address;
    double balance;

    @Override
    public String toString() {
        return id + " " + userId + " " + address +  " " + walletType + " " + balance + " ";
    }

}
