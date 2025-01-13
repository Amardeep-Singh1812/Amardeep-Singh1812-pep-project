package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.*;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account LoginToExistingAccount(Account account) {
        if(account.getUsername() == "" || account.getPassword() == "") {
            return null;
        }
       Account checkAccount = accountDAO.findAccountByUsername(account.getUsername());
        if(checkAccount!= null && checkAccount.getPassword().equals(account.getPassword())) {
            return checkAccount;
        }
        return null;
    }

    /**
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account insertAccount(Account account) {
        if(account.getUsername() == "" || account.getPassword().length() < 4) {
            return null;
        }
        Account existingAccount = accountDAO.findAccountByUsername(account.getUsername());
        if(existingAccount!= null) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }
}
