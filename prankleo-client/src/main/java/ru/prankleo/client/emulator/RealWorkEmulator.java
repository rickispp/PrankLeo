package ru.prankleo.client.emulator;

import javax.swing.*;
import java.awt.*;

/**
 * Окно с бесконечным прогресс баром и просьбой подождать
 */
public class RealWorkEmulator extends JFrame {

    public RealWorkEmulator() {
        setTitle("Excise generator");
        setSize(300, 60);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/app_icon.png")));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel waitLabel = new JLabel("Пожалуйста, подождите...");
        add(waitLabel, BorderLayout.SOUTH);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        add(progressBar, BorderLayout.NORTH);
    }

    public void start() {
        setVisible(true);
    }
}
