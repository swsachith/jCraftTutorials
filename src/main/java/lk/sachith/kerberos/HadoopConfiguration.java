package lk.sachith.kerberos;

/**
 * Created by swithana on 23/04/14.
 */

import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;
import java.util.Map;


public class HadoopConfiguration
        extends javax.security.auth.login.Configuration {
    private Map<String, String> BASIC_JAAS_OPTIONS =
            new HashMap<String, String>();

    private Map<String, String> USER_KERBEROS_OPTIONS =
            new HashMap<String, String>();

    private String ticketCache;
    public HadoopConfiguration(String ticketCache) {
        this.ticketCache = ticketCache;
        System.out.println("TicketCache: "+ticketCache);
        init();
    }

    private void init()
     {
        if (false) {
            USER_KERBEROS_OPTIONS.put("useDefaultCache", "true");
        } else {
            USER_KERBEROS_OPTIONS.put("doNotPrompt", "true");
            USER_KERBEROS_OPTIONS.put("useTicketCache", "true");
            USER_KERBEROS_OPTIONS.put("debug", "true");
        }
//        String ticketCache = "/Users/swithana/krb5cc_apache_swithana";
//        String ticketCache = System.getenv("KRB5CCNAME");
        USER_KERBEROS_OPTIONS.put("ticketCache", ticketCache);
        USER_KERBEROS_OPTIONS.put("renewTGT", "true");
//        USER_KERBEROS_OPTIONS.putAll(BASIC_JAAS_OPTIONS);
    }

    private AppConfigurationEntry USER_KERBEROS_LOGIN =
            new AppConfigurationEntry("com.sun.security.auth.module.Krb5LoginModule",
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                    USER_KERBEROS_OPTIONS);

    private AppConfigurationEntry[] SIMPLE_CONF =
            new AppConfigurationEntry[]{USER_KERBEROS_LOGIN};

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        return SIMPLE_CONF;
    }
    /*private static final AppConfigurationEntry KEYTAB_KERBEROS_LOGIN =
            new AppConfigurationEntry(KerberosUtil.getKrb5LoginModuleName(),
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                    KEYTAB_KERBEROS_OPTIONS);*/


}