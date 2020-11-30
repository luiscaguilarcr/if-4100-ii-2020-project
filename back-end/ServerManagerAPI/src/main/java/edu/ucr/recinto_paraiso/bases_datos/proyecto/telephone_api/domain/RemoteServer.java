package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;
/**
 * This class represents a remote server instance.
 *
 *  The label, description and version are additional information that can be useful to the user to identify
 * the instance.
 *  The version is additional info to help the user set more information of the server instance. For example,
 * the user specifies the version = 'ubuntu server LTS 20.04' or 'Windows Server 2019 64 bits'.
 *
 *  The operative system (windows server, ubuntu server...) is used to identify commands.
 *
 *  The SSHConfiguration will be used to establish one or more SSHSession.
 */
public class RemoteServer {
    private final Integer id;
    private Integer adminId;
    private String label;
    private String description;
    private String version;
    private final String operativeSystem;
    private SSHConfiguration sshConfiguration;

    /**
     * Creates a new Remote Server instance.
     *
     * @param adminId ServerAdministrator's id.
     * @param label of the remote server.
     * @param description of the remote server. Used to add more details if the user have more than one server.
     * @param operativeSystem of the remote server instance.
     * @param version of the remote server instance.
     * @param sshConfiguration of the remote server instance. Used to establish ssh sessions.
     */
    public RemoteServer(Integer id, Integer adminId, String label, String description, String operativeSystem, String version, SSHConfiguration sshConfiguration) {
        this.id = id;
        this.adminId = adminId;
        this.label = label;
        this.description = description;
        this.operativeSystem = operativeSystem;
        this.version = version;
        this.sshConfiguration = sshConfiguration;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperativeSystem() {
        return operativeSystem;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SSHConfiguration getSshConfiguration() {
        return sshConfiguration;
    }

    public void setSshConfiguration(SSHConfiguration sshConfiguration) {
        this.sshConfiguration = sshConfiguration;
    }

    public Integer getId() {
        return id;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "RemoteServer{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", operativeSystem=" + operativeSystem +
                ", sshConfiguration=" + sshConfiguration +
                '}';
    }

    /**
     * The password is not included.
     *
     * @return User in JSON format.
     */
    public String toJSON(){
        return "{"+
                "\"server_id\":\"" + id + "\"," +
                "\"admin_id\":\"" + adminId + "\"," +
                "\"label\":\"" + label+ "\"," +
                "\"description\":\"" +  description+ "\"," +
                "\"operative_system\":\"" + operativeSystem + "\"," +
                "\"version\":\"" + version + "\"," +
                "\"server_address\":\"" + sshConfiguration.getServerAddress() +"\"," +
                "\"username\":\"" + sshConfiguration.getUsername() + "\"," +
                "\"password\":\"" + sshConfiguration.getPassword() + "\"," +
                "\"port\":\"" + sshConfiguration.getPort() + "\"," +
                "\"session_time\":\"" +  sshConfiguration.getSessionTime() +"\"" +
                "}";
    }
}
