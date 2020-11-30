package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHConfiguration;

public class RemoteServerBuilder {
    private Integer id;
    private Integer adminId;
    private String label;
    private String description;
    private String version;
    private String operativeSystem;
    private SSHConfiguration sshConfiguration;

    public RemoteServerBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public RemoteServerBuilder setAdminId(Integer adminId) {
        this.adminId = adminId;
        return this;
    }

    public RemoteServerBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public RemoteServerBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public RemoteServerBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public RemoteServerBuilder setOperativeSystem(String operativeSystem) {
        this.operativeSystem = operativeSystem;
        return this;
    }

    public RemoteServerBuilder setSshConfiguration(SSHConfiguration sshConfiguration) {
        this.sshConfiguration = sshConfiguration;
        return this;
    }

    public RemoteServer build() {
        return new RemoteServer(id, adminId, label, description, operativeSystem, version, sshConfiguration);
    }
}
