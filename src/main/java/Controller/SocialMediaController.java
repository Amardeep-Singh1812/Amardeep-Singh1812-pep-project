package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller class responsible for handling API requests for the social media application.
 * This class defines various endpoints related to account management and messaging services.
 */
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * Initializes the Javalin application and sets up API endpoints.
     *
     * @return a Javalin app object that defines the behavior of the controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messageHandler);
        app.get("/messages", this::allMessageRetrieverHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::allMessageRetrieverForUserHandler);
        return app;
    }

    /**
     * Handles user registration by creating a new account.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.insertAccount(account);
        if (addedAccount != null) {
            ctx.status(200).json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handles user login by verifying existing account credentials.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void loginHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountService.loginToExistingAccount(account);
        if (login != null) {
            ctx.status(200).json(mapper.writeValueAsString(login));
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handles message creation by inserting a new message.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void messageHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.insertMessage(message);
        if (addedMessage != null) {
            ctx.status(200).json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Retrieves all messages from the database.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void allMessageRetrieverHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200).json(messages);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.status(200).json(message);
        } else {
            ctx.status(404);
        }
    }

    /**
     * Deletes a message by its ID.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage != null) {
            ctx.status(200).json(deletedMessage);
        } else {
            ctx.status(404);
        }
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void allMessageRetrieverForUserHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.allMessageRetrieverForUserHandler(accountId);
        ctx.status(200).json(messages);
    }

    /**
     * Updates a message's content by its ID.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void updateMessageHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessageText = mapper.readValue(ctx.body(), Message.class);
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(newMessageText, messageId);
        if (updatedMessage != null) {
            ctx.status(200).json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Example handler for a sample endpoint.
     *
     * @param ctx The Javalin Context object that manages the HTTP request and response.
     */
    private void exampleHandler(Context ctx) {
        ctx.json("Sample text");
    }
}
