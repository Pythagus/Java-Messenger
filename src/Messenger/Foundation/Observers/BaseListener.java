package Messenger.Foundation.Observers;

import Messenger.Foundation.Providers.ObserverProvider;
import Messenger.Foundation.Observers.Contracts.Listener;
import Messenger.Foundation.Observers.Contracts.Observable;

/**
 * @author Damien MOLINA
 */
abstract public class BaseListener implements Observable, Listener {

    /**
     * Observers provider.
     */
    protected final ObserverProvider observer = new ObserverProvider() ;

    /**
     * Add a listener to the listener provider.
     *
     * @param listener : listener instance.
     */
    public void addListener(Listener listener) {
        this.observer.addListener(listener) ;
    }

    /**
     * Remove the listener from the listener provider.
     *
     * @param listener : listener instance.
     */
    public void removeListener(Listener listener) {
        this.observer.removeListener(listener) ;
    }

}
