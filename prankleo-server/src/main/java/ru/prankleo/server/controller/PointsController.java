package ru.prankleo.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prankleo.commons.dto.CurrentPointInfo;
import ru.prankleo.server.handler.PointsService;
import java.util.Set;

@RestController
@RequestMapping(value = "/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CurrentPointInfo register(@RequestBody CurrentPointInfo currentPointInfo) {
        pointsService.register(currentPointInfo);
        return currentPointInfo;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Set<CurrentPointInfo> getCurrentPoints() {
        return pointsService.getPoints();
    }
}
