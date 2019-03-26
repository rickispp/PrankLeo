package ru.crystals.prankleo.dispatcher;

import ru.crystals.prankleo.dto.Command;
import ru.crystals.prankleo.dto.LeoNotificationCommand;
import ru.crystals.prankleo.dto.LockComputatorCommand;
import ru.crystals.prankleo.dto.OpenLinkInBrowserCommand;
import ru.crystals.prankleo.dto.TrayNotificationCommand;
import ru.crystals.prankleo.processor.CommandProcessor;
import ru.crystals.prankleo.processor.LeoNotificationCommandProcessor;
import ru.crystals.prankleo.processor.LockComputatorCommandProcessor;
import ru.crystals.prankleo.processor.OpenLinkInBrowserCommandProcessor;
import ru.crystals.prankleo.processor.TrayNotificationCommandProcessor;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {

    private static CommandDispatcher ourInstance = new CommandDispatcher();

    private static Map<String, CommandProcessor> commandProcessorMap = new HashMap<>();

    static {
        commandProcessorMap.put(LeoNotificationCommand.class.getName(), new LeoNotificationCommandProcessor());
        commandProcessorMap.put(LockComputatorCommand.class.getName(), new LockComputatorCommandProcessor());
        commandProcessorMap.put(OpenLinkInBrowserCommand.class.getName(), new OpenLinkInBrowserCommandProcessor());
        commandProcessorMap.put(TrayNotificationCommand.class.getName(), new TrayNotificationCommandProcessor());
    }

    private CommandDispatcher() {
    }

    public static CommandDispatcher getInstance() {
        return ourInstance;
    }

    public void runCommand(Command command) {
        CommandProcessor commandProcessor = commandProcessorMap.get(command.getClass().getName());
        if(commandProcessor != null) {
            commandProcessor.runCommand(command);
        }
    }
}
