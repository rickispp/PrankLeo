package ru.prankleo.commons.dto;

/**
 * Показать уведомление в трее
 */
public class TrayNotificationCommand extends Command {

    /**
     * Заголовок оповещения
     */
    private String title;

    /**
     * Сообщение
     */
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
