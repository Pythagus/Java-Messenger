package fr.insa.messenger.models.messages;

import java.util.Date;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.GregorianCalendar;
import fr.insa.messenger.system.Env;
import fr.insa.messenger.models.User;
import fr.insa.messenger.utils.DateUtils;

/**
 * Message sent between two users.
 *
 * @author Maud PENNETIER
 */
public class Message implements Serializable {

    /**
     * Serialisation identifier.
     */
    private static final long serialVersionUID = 4242424242424242411L ;

    /**
     * Message data.
     */
    protected final MessageData data ;

    /**
     * Send date.
     */
    protected final long timestamp ;

    /**
     * Sender of the message.
     */
    protected final User sender ;

    /**
     * Targeted user.
     */
    protected final User target ;

    /**
     * Make a new Message instance.
     *
     * @param target : targeted user.
     * @param data : sent message.
     */
    public Message(User target, MessageData data) {
        /*
         * When the Message instance is created,
         * the date attribute is set to get the
         * exact sending time.
         */
        this(
            target, data, new Timestamp(System.currentTimeMillis()).getTime()
        ) ;
    }

    /**
     * Make a new Message instance.
     *
     * @param target : targeted user.
     * @param data : sent message.
     */
    public Message(User target, MessageData data, long timestamp) {
        this(Env.getUser(), target, data, timestamp) ;
    }

    /**
     * Make a new Message instance.
     *
     * @param target : targeted user.
     * @param data : sent message.
     */
    public Message(User sender, User target, MessageData data, long timestamp) {
        this.target    = target ;
        this.data      = data ;
        this.sender    = sender ;
        this.timestamp = timestamp ;
    }

    /**
     * @return date of the message
     */
    public Date getDate() {
        return new Date(this.timestamp) ;
    }

    /**
     * Get the date formatted to be
     * print.
     *
     * @return the formatted date.
     */
    public String getDateForHuman() {
        Date date = this.getDate() ;

        GregorianCalendar calendar = new GregorianCalendar() ;
        calendar.setTime(date) ;

        Date today = DateUtils.today() ;

        return DateUtils.format(
            date, calendar.before(today) ? DateUtils.DATE_TIME_FORMAT : DateUtils.TIME_FORMAT
        ) ;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return this.sender ;
    }

    /**
     * @return the target
     */
    public User getTarget() {
        return this.target ;
    }

    /**
     * @return the MessageData instance
     */
    public MessageData getData() {
        return this.data ;
    }

}