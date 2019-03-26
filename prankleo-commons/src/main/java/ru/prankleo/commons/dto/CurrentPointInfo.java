package ru.prankleo.commons.dto;

import java.util.Objects;

public class CurrentPointInfo {
    private String ip;
    private String userName;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentPointInfo that = (CurrentPointInfo) o;
        return Objects.equals(ip, that.ip) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, userName);
    }
}
