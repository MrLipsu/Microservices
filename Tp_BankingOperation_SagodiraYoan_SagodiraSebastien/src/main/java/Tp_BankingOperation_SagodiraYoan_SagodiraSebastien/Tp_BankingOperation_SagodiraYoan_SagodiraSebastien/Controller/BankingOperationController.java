package Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Controller;

import Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities.BankAccount;
import Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities.BankingOperation;
import Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Tp_BankingOperation_SagodiraYoan_SagodiraSebastien.Entities.BankingOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;

@RestController
@RequestMapping("/BankingOperation")
public class BankingOperationController {

    //initialisation d'un objet de l'interface BankingOperationRepository
    @Autowired
    private BankingOperationRepository bankingOperationRepository;

    //Méthode find all Banking operation
    @GetMapping("/all")
    public List<BankingOperation> retreiveAllBankingOperations() {

        //Appel à la méthode findAll de JPA
        List<BankingOperation> bankingOperations = bankingOperationRepository.findAll();
        return bankingOperations;
    }

    @GetMapping("/{id}")
    public BankingOperation findBankingOperationById(long id) {

        //Appel à la méthode find JPA
        Optional<BankingOperation> bankingOperation = bankingOperationRepository.findById(id);

        return bankingOperation.get();
    }

    @GetMapping("/delete/{id}")
    public boolean deleteBankingOperation(@PathVariable long id) {

        Optional<BankingOperation> ba = bankingOperationRepository.findById(id);

        if (!ba.isPresent()) {
            return false;
        } else {
            bankingOperationRepository.deleteById(id);
            return true;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBankingOperation(@RequestBody BankingOperation bankingOperation) {
        BankingOperation newBankingoperation = bankingOperationRepository.save(bankingOperation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBankingoperation.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> putBankingOperation(@RequestBody BankingOperation bankingOperation, @PathVariable long id) {

        Optional<BankingOperation> ba = bankingOperationRepository.findById(id);

        if (!ba.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            bankingOperation.setId(id);
            bankingOperationRepository.save(bankingOperation);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byType/{type}")
    public List<BankingOperation> findByType(@PathVariable String type) {
        List<BankingOperation> bankingOperations = bankingOperationRepository.findByType(type);
        return bankingOperations;
    }

    @GetMapping("/byIbanSource/{ibanSource}")
    public List<BankingOperation> findByIbanSource(@PathVariable String ibanSource) {
        List<BankingOperation> bankingOperations = bankingOperationRepository.findByIbanSource(ibanSource);
        return bankingOperations;
    }

    @GetMapping("/byIbanDestination/{ibanDestination}")
    public List<BankingOperation> findByIbanDestination(@PathVariable String ibanDestination) {
        List<BankingOperation> bankingOperations = bankingOperationRepository.findByIbanDestination(ibanDestination);
        return bankingOperations;
    }

    @GetMapping("/byDate/{date}")
    public List<BankingOperation> findByDate(@PathVariable String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = date;
        List<BankingOperation> bankingOperations = null;
        try {
            Date date1 = formatter.parse(dateInString);
            System.out.println(date1);
            System.out.println(formatter.format(date1));
            bankingOperations = bankingOperationRepository.findByDate(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bankingOperations;
    }


    @RequestMapping(value="/BankAccount/All",method = {RequestMethod.GET})
    public List<BankAccount> retrieveAllBankAccount() {


        List<BankAccount> bankAccounts = new ArrayList<BankAccount>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://192.168.99.100:8000/BankAccount/All",List.class);

        bankAccounts = responseEntity.getBody();

        return bankAccounts;
    }

    @RequestMapping(value="/BankAccount/findByIban/{iban}",method = {RequestMethod.GET})
    public BankAccount retrieveBankAccountByIban(@PathVariable String iban) {

        System.out.println(iban);
        BankAccount bankAccount;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BankAccount> responseEntity = restTemplate.getForEntity("http://192.168.99.100:8000/BankAccount/byIban/" + iban, BankAccount.class);
        System.out.println(responseEntity);
        bankAccount = responseEntity.getBody();
        System.out.println(bankAccount);

        return bankAccount;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> transaction(@RequestParam("ba1") String ba1, @RequestParam("ba2") String ba2, @RequestParam("amount") double amount) {
        String iban1 = ba1;
        String iban2 = ba2;
        Date date = new Date();

        BankingOperation bo1 = new BankingOperation("transaction", iban1, iban2, -amount, date);
        BankingOperation bo2 = new BankingOperation("transaction", iban2, iban1, amount, date);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bo1.getId()).toUri();

        bankingOperationRepository.save(bo1);
        bankingOperationRepository.save(bo2);

        return ResponseEntity.ok().build();

    }
}
