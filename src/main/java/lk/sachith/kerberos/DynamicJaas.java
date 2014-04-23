package lk.sachith.kerberos;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by swithana on 4/23/14.
 */
public class DynamicJaas {
    public static void main(String[] args) {
        System.setProperty("java.security.krb5.conf", "/Users/swithana/git/KerberosConnector/src/main/resources/krb5.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("sun.security.krb5.debug", "true");

        String host = "gw110.iu.xsede.org";
        String user = "swithana";
        String command = "ls -ltr";

        javax.security.auth.login.Configuration.setConfiguration(new HadoopConfiguration("/Users/swithana/krb5cc_apache_swithana"));

        JSch jsch = new JSch();
        jsch.setLogger(new MyLogger());
        try {
            Session session = jsch.getSession(user, host, 22);
            executeCommand(session,command);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        user = "cpelikan";

        javax.security.auth.login.Configuration.setConfiguration(new HadoopConfiguration("/Users/swithana/krb5cc_apache_cpelikan"));

        try {
            Session session = jsch.getSession(user, host, 22);
            executeCommand(session,command);
        } catch (JSchException e) {
            e.printStackTrace();
        }

    }

    public static class MyLogger implements com.jcraft.jsch.Logger {
        static java.util.Hashtable name = new java.util.Hashtable();

        static {
            name.put(new Integer(DEBUG), "DEBUG: ");
            name.put(new Integer(INFO), "INFO: ");
            name.put(new Integer(WARN), "WARN: ");
            name.put(new Integer(ERROR), "ERROR: ");
            name.put(new Integer(FATAL), "FATAL: ");
        }

        public boolean isEnabled(int level) {
            return true;
        }

        public void log(int level, String message) {
            System.err.print(name.get(new Integer(level)));
            System.err.println(message);
        }
    }

    public static void executeCommand(Session session, String command) {

        try {
            // jsch.addIdentity(privateKey,paraphrase);

            // Properties props = System.getProperties();
            // props.list(System.out);

            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications",
                    "gssapi-with-mic");

            session.setConfig(config);
//            session.setPassword(password);
            session.connect(20000);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
