package lk.sachith.kerberos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by swithana on 2/19/14.
 */
public class SystemCommandExecute {
    public static void main(String[] args) {
        try {

            Runtime r = Runtime.getRuntime();
            Process p = r.exec("scp swithana@156.56.179.200:/home/swithana/test.txt airavata@gw98.iu.xsede.org:/home/airavata/temp.txt");
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";


            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
