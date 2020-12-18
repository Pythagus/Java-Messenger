package fr.insa.messenger.network.listeners.handlers;

import fr.insa.messenger.models.User;
import fr.insa.messenger.ui.GraphicInterface;
import fr.insa.messenger.system.console.Console;
import fr.insa.messenger.ui.screens.DiscussionScreen;
import fr.insa.messenger.ui.screens.utils.BarType;
import fr.insa.messenger.ui.screens.utils.ContentType;
import fr.insa.messenger.ui.screens.discussions.DiscussionBar;
import fr.insa.messenger.ui.screens.discussions.DiscussionList;

/**
 * @author Damien MOLINA
 */
public class QuitHandler {

    /**
     * Run the listener.
     */
    public void handle(User user) {
        Console.comment("=> Conversation left with : " + user.getPseudo()) ;

        DiscussionBar bar = GraphicInterface.instance().discussionBar() ;

        if(bar != null) {
            DiscussionList list     = bar.getList() ;
            DiscussionScreen screen = (DiscussionScreen) list.getFrame().getContent(ContentType.DISCUSSION) ;

            // Update the content.
            screen.setConversation(null) ;
            list.getFrame().showContent(ContentType.WELCOME) ;
            list.getFrame().getBar(BarType.CONTACTS).getList().addItem(user, false) ;
            list.removeItem(user) ;
            list.clearSelection() ;
        }
    }

}
