package Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface BankingOperationRepository  extends JpaRepository<BankingOperation, Long>{

    List<BankingOperation> findByType(String type);
    List<BankingOperation> findByIbanSource(String ibanSource);
    List<BankingOperation> findByIbanDestination(String ibanDestination);
    List<BankingOperation> findByDate(Date date);
    //List<BankAccount> retrieveBankAccountByIban(String iban);
    //List<BankingOperation> retreiveAllBankingOperations();
}
