package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain;

import com.jcraft.jsch.*;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JSONUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class establish and execute SSH commands.
 */
public class SSHSession {
    private final String id;
    private final RemoteServer remoteServer;
    private Session session;
    /**
     * Constructor
     *
     * @param remoteServer SSH configuration.
     */
    public SSHSession(RemoteServer remoteServer) {
        this.id = UUID.randomUUID().toString();
        this.remoteServer = remoteServer;
    }

    /**
     * Establish a connection with the SSH Remote Server.
     *
     * @return {@code true} if the connection has been establish successfully.
     * {@code false} otherwise.
     * @throws BusinessException Exception threw when an error comes up
     *                             during connection.
     */
    public boolean connect() throws BusinessException {
        try {
            if (this.session == null || !this.session.isConnected()) {
                final JSch jsch = new JSch();

                final String config = getParsedConfiguration(                                       /* Parse the configuration into a string */
                this.remoteServer.getSshConfiguration());
                final ConfigRepository configRepository = com.jcraft.jsch.OpenSSHConfig.parse(config);

                jsch.setConfigRepository(configRepository);

                session = jsch.getSession("foo");

                session.setPassword(this.remoteServer.getSshConfiguration().getPassword());

                UserInfo ui = new MyUserInfo() {
                    public void showMessage(String message) { }
                    public boolean promptYesNo(String message) { return true; }// TODO MEJORAR
                };

                session.setUserInfo(ui);

                session.connect();                                                                  /* making a connection with timeout as defined above. */
                if (session.isConnected()){
                    LogProcessService.getInstance().genericSSHConnectionLog(id,
                            LogProcessService.getInstance().CONFIRMATION,
                            "Conexión ssh establecida.");
                    return true;
                }
            }
        } catch (IOException e) {
            sendConnectionErrorMessage();
            throw new BusinessException("Error en la conexión (parse) ", -1);

        } catch (JSchException e) {
            sendConnectionErrorMessage();
            throw new BusinessException("Error en la conexión", -1);
        }
        return false;
    }
    private void sendConnectionErrorMessage(){
        LogProcessService.getInstance().genericSSHConnectionLog(id,
                LogProcessService.getInstance().ERROR,
                "Conexión no estabecida con el servidor " +
                remoteServer.getLabel() + " (" +
                remoteServer.getSshConfiguration().getUsername()+"@"+
                remoteServer.getSshConfiguration().getServerAddress()+").");
    }
    /**
     * Close the SSH connection with the Server.
     */
    public final void disconnect() {
        if (this.isConnected()) {
            this.session.disconnect();
        }
    }

    public final boolean isConnected() {
        return this.session != null && this.session.isConnected();
    }

    /**
     * Creates a parsed String with the configuration.
     *
     * @param configuration SSH Configuration to establish the connection.
     * @return {@code String} Configuration parsed.
     */
    private String getParsedConfiguration(SSHConfiguration configuration) {

        return (configuration != null) ? "Port " + configuration.getPort() + "\n\n" +
                "Host foo\n" +
                "  User " + configuration.getUsername() + "\n" +
                "  Hostname " + configuration.getServerAddress() + "\n" +
                "Host *\n" +
                "  ConnectTime " + configuration.getSessionTime() + "\n" +
                "  PreferredAuthentications keyboard-interactive,password,publickey\n" +
                "  #ForwardAgent yes\n" +
                "  #StrictHostKeyChecking no\n" +
                "  #IdentityFile ~/.ssh/id_rsa\n" +
                "  #UserKnownHostsFile ~/.ssh/known_hosts" : null;
    }

    /**
     * Executes a command throw SSH.
     *
     * @param command SSH command to execute.
     * @return the SSH command response.
     * @throws IllegalAccessException Exception throws when the connection
     *                                is all ready established.
     * @throws JSchException          Exception throws is one SSH command
     *                                execution has failed.
     * @throws IOException            Exception cause when the SSH command
     *                                response has an error.
     */
    public final String executeCommand(String command) throws IllegalAccessException, JSchException, IOException {
        if (this.session != null && this.session.isConnected()) {
            /* Open one SSH channel */
            ChannelExec channelExec = (ChannelExec) this.session.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand(command);                                        /* Executes the command */
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  /* Get the text printed in console. */
            final List<String> lines = new ArrayList<>();
            final JSONUtil jsonUtil = new JSONUtil();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            channelExec.disconnect();                                               /* close the SSH channel */
            return jsonUtil.asJson(lines);                                          /* Get the text printed in console */
        } else {
            throw new IllegalAccessException("There is not SSH session initialize.");
        }
    }

    public static abstract class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        public String getPassword() {
            return null;
        }

        public boolean promptYesNo(String str) {
            return false;
        }

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return false;
        }

        public boolean promptPassword(String message) {
            return false;
        }

        public void showMessage(String message) {
        }

        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SSHSession that = (SSHSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public RemoteServer getRemoteServer() {
        return remoteServer;
    }

    @Override
    public String toString() {
        return "SSHSession{" +
                "id=" + id +
                ", remoteServer=" + remoteServer +
                ", session=" + session +
                '}';
    }
}


