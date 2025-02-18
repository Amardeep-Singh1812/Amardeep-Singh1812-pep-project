package Service;

import Model.Account;
import DAO.AccountDAO;

/**
 * Service class responsible for handling business logic related to account management.
 * It interacts with the AccountDAO to perform account-related operations such as 
 * user registration and authentication.
 */
public class AccountService {
    private final AccountDAO accountDAO;

    /**
     * Default constructor that initializes AccountDAO.
     */
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Constructor for dependency injection, allowing for custom AccountDAO instances.
     *
     * @param accountDAO The data access object for account operations.
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Authenticates a user by verifying their username and password.
     *
     * @param account The account object containing the username and password.
     * @return The authenticated Account object if credentials are valid, otherwise null.
     */
    public Account loginToExistingAccount(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().isEmpty()) {
            return null; // Return null if username or password is empty
        }

        Account existingAccount = accountDAO.findAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount; // Return the account if authentication is successful
        }

        return null; // Return null if authentication fails
    }

    /**
     * Registers a new account if the username is unique and the password meets the criteria.
     *
     * @param account The account object containing user credentials.
     * @return The persisted Account object if registration is successful, otherwise null.
     */
    public Account insertAccount(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null; // Username cannot be empty, and password must be at least 4 characters
        }

        Account existingAccount = accountDAO.findAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            return null; // Return null if the username already exists
        }

        return accountDAO.insertAccount(account); // Insert the new account
    }
}
