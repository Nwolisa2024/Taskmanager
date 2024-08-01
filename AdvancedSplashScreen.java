import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvancedSplashScreen extends JWindow {

    private JProgressBar progressBar;
    private JLabel statusLabel;

    public AdvancedSplashScreen() {
        // Set size and location
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Background image
        JLabel backgroundImage = new JLabel(new ImageIcon("background.jpeg"));
        backgroundImage.setLayout(null);
        add(backgroundImage, 0);
        backgroundImage.setBounds(0, 0, getWidth(), getHeight());

        // Background color
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Task Management System");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(80, 160, 300, 30);
        add(title);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(50, 200, 300, 20);
        progressBar.setStringPainted(true);
        add(progressBar);

        // Status label
        statusLabel = new JLabel("Loading...");
        statusLabel.setBounds(50, 220, 200, 20);
        add(statusLabel);

        // Layout
        setLayout(null);

        // Simulate loading
        Timer timer = new Timer(100, new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                progressBar.setValue(count);
                statusLabel.setText("Loading... (" + count + "%)");

                if (count >= 100) {
                    ((Timer) e.getSource()).stop();
                    dispose();
                    // Open main application window here
                    SwingUtilities.invokeLater(TaskManagerGUI::new);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdvancedSplashScreen splash = new AdvancedSplashScreen();
            splash.setVisible(true);
        });
    }
}
