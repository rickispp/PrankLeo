package ru.crystals.prankleo.dto;

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
}
