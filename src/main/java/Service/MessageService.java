package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    //Create message
    public Message createMessage(Message message){
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    //get all messages 
    public List<Message> getAllMessages(){
        return messageDAO.GetAllMessages();
    }

    //get message by ID
    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }


}
