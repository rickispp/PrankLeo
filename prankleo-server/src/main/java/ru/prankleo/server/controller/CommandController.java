package ru.prankleo.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prankleo.commons.dto.Command;
import ru.prankleo.server.handler.CommandService;
import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @GetMapping(value = "/{pointIp}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Command> getCommands(@PathVariable String pointIp) {
        return commandService.getCommandsMapForPoint(pointIp);
    }

    @PostMapping(value = "/{pointIp}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Command createCommand(@PathVariable String pointIp, @RequestBody Command command) {
        return commandService.putCommandForPoint(pointIp, command);
    }
}
