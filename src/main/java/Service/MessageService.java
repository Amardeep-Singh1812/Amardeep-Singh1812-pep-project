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
