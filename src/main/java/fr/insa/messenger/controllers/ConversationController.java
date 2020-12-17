package fr.insa.messenger.controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import fr.insa.messenger.system.Env;
import fr.insa.messenger.models.User;
import fr.insa.messenger.models.Conversation;
import fr.insa.messenger.models.messages.Message;
import fr.insa.messenger.network.NetworkInterface;
import fr.insa.messenger.database.tables.MessageTable;
import fr.insa.messenger.network.utils.BroadcastSplitter;

/**
 * Handle the multiple discussions created.
 *
 * @author Maud PENNETIER, Damien MOLINA
 */
public class ConversationController extends Controller {

    /**
     * Singleton instance.
     */
    private static final ConversationController INSTANCE = new ConversationController() ;

    /**
     * Conversations list.
     */
    private static final ArrayList<Conversation> conversations = new ArrayList<>() ;

    /**
     * Get the ConversationController singleton instance.
     *
     * @return the singleton instance.
     */
    public static ConversationController instance() {
        return ConversationController.INSTANCE ;
    }

    /**
     * Get the conversation belonged to
     * the given user.
     *
     * @param user : targeted user.
     * @return the conversation instance, null otherwise.
     */
    public Conversation getConversation(User user) {
        for(Conversation conversation : ConversationController.conversations) {
            if(conversation.getTarget().equals(user)) {
                return conversation ;
            }
        }

        return null ;
    }

    /**
     * Add a message into the conversation.
     *
     * @param user : targeted user.
     * @param message : message to add.
     */
    public void addConversationMessage(User user, Message message) {
        this.getConversation(user).addMessage(message) ;
    }

    /**
     * Add a new conversation with the
     * given user.
     *
     * @param user : targeted user.
     */
    public void addConversation(User user) {
        Conversation conversation = new Conversation(user) ;

        // Get the conversation historic.
        conversation.addMessages(this.getHistoric(user)) ;

        ConversationController.conversations.add(conversation) ;
    }

    /**
     * Start new discussion.
     *
     * @param user : user with whom the conversation is held.
     */
    public void start(User user) {
        NetworkInterface.instance().getEnvoyer().sendRequestMeeting(user) ;
    }

    /**
     * Stop existing discussion.
     *
     * @param user : user with whom the conversation is held.
     */
    public void stop(User user) {
        // TODO: call network.

        ConversationController.conversations.removeIf(
            conversation -> conversation.getTarget().equals(user)
        ) ;
    }


    /**
     * Get the older messages.
     *
     * @param user : user with whom the conversation is held.
     */
    private Message[] getHistoric(User user) {
        ArrayList<Message> messages = new ArrayList<>() ;

        try {
            new MessageTable().select(Env.getUser(), user).forEach((ResultSet r) -> {
                try {
                    messages.add(MessageTable.toMessage(r)) ;
                } catch (Exception e) {
                    e.printStackTrace() ;
                    return false ;
                }

                return true ;
            }) ;
        } catch (SQLException a) {
            a.printStackTrace() ;
        }

        return messages.toArray(new Message[]{}) ;
    }


    /**
     * Send message to next user, nexthop network classes.
     *
     * @param message : content of the message.
     */
    public void send(Message message) {
        NetworkInterface.instance().getEnvoyer().sendMessage(message) ;

        try {
            new MessageTable().insert(message) ;
        } catch(SQLException e) {
            e.printStackTrace() ;
        }
    }

    /**
     * Determine whether the given text
     * is valid.
     *
     * @param text : submitted text.
     * @return True or False.
     */
    public boolean isValidText(String text) {
        return text.length() > 0 && ! text.contains(BroadcastSplitter.DELIMITER) ;
    }

}