package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    private Connection connection;
    public AccountDAO(Connection connection){
        this.connection = connection;
    }

    public AccountDAO() {
        
    }

    //A method to check, if user already exit
    public boolean userExists(String username){
        String qry = "SELECT COUNT(*) FROM Account WHERE username = ?";
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(qry)){
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Register account
    public Account registerAccount(Account account){
        if(userExists(account.getUsername())){
            return null; //User already exist
        }
        String sql = "INSERT INTO Account (username, password) VALUES(?, ?)";
        
        try (Connection con = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0){
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                account.setAccount_id(rs.getInt(1));
                return account;
                }
            }
        } catch (SQLException e) {
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
