package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO  = new AccountDAO();
    }

    //Register account
    public Account register(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    //Login account
    public Account login(String username, String password){
        return accountDAO.login(username, password);
    }
    
}
