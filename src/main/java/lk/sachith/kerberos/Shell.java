package lk.sachith.kerberos;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by swithana on 2/21/14.
 */
public class Shell {

    public static void main(String[] args) {
        String host = "gw110.iu.xsede.org";
        String user = "swithana";
        String paraphrase = "sachithwithana";
        String password = "dihini himahansi and thevini himansa";
        String  command = "ls -ltr";
        String privateKey = "/Users/swithana/id_rsa";

        JSch jsch = new JSch();
        jsch.setLogger(new MyLogger());

        System.setProperty("java.security.krb5.conf", "/Users/swithana/git/KerberosConnector/src/main/resources/krb5.conf");
        System.setProperty("java.security.auth.login.config", "/Users/swithana/git/KerberosConnector/src/main/resources/login.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("sun.security.krb5.debug", "true");

        try {
            // jsch.addIdentity(privateKey,paraphrase);

            // Properties props = System.getProperties();
            // props.list(System.out);

            Session session = jsch.getSession(user, host, 22);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications",
                    "gssapi-with-mic");

            session.setConfig(config);
            session.setPassword(password);
            session.connect(20000);

            Channel channel = session.openChannel("shell");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();


            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class MyLogger implements com.jcraft.jsch.Logger {
        static java.util.Hashtable name=new java.util.Hashtable();
        static{
            name.put(new Integer(DEBUG), "DEBUG: ");
            name.put(new Integer(INFO), "INFO: ");
            name.put(new Integer(WARN), "WARN: ");
            name.put(new Integer(ERROR), "ERROR: ");
            name.put(new Integer(FATAL), "FATAL: ");
        }
        public boolean isEnabled(int level){
            return true;
        }
        public void log(int level, String message){
            System.err.print(name.get(new Integer(level)));
            System.err.println(message);
        }
    }
}
