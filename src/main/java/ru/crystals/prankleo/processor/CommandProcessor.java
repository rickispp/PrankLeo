package ru.crystals.prankleo.processor;

import ru.crystals.prankleo.dto.Command;

public interface CommandProcessor<C extends Command> {
    void runCommand(C command);
}
