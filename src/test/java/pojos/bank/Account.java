package pojos.bank;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Account {

    private String accountType;
    private Double balance;
    private Customer customer;
    private List<Transaction> transactions;


}
