package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    String id;
    String userId;
    String type;
    double balance;

    @Override
    public String toString() {
        return id + " " + userId + " " + type + " " + balance + " ";
    }
}
