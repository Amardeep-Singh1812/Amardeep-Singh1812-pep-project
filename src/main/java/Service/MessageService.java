package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.*;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * @param account_id The unique ID of the account whose messages are to be retrieved.
     * @return A list of messages posted by the user.
     */
    public List<Message> allMessageRetrieverForUserHandler(int account_id) {
        return messageDAO.getAllMessagesSentByParticularUser(account_id);
    }

    /**
     * Updates the text of a message by its ID.
     * @param newMessageText A Message object containing the new text.
     * @param message_id The unique ID of the message to be updated.
     * @return The updated Message object if the update was successful, otherwise null.
     */
    public Message updateMessageById(Message newMessageText, int message_id) {
        String messageText = newMessageText.getMessage_text();
        if (messageText.isEmpty() || messageText.length() > 255) {
            return null;
        }
        Message existingMessage = messageDAO.getMessageById(message_id);
        if (existingMessage != null) {
            if (messageDAO.updateMessageById(message_id, messageText)) {
                existingMessage.setMessage_text(messageText);
                return existingMessage;
            }
        }
        return null;
    }

    /**
     * Deletes a message from the database by its ID.
     * @param message_id The unique ID of the message to be deleted.
     * @return The deleted Message object if found and deleted, otherwise null.
     */
    public Message deleteMessageById(int message_id) {
        Message existingMessage = messageDAO.getMessageById(message_id);
        if (existingMessage != null) {
            messageDAO.deleteMessageById(message_id);
            return existingMessage;
        }
        return null;
    }

    /**
     * Retrieves a message by its ID.
     * @param message_id The unique ID of the message to be retrieved.
     * @return The Message object if found, otherwise null.
     */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    /**
     * Retrieves all messages from the database.
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Inserts a new message into the database.
     * @param message The Message object to be inserted.
     * @return The inserted Message object if successful, otherwise null.
     */
    public Message insertMessage(Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        boolean isValidUser = messageDAO.findAccountById(message.getPosted_by());
        if (isValidUser) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }
}
