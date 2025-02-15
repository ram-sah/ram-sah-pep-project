package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
   private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO  = new AccountDAO();
    }

    //Register account
    public Account register(String username, String password){
        if(username == null || username.trim().isBlank() || password.length() < 4){
            return null;
        }
        if(accountDAO.userExists(username)){
            return null;
        }
        return accountDAO.registerAccount(new Account(0, username, password));
    }

    //Login account
    public Account login(String username, String password){
        return accountDAO.login(username, password);
    }
    
}
