package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }     

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this:: deletMessageById);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessageByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    //register user
    private void registerUser (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account createdAccount = accountService.register(account.getUsername(), account.getPassword());
        if(createdAccount == null){
            context.status(400);
        }else {
            context.json(createdAccount);
        }
    }

    //Login user
    private void loginUser (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
        if(loggedIn != null){
            context.json(loggedIn);
        }else {
            context.status(401);
        }
    }

    //Create a message
    private void createMessage (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message created = messageService.createMessage(message);
        if(created != null){
            context.json(created);
        }else {
            context.status(400);
        }
    }

    //get all messages
    private void getAllMessages(Context context) {
        context.json(messageService.getAllMessages());
    }

    //Retrive message by ID
    private void getMessageById(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message getMsgById = messageService.getMessageById(message_id);

        if(getMsgById != null){
            context.status( 200);
            context.json(getMsgById);
        }else {
            context.status(200);
        }
    }

    //Delete Message By Id
    public void deletMessageById(Context context){
        int del_id = Integer.parseInt(context.pathParam("message_id")); 
        Message del_msg = messageService.deletMessageById(del_id);
        if(del_msg != null){
            context.status( 200);
            context.json(del_msg);
        }else {
            context.status( 200);
        }
    }

    //Update message
    private void updateMessage(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = context.bodyAsClass(Message.class);

        if (messageService.updateMessage(id, message.getMessage_text())) {
            context.status(200);
            // Retrieve the updated message
            Message updatedMessage = messageService.getMessageById(id); 
            context.json(updatedMessage); 
        } else {
            context.status(400);
        }
    }
    
    // get message by userID
    private void getMessageByUser(Context context){
        int id = Integer.parseInt(context.pathParam("account_id"));
        try {
            List<Message> getMsgById = messageService.getAllMessagesByUser(id);
            if(getMsgById != null){
                context.json(getMsgById);
            }else {
                context.json("{}");
            }

        } catch (Exception e) {
            context.status(400).result("Invalid JSON format");
        }

    }



}