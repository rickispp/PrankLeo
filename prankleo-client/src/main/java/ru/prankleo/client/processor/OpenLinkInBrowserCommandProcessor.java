package ru.prankleo.client.processor;

import ru.prankleo.client.dto.OpenLinkInBrowserCommand;

import java.awt.Desktop;
import java.net.URI;

public class OpenLinkInBrowserCommandProcessor implements CommandProcessor<OpenLinkInBrowserCommand> {

    @Override
    public void runCommand(OpenLinkInBrowserCommand command) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(URI.create(command.getUrl()));
        } catch (Exception ex) {
            //  ну не смогли, сорян
        }
    }
}
