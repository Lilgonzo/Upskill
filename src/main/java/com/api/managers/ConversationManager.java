package com.api.managers;

import com.api.entities.Conversation;
import com.api.entities.Message;
import com.api.entities.Profile;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ConversationManager {

    /**
     * Returns the conversation messages for the conversation.
     * @param convoId the conversation
     * @return the conversation with all the messages
     * @throws Exception e
     */
    public Conversation findConversationMessages(Integer convoId) throws Exception {
        String sql =
                "select message, timestamp, username from conversation c " +
                        "join message m using (conversationID) " +
                        "join profile p on m.fromUserId = p.userID " +
                        "where conversationID=" + convoId;

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeQuery(sql);

            ResultSet resultSet = statement.getResultSet();

            Conversation conversation = new Conversation();
            List<Message> messageList = new LinkedList<>();
            Message message;
            Profile fromUser;
            while (resultSet.next()) {
                message = new Message();
                message.setMessage(resultSet.getString("message"));
                message.setTimeStamp(resultSet.getTimestamp("timestamp"));

                fromUser = new Profile();
                fromUser.setUsername(resultSet.getString("username"));
                message.setFromUser(fromUser);

                messageList.add(message);
            }
            conversation.setMessageList(messageList);

            return conversation;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Deletes convo.
     * @param securityContext the users convo to delete
     * @throws Exception e
     */
    public void deleteConversation(SecurityContext securityContext, Integer convoId) throws Exception {
        String sql =
                "delete from conversation where conversationID=" + convoId + " and userId=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Sends a message
     * @param securityContext user
     * @param message message
     * @return the message
     * @throws Exception e
     */
    public Message sendMessage(SecurityContext securityContext, Message message) throws Exception {
        String insertMessageSql =
                "insert into message set conversationID=?, message=?, fromUserId=?";

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertMessageSql)
                ) {

            preparedStatement.setInt(1, message.getConversation().getConversationID());
            preparedStatement.setString(2, message.getMessage());
            preparedStatement.setInt(3, Integer.parseInt(securityContext.getUserPrincipal().getName()));
            preparedStatement.execute();
        } catch (Exception e) {
            throw new Exception(e);
        }

        return message;
    }
}