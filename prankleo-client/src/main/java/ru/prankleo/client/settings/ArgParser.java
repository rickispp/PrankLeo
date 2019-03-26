package ru.prankleo.client.settings;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ArgParser {
    /**
     * Опция "тихого" запуска.
     * Будет использоваться для автозапуска, дабы не тревожить хомячка лишними окнами.
     */
    private static final Option silentModeOption = new Option("sm", "silentmode", false, "Start in silent mode");

    /**
     * Набор доступных опций
     */
    private static final Options options = new Options();

    static {
        options.addOption(silentModeOption);
    }

    private static ArgParser ourInstance = new ArgParser();

    public static ArgParser getInstance() {
        return ourInstance;
    }

    private ArgParser() {
    }

    public Settings parseSettings(String[] args) {
        Settings settings = new Settings();

        try {
            Options options = new Options();
            options.addOption(silentModeOption);

            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            settings.setInSilentMode(commandLine.hasOption(silentModeOption.getOpt()));
        } catch (Exception ex) {
            //  да поебать вообще
        }

        return settings;
    }
}
