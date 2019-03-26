package ru.crystals.prankleo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TrayNotificationCommand.class, name = "TrayNotification"),
        @JsonSubTypes.Type(value = LockComputatorCommand.class, name = "LockComputator"),
        @JsonSubTypes.Type(value = LeoNotificationCommand.class, name = "LeoNotification"),
        @JsonSubTypes.Type(value = OpenLinkInBrowserCommand.class, name = "OpenLinkInBrowser")
})
public abstract class Command {

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
