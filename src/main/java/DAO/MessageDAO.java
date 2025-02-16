package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;
import java.util.*;

public class MessageDAO {

    //Create message
    public Message createMessage(Message message){
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?) RETURNING message_id";
        
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            // preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                message.setMessage_id(rs.getInt("message_id"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
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
}
    


    

