package ru.prankleo.client.processor;

import ru.prankleo.commons.dto.TrayNotificationCommand;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

public class TrayNotificationCommandProcessor implements CommandProcessor<TrayNotificationCommand> {

    @Override
    public void runCommand(TrayNotificationCommand command) {
        try {
            SystemTray systemTray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("");
            TrayIcon trayIcon = new TrayIcon(image);
            systemTray.add(trayIcon);

            trayIcon.displayMessage(command.getTitle(), command.getMessage(), TrayIcon.MessageType.INFO);
        } catch (Exception ex) {
            //  ничего страшного
        }
    }
}
