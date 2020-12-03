package Messenger;

import java.net.InetAddress;
import Messenger.Foundation.Environment;
import Messenger.Foundation.Models.User;

// TODO : TO DELETE
public class LauncherTest {

    public static void main(String[] args) throws Exception {
        Messenger msn = new Messenger() ;
        msn.start() ;

        InetAddress ip = InetAddress.getByName("192.168.1.1") ;
        User target = new User(ip) ;
        target.setPseudo("André") ;
        Environment.getNetworkInterface().getEnvoyer().sendRequestMeeting(target) ;
    }

}
