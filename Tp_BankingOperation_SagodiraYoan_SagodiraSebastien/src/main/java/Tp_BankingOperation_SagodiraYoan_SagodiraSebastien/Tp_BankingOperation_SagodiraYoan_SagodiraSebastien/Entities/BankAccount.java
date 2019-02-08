package Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities;

import org.springframework.stereotype.Service;
import javax.persistence.*;

@Entity
@Service
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String iban;
    private String accountType;
    private double interestRate;
    private String costs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public BankAccount() {
    }

    public BankAccount(String iban, String accountType, double interestRate, String costs) {
        this.iban = iban;
        this.accountType = accountType;
        this.interestRate = interestRate;
        this.costs = costs;
    }
}
