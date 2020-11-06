package Messenger.gui;

import Messenger.gui.utils.GraphicImage;
import Messenger.gui.layout.TitleBar;
import Messenger.utils.Current;
import javax.swing.*;
import java.awt.*;

/**
 * @author Damien MOLINA
 */
public class Frame extends JFrame {

    /**
     * Main title bar instance.
     */
    private final JPanel titleBar ;

    /**
     * Create a graphical instance
     * for the Messenger.
     *
     * @param content : frame content.
     */
    public Frame(JComponent content) {
        this.initializeComponentGraphics() ;

        this.titleBar = new TitleBar(this) ;

        this.graphicContent(content) ;
    }

    /**
     * Initialize graphically the component.
     */
    private void initializeComponentGraphics() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;
        this.setTitle(Current.getApplication().getName()) ;
        this.setLocationByPlatform(true) ;
        this.setUndecorated(true) ;
        this.setIconImage(new ImageIcon(GraphicImage.logo()).getImage()) ;
    }

    /**
     * Add the content to the frame.
     *
     * @param content : JComponent to add.
     */
    private void graphicContent(JComponent content) {
        this.getContentPane().add(this.titleBar, BorderLayout.PAGE_START) ;
        this.add(content, BorderLayout.LINE_START) ;
        this.pack() ;
    }

}
