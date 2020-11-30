package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHConfiguration;

public class SSHConfigurationBuilder {
    private String serverAddress;
    private String username;
    private String password;
    private int port;
    private int sessionTime;

    public SSHConfigurationBuilder setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public SSHConfigurationBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public SSHConfigurationBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public SSHConfigurationBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public SSHConfigurationBuilder setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
        return this;
    }
    public SSHConfiguration build(){
        return new SSHConfiguration(serverAddress, username, password, port, sessionTime);
    }
}
