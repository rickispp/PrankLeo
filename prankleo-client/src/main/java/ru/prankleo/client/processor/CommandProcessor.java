package ru.prankleo.client.processor;

import ru.prankleo.commons.dto.Command;

public interface CommandProcessor<C extends Command> {
    void runCommand(C command);
}
