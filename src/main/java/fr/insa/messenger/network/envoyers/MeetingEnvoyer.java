package fr.insa.messenger.network.envoyers;

import java.net.Socket;
import java.io.IOException;
import java.net.ConnectException;
import fr.insa.messenger.system.Env;
import fr.insa.messenger.models.User;
import java.net.NoRouteToHostException;
import fr.insa.messenger.network.Envoyer;
import fr.insa.messenger.network.NetworkInterface;
import fr.insa.messenger.network.models.MeetingPacket;
import fr.insa.messenger.network.listeners.MeetingResponseListener;
import fr.insa.messenger.network.listeners.handlers.DeniedHandler;
import fr.insa.messenger.network.listeners.handlers.AcceptedHandler;

/**
 * @author Damien MOLINA
 */
public class MeetingEnvoyer extends BaseEnvoyer {

    /**
     * Generated meeting packet.
     */
    private final MeetingPacket packet ;

    /**
     * Make a new Meeting envoyer instance.
     *
     * @param envoyer : envoyer instance.
     * @param user : user to connect with.
     * @param state : packet state.
     */
    public MeetingEnvoyer(Envoyer envoyer, User user, MeetingPacket.State state) {
        super(envoyer, user) ;

        // Make the meeting packet.
        this.packet = new MeetingPacket(Env.getUser(), user) ;
        this.packet.setState(state) ;
    }

    /**
     * Make the sending.
     */
    protected void send() throws IOException {
        DeniedHandler deniedCallback = new DeniedHandler() ;

        try {
            // Make the socket.
            Socket socket = new Socket(
                this.packet.getDestinationAddress(), NetworkInterface.MEETING_PORT
            ) ;

            this.envoyer.send(
                socket, this.packet, false
            ) ;

            if(this.packet.isWaitingForResponse()) {
                // Start the listener at the given port.
                MeetingResponseListener listener = new MeetingResponseListener(socket) ;
                listener.setCallbacks(
                    new AcceptedHandler(), deniedCallback
                ) ;
                listener.start() ;
            }
        }

        /*
         * This exception is thrown when the
         * user is not reachable in the network.
         */
        catch (NoRouteToHostException | ConnectException e) {
            deniedCallback.unreachableUser(e, this.user) ;
        }
    }

}
