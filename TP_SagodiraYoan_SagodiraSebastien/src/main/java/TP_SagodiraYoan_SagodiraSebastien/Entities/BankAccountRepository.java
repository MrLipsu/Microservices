package TP_SagodiraYoan_SagodiraSebastien.Entities;

import TP_SagodiraYoan_SagodiraSebastien.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByIban(String iban);
    List<BankAccount> findAll();
    //void deleteBankAccount(long id);
    //ResponseEntity<Object> createBankAccount(BankAccount bankAccount);
    //ResponseEntity<Object> putBankAccount(BankAccount bankAccount, long id);
}


