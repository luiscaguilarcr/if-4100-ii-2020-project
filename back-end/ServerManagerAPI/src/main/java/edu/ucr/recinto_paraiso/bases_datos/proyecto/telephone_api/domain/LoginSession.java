package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoginSession {
    private final Integer userId;
    private final String sessionToken;
    private LocalDateTime expireDate;
    private final String IP;

    public LoginSession(Integer userId, String sessionToken, String IP) {
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.expireDate = null;
        this.IP = IP;
    }

    public LoginSession(Integer userId, String sessionToken, LocalDateTime expireDate, String IP) {
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.expireDate = expireDate;
        this.IP = IP;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getIP() {
        return IP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginSession that = (LoginSession) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(sessionToken, that.sessionToken) &&
                Objects.equals(IP, that.IP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionToken, IP);
    }

    @Override
    public String toString() {
        return "LoginSession{" +
                "userId=" + userId +
                ", token=" + sessionToken +
                ", expireDate=" + expireDate +
                ", IP='" + IP + '\'' +
                '}';
    }
}
