package ru.crystals.prankleo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrayNotificationAction.class, name = "TrayNotification"),
        @JsonSubTypes.Type(value = LockComputatorAction.class, name = "LockComputator"),
        @JsonSubTypes.Type(value = LeoNotificationAction.class, name = "LeoNotification")
})
public abstract class Action {

    /**
     * Время ожидания между получением экшна и непосредственным его выполнением
     */
    private long timeout = 10000;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
