package ru.prankleo.client.processor;

import ru.prankleo.client.dto.LockComputatorCommand;

public class LockComputatorCommandProcessor implements CommandProcessor<LockComputatorCommand> {

    @Override
    public void runCommand(LockComputatorCommand command) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("rundll32.exe user32.dll,LockWorkStation");
        } catch (Exception ex) {
            //  ну не смогли, сорян
        }
    }
}
