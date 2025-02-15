package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account registerAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO Account (username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                account.setAccount_id(rs.getInt("account_id"));
                return account;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

// Login account
    public Account login(String username, String password){
    String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
    try (Connection conn = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    



}
