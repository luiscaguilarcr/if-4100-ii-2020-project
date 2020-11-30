package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

public class SSHCommandRequest<K> {
    private String command;
    private String response;
    private final K sessionKey;

    public SSHCommandRequest(K sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public K getSessionKey() {
        return sessionKey;
    }


    @Override
    public String toString() {
        return "SSHCommandRequest{" +
                "command='" + command + '\'' +
                ", response='" + response + '\'' +
                ", sessionKey=" + sessionKey +
                '}';
    }
}
