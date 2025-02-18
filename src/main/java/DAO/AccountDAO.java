package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

/**
 * Data Access Object (DAO) class for managing Account-related database operations.
 */
public class AccountDAO {
    
    /**
     * Inserts a new user into the account table.
     * 
     * @param account The account object containing user details.
     * @return The inserted account with the generated account ID, or null if insertion fails.
     */
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                account.setAccount_id(rs.getInt(1));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an account by username.
     * 
     * @param username The username to search for.
     * @return An account object identified by the given username, or null if not found.
     */
    public Account findAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
