package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.*;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO MessageDAO) {
        this.messageDAO = MessageDAO;
    }

    
    /**
     * @return The Messages matched with id.
     */
    public List<Message> allMessageRetrieverForUserHandler(int account_id) {
        return messageDAO.getAllMessagesSentByParticularUser(account_id);
    }

    /**
     * @param1 newMessageText an Message object
     * @param2 Message Id
     * @return The Message which is Updated using id.
     */
    public Message UpdateMessageById(Message newMessageText, int message_id) {
        String messageText = newMessageText.getMessage_text();
        if(messageText == "" || messageText.length() > 255){
            return null;
        }
        Message messageExistOrNot = messageDAO.getMessageById(message_id);
        if(messageExistOrNot != null) {
            if(messageDAO.UpdateMessageById(message_id, messageText)) {
                messageExistOrNot.setMessage_text(messageText);
                return messageExistOrNot;
            }
        }
        return null;
    }

    /**
     * @param Message Id
     * @return The Message which is deleted using id.
     */
    public Message DeleteMessageById(int message_id) {
        Message messageExistOrNot = messageDAO.getMessageById(message_id);
        if(messageExistOrNot != null) {
            messageDAO.DeleteMessageById(message_id);
            return messageExistOrNot;
        }
        return null;
    }

    /**
     * @return The Messages matched with id.
     */
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    /**
     * @return The List of messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * @param Message an message object.
     * @return The persisted message if the persistence is successful.
     */
    public Message insertMessage(Message message) {
       if(message.getMessage_text() == "" || message.getMessage_text().length() > 255) {
            return null;
       }
       Boolean checkForValidUser = messageDAO.findAccountById(message.getPosted_by());
       if(checkForValidUser) {
            return messageDAO.insertMessage(message);
       }
       return null; 
    }
}
