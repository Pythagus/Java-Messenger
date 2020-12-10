package Messenger.Foundation.System;

import Messenger.Foundation.Application;
import Messenger.Foundation.Models.User;
import Messenger.Network.NetworkInterface;
import Messenger.Foundation.Controllers.Controller;
import Messenger.Foundation.Controllers.UserController;
import Messenger.Foundation.Providers.ControllerProvider;
import Messenger.Foundation.Controllers.ConversationController;

/**
 * @author Damien MOLINA
 */
public final class Env {

    // Forbid object instantiation.
    private Env() {}

    /**
     * Application instance.
     */
    private static Application application ;

    /**
     * Currently logged in user.
     */
    private static User user ;

    /**
     * Controller provider instance.
     */
    private static final ControllerProvider controller = new ControllerProvider() ;

    /**
     * Network interface instance.
     */
    private static NetworkInterface networkInterface ;

    /**
     * Get the current logged in user instance.
     *
     * @return the user instance if there is a
     *      logged in user, NULL otherwise.
     */
    public static User getUser() {
        return Env.user ;
    }

    /**
     * Set the user instance.
     *
     * @param user : the user instance.
     */
    public static void setUser(User user) {
        Env.user = user;
    }

    /**
     * Get the current application instance.
     *
     * @return the application instance.
     */
    public static Application getApplication() {
        return Env.application ;
    }

    /**
     * Set the current application instance.
     *
     * @param app : the application instance.
     */
    public static void setApplication(Application app) {
        Env.application = app ;
    }

    /**
     * Add a controller to the controllers list.
     *
     * @param controller : controller to add.
     */
    public static void addController(Controller controller) {
        Env.controller.add(controller) ;
    }

    /**
     * Get the UserController instance.
     *
     * @return the user controller instance.
     */
    public static UserController userController() {
        return (UserController) Env.controller.get(UserController.class) ;
    }

    /**
     * Get the ConversationController instance.
     *
     * @return the conversation controller instance.
     */
    public static ConversationController conversationController() {
        return (ConversationController) Env.controller.get(ConversationController.class) ;
    }

    /**
     * Get the network interface instance.
     *
     * @return the network interface instance.
     */
    public static NetworkInterface getNetworkInterface() {
        return Env.networkInterface ;
    }

    /**
     * Get the network interface instance.
     *
     * @param networkInterface : the network interface instance.
     */
    public static void setNetworkInterface(NetworkInterface networkInterface) {
        Env.networkInterface = networkInterface ;
    }

}
