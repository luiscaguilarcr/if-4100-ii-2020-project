package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

/**
 * This class represents the basic configuration required to establish a SSH session.
 */
public class SSHConfiguration {
    private String serverAddress;
    private String username;
    private String password;
    private int port;
    private int sessionTime;
    /**
     * +
     *
     * @param serverAddress   of the remote server.
     * @param username    of the user's remote server.
     * @param password    of the user's remote server.
     * @param port        of the remote server.
     * @param sessionTime time of the session.
     */
    public SSHConfiguration(String serverAddress, String username, String password, int port, int sessionTime) {
        this.serverAddress = serverAddress;
        this.username = username;
        this.password = password;
        this.port = port;
        this.sessionTime = sessionTime;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SSHConfiguration{" +
                "ipAddress='" + serverAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", sessionTime=" + sessionTime +
                '}';
    }
}
