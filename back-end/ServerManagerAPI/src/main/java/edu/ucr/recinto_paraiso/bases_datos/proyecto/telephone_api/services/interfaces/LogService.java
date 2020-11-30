package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Log;

import java.util.List;

public interface LogService {
    void sessionTokenGenerationLog(final String CLIENT_TOKEN, final String SESSION_TOKEN, final int userId, final String ipAddress);
    void sshTokenGenerationLog(final String SESSION_TOKEN, final String SSH_SESSION_TOKEN, final int remoteServerId);
    void genericUserLog(final String SESSION_TOKEN, final int category, final String detail);
    void genericRemoteServerLog(final String SESSION_TOKEN, final int category, final String detail);
    void genericSSHConnectionLog(final String SESSION_TOKEN, final int category, final String detail);
    List<Log> getLogs();
}
