package Messenger.Network.Tasks.Listeners;

import java.net.Socket;
import java.io.IOException;
import Messenger.GUI.Screens.uiWindow;
import Messenger.Foundation.System.Env;
import Messenger.GUI.Subscreens.uiDiscussion;
import Messenger.Network.Models.MessagePacket;
import Messenger.Foundation.Models.Conversation;
import Messenger.Foundation.Models.Messages.Message;
import Messenger.GUI.Layout.Concerns.VerticalBarType;
import Messenger.GUI.Layout.Items.Discussion.uiDiscussionBar;
import Messenger.Network.Tasks.Listeners.Concerns.ServerListener;
import Messenger.GUI.Exceptions.ConversationItemNotFoundException;

/**
 * @author Damien MOLINA
 */
public class ReceiveListener extends ServerListener<MessagePacket> {

    /**
     * Make a new listener instance.
     *
     * @param port : listening port.
     */
    public ReceiveListener(int port) throws IOException {
        super(port) ;
    }

    /**
     * Determine whether the packet should
     * be managed.
     *
     * @param packet : packet to manage.
     * @return True if the packet should be managed,
     * False otherwise.
     */
    protected boolean shouldManagePacket(MessagePacket packet) {
        return Env.userController().hasUser(packet.getSourceUser()) ;
    }

    /**
     * Manage the received packet instance.
     *
     * @param socket : packet's socket.
     * @param packet : received packet.
     */
    protected void manageReceivedPacket(Socket socket, MessagePacket packet) {
        Message message   = packet.getData() ;
        uiWindow uiWindow = (uiWindow) Env.getApplication().getGraphicFrame().getScreen() ;

        try {
            uiDiscussionBar bar = (uiDiscussionBar) uiWindow.getVerticalBar(VerticalBarType.DISCUSSION) ;
            bar.updateFromUser(
                packet.getSourceUser(), packet.getData().getData()
            ) ;

            uiDiscussion discussion = (uiDiscussion) uiWindow.getRightSide().getSubScreen() ;
            Conversation conversation = discussion.getActiveConversation() ;

            if(conversation != null && conversation.getTarget().equals(packet.getSourceUser())) {
                discussion.addMessage(message) ;
            }
        } catch (ConversationItemNotFoundException e) {
            e.printStackTrace() ;
        }
    }

}
