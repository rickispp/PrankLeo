package ru.prankleo.commons.dto;

/**
 * Самый смак.
 * То самое окно с предложением отдохнуть.
 */
public class LeoNotificationCommand extends Command {

    /**
     * Сообщение
     */
    private String message;

    /**
     * URL изображения, которое будет показано вместе с сообщением
     */
    private String imageUrl;

    /**
     * Текст кнопки закрытия окна
     */
    private String closeButtonText;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloseButtonText() {
        return closeButtonText;
    }

    public void setCloseButtonText(String closeButtonText) {
        this.closeButtonText = closeButtonText;
    }
}
