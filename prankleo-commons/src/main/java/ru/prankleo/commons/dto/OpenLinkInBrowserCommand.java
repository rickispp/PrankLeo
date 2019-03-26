package ru.prankleo.commons.dto;

/**
 * Открыть ссылку в браузере
 */
public class OpenLinkInBrowserCommand extends Command {

    /**
     * URL по которому будет совершен переход
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
