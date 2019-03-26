package ru.prankleo.server.handler;

import org.springframework.stereotype.Service;
import ru.prankleo.commons.dto.CurrentPointInfo;

import java.util.HashSet;
import java.util.Set;

@Service
public class PointsService {

    private Set<CurrentPointInfo> points = new HashSet<>();

    public void register(CurrentPointInfo pointInfo) {
        points.add(pointInfo);
    }

    public Set<CurrentPointInfo> getPoints() {
        return points;
    }
}
