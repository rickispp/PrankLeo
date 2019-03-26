package ru.prankleo.client.processor;

import ru.prankleo.client.util.UIUtil;
import ru.prankleo.commons.dto.LeoNotificationCommand;

public class LeoNotificationCommandProcessor implements CommandProcessor<LeoNotificationCommand> {

    @Override
    public void runCommand(LeoNotificationCommand command) {
        UIUtil.showEyeLeoWindow(command.getImageUrl(), command.getMessage(), command.getCloseButtonText());
    }
}
