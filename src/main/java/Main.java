import twitter.client.StartupGUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {// Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception or not
        }
        SwingUtilities.invokeLater(StartupGUI::new);
}}
