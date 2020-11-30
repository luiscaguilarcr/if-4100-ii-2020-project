package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

public class Log {
    private String date;
    private int category;
    private int type;
    private String sessionToken;
    private String detail;

    public Log() {
    }

    public Log(String date, int category, int type, String sessionToken, String detail) {
        this.date = date;
        this.category = category;
        this.type = type;
        this.sessionToken = sessionToken;
        this.detail = detail;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public int getCategory() {
        return category;
    }

    public int getType() {
        return type;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getDetail() {
        return detail;
    }

}
