package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import net.bytebuddy.utility.visitor.ContextClassVisitor;

import java.util.List;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messageHandler);
        app.get("/messages", this::allMessageRetrieverHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        return app;
    }

    /**
     * This is an Register handler for an Account Registration.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);  
        Account addAccount = accountService.insertAccount(account);
        if(addAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addAccount));
        }
        else {
            ctx.status(400);
        }
    }

    /**
     * This is an Login handler for an Logging the esisting user account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountService.LoginToExistingAccount(account);
        if(login!= null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(login));
        }
        else{
            ctx.status(401);
        }
    }

    /**
     * This is an Message handler for .
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void messageHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addMessage = messageService.insertMessage(message);
        if(addMessage!=null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addMessage));
        }
        else {
            ctx.status(400);
        }
    }

    /**
     * This is an Message handler to Retrieve all messages .
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void allMessageRetrieverHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200);
        ctx.json(messages);
    }

    /**
     * This is an Message handler to Retrieve messages by Id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void  getMessageByIdHandler(Context ctx) {
        // Write Code
    }

    /**
     * This is an Message handler to Delete messages by Id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void  deleteMessageByIdHandler(Context ctx) {
        // Write Code
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}