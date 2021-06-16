package pojos.bank;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Customer implements Comparator<Customer> {

    private LocalDateTime accountOpenDate;
    private Boolean active;
    private String address;
    private String fullName;
    private Integer id;
    private Boolean isActive;
    private List<Account> accounts;

    @Override
    public int compare(Customer customer1, Customer customer2){
        return customer1.fullName.compareTo(customer2.fullName);
    }

}
