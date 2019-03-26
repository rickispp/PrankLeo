package ru.prankleo.client.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class UIUtil {

    private UIUtil(){}

    public static void showEyeLeoWindow(String imageUrl, String message, String buttonText) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(createImage(imageUrl));
        jPanel.add(createMessageLabel(message));
        JButton closeButton = createCloseButton(buttonText);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        jPanel.add(closeButton);
        frame.setContentPane(jPanel);
        frame.setBackground(new Color(0.0f,0.0f,0.0f,0.5f));
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private static JLabel createImage(String url) {
        JLabel jLabel = new JLabel();

        try {
            Image image = ImageIO.read(new URL(url));
            ImageIcon imageIcon = new ImageIcon(image);
            jLabel.setIcon(imageIcon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Border emptyBorder = new EmptyBorder(150, 10, 20, 10);
        jLabel.setBorder(emptyBorder);

        return jLabel;
    }

    private static JLabel createMessageLabel(String message) {
        JLabel messageLabel = new JLabel();
        messageLabel.setOpaque(false);
        messageLabel.setText(message);
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.BOLD, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(new Color(1f, 1f, 1f));
        Border emptyBorder = new EmptyBorder(10, 10, 30, 10);
        messageLabel.setBorder(emptyBorder);
        return messageLabel;
    }

    private static JButton createCloseButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, 14));;
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(new Color(1f,1f,1f,1f));
        button.setBackground(new Color(0.0f,0.0f,0.0f,1f));

        return button;
    }
}
