package lk.sachith.kerberos;

import com.jcraft.jsch.*;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by swithana on 2/20/14.
 */
public class SSHCommandExecutor {
    public static void main() {

//        String host = "156.56.179.200";
        String host = "gw98.iu.xsede.org";
        String user = "airavata";
//        String user = "swithana";
        String paraphrase = "sachithwithana";
        String command = "scp withana.txt swithana@156.56.179.200:/home/swithana";
//        String  command = "ls -ltr";
//        String  command = "scp test.txt airavata@g";
//        String  command = "ssh -a airavata@gw98.iu.xsede.org";
//        String command = "scp sachith.txt airavata@gw98.iu.xsede.org:/home/airavata";
        String privateKey = "/Users/swithana/id_rsa";

        JSch jsch = new JSch();
        jsch.setLogger(new MyLogger());


        try {
            jsch.addIdentity(privateKey, paraphrase);

            // Properties props = System.getProperties();
            // props.list(System.out);

            Session session = jsch.getSession(user, host, 22);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications",
                    "publickey");

            session.setConfig(config);
            //session.setPassword(password);
            session.connect(20000);

            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(command);
            ((ChannelExec) channel).setAgentForwarding(true);

            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            channel.connect();
            Thread.sleep(1000);

            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
}
