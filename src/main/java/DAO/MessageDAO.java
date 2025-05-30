package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;
import java.util.*;
import java.sql.Statement;

public class MessageDAO {

    //Create message
    public Message createMessage(Message message){
        String userSql = "SELECT 1 FROM Account WHERE ACCOUNT_ID = ? ";
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
        
        try (Connection conn = ConnectionUtil.getConnection()){

            //Check if user exists
            try(PreparedStatement userStmt = conn.prepareStatement(userSql)){
                userStmt.setInt(1, message.getPosted_by());
                try (ResultSet rs = userStmt.executeQuery()){
                    if (!rs.next()){
                        return null;
                    }
                }
            }
        
            //Insert message only if user exist 
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    message.setMessage_id(rs.getInt(1));
                    return message;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    // Get All Messages
    public List<Message> GetAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msg = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message receivedMsg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                msg.add(receivedMsg);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msg;
    }

    //Get Message by ID
    public Message getMessageById(int messageId) {
        String sql = "SELECT * FROM Message WHERE message_id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, messageId);

            ResultSet rs = prst.executeQuery();
            if(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Delete message
    public Message deletMessageById(int messageId){
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        String del_sql = "DELETE FROM Message WHERE message_id = ?";

        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement prsmt = conn.prepareStatement(sql);
            PreparedStatement delStmt = conn.prepareStatement(del_sql);) {

            prsmt.setInt(1, messageId);

            ResultSet rs = prsmt.executeQuery();
            if(rs.next()){
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    );
                    delStmt.setInt(1, messageId);
                    delStmt.executeUpdate();
                
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        return null;
    }

    //Update message
    public boolean updateMessage(int messageId, String newText) {
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newText);
            stmt.setInt(2, messageId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Retrive all message written by particular user
    public List<Message> getMessageByUserId(int id) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message WHERE posted_by = ?";
        try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }



}
    


    

