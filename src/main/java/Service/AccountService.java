package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    static AccountDAO accountDAO;

    public AccountService(){
        accountDAO  = new AccountDAO();
    }

    public static Account register(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        }
        return accountDAO.registerAccount(account);
        
    }
    
}
