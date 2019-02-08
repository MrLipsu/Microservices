package Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities;

import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;

@Entity
@Service
public class BankingOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String ibanSource;
    private String ibanDestination;
    private Double amount;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIbanSource() {
        return ibanSource;
    }

    public void setIbanSource(String ibanSource) {
        this.ibanSource = ibanSource;
    }

    public String getIbanDestination() {
        return ibanDestination;
    }

    public void setIbanDestination(String ibanDestination) {
        this.ibanDestination = ibanDestination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BankingOperation() {
    }

    public BankingOperation(String type, String ibansource, String ibanDestination, Double amount, Date date) {
        this.type = type;
        this.ibanSource = ibansource;
        this.ibanDestination = ibanDestination;
        this.amount = amount;
        this.date = date;
    }
}
