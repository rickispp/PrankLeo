package ru.prankleo.server.handler;

import org.springframework.stereotype.Service;
import ru.prankleo.commons.dto.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandService {

    private Map<String, List<Command>> commandsMap = new HashMap<>();

    /**
     * Логика простая - забираем все, что есть
     * Все, что забрали - вычищаем
     */
    public List<Command> getCommandsMapForPoint(String pointId) {
        List<Command> commands = Optional.ofNullable(commandsMap.get(pointId)).orElse(new ArrayList<>());
        List<Command> result = new ArrayList<>(commands);
        commands.clear();
        return result;
    }

    public Command putCommandForPoint(String pointId, Command command) {
        commandsMap.putIfAbsent(pointId, new ArrayList<>());
        commandsMap.get(pointId).add(command);
        return command;
    }
}
