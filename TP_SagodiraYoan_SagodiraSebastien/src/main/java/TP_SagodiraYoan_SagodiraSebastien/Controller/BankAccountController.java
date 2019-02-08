package TP_SagodiraYoan_SagodiraSebastien.Controller;

import TP_SagodiraYoan_SagodiraSebastien.Entities.BankAccount;
import TP_SagodiraYoan_SagodiraSebastien.Entities.BankAccountRepository;
import TP_SagodiraYoan_SagodiraSebastien.Entities.BankingOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/BankAccount")
public class BankAccountController {

    @Autowired
    private Environment environment;

    @Autowired
    private BankAccountRepository repository;

    @GetMapping("/byIban/{iban}")
    public BankAccount retrieveBankAccount(@PathVariable String iban){
        BankAccount bankAccount = repository.findByIban(iban);
        return bankAccount;
    }

    @GetMapping("/All")
    public List<BankAccount> retrieveAllBankAccount(){
        List<BankAccount> listBankAccount = repository.findAll();
        return listBankAccount;
    }

    @GetMapping("/delete/{id}")
    public boolean deleteBankAccount(@PathVariable long id){

        Optional<BankAccount> ba = repository.findById(id);

        if (!ba.isPresent()){
            return false;
        }else{
            repository.deleteById(id);
            return true;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount newBankAccount = repository.save(bankAccount);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBankAccount.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> putBankAccount(@RequestBody BankAccount bankAccount, @PathVariable long id) {

        Optional<BankAccount> ba = repository.findById(id);

        if (!ba.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            bankAccount.setId(id);
            repository.save(bankAccount);
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value="/BankingOperation/byIban/{iban}",method = {RequestMethod.GET})
    public List<BankingOperation> retrieveAllBankingOperationByIban(@PathVariable String iban) {


        List<BankingOperation> bankingOperations = new ArrayList<BankingOperation>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://192.168.99.100:8001/BankingOperation/byIbanSource/" + iban,List.class);
        ResponseEntity<List> responseEntity2 = restTemplate.getForEntity("http://192.168.99.100:8001/BankingOperation/byIbanDestination/" + iban,List.class);
        bankingOperations = responseEntity.getBody();
        bankingOperations.addAll(responseEntity2.getBody());


        return bankingOperations;
    }

    @RequestMapping(value="/transaction")
    public static ResponseEntity<Object> transaction(@RequestParam("ba1") String ba1,@RequestParam String ba2, @RequestParam double amount){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://192.168.99.100:8001/BankingOperation/transaction?ba1="+ba1+"&ba2="+ba2+"&amount="+amount+"50",List.class);

        restTemplate.postForLocation("http://192.168.99.100:8001/BankingOperation/transaction?ba1="+ba1+"&ba2="+ba2+"&amount="+amount+"50",List.class);

        return ResponseEntity.ok().build();
    }

    /*@RequestMapping(value="/BankingOperation/create",method = {RequestMethod.POST})
    public boolean retrieveBankAccountByIban(@RequestBody BankingOperation bankingOperation) {



        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.postForEntity("http://localhost:8001/BankingOperation/create/");
        System.out.println(responseEntity);
        bankAccount = responseEntity.getBody();
        System.out.println(bankAccount);

        return true;
    }*/

}
