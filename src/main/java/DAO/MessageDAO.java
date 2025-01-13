package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Method to Update message using id from the database.
     * @Param message_id
     * @return Nothing.
     */
    public Boolean UpdateMessageById(int message_id, String message_text) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Message SET message_text = ? WHERE message_id =?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,message_text);
            pstmt.setInt(2,message_id);
            if(pstmt.executeUpdate() == 1) {
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Method to delete message using id from the database.
     * @Param message_id
     * @return Nothing.
     */
    public void DeleteMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,message_id);
            pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to retrieve all messages from the database.
     * @Param message_id
     * @return All Messages.
     */
    public Message getMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,message_id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
    }

    /*
     * Method to retrieve all messages from the database.
     * @return All Messages.
     */
    public List<Message> getAllMessagesSentByParticularUser(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,account_id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return messages;
    }

    /*
     * Method to retrieve all messages from the database.
     * @return All Messages.
     */
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return messages;
    }

    /* 
     * Method to Insert message in the database.
    */
     public Message insertMessage(Message message) {

        Connection conn = ConnectionUtil.getConnection();
        try{

            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                message.setMessage_id(rs.getInt(1));
                return message;
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
     }
     
    /**
     * Method to get account by username.
     * @return an account identified by username.
     */
    public Boolean findAccountById(int id){
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
