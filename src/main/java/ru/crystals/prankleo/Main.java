package ru.crystals.prankleo;

import ru.crystals.prankleo.settings.ArgParser;
import ru.crystals.prankleo.settings.Settings;

public class Main {

    public static void main(String[] args) {
        Settings settings = ArgParser.getInstance().parseSettings(args);

        if(settings.isInSilentMode()) {

        } else {
            emulateRealWork();
        }
    }

    /**
     * Запустить имитацию нужной программы
     * TODO: Например, вывести окно имитирующее важные преобразования, типа генерации акцизных марок
     */
    private static void emulateRealWork() {

    }

    /**
     * Подложить скрипт запуска самого себя в автозапуск, если ранее еще не был подложен
     */
    private static void putSelfToAutoRunIfNeeded() {

    }
}
