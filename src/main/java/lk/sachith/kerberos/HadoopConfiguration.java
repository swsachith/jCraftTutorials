package lk.sachith.kerberos;

/**
 * Created by swithana on 23/04/14.
 */

import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;
import java.util.Map;


public class HadoopConfiguration
        extends javax.security.auth.login.Configuration {
    private static final Map<String, String> BASIC_JAAS_OPTIONS =
            new HashMap<String, String>();

    private static final Map<String, String> USER_KERBEROS_OPTIONS =
            new HashMap<String, String>();

    static {
        if (false) {
            USER_KERBEROS_OPTIONS.put("useDefaultCache", "true");
        } else {
            USER_KERBEROS_OPTIONS.put("doNotPrompt", "true");
            USER_KERBEROS_OPTIONS.put("useTicketCache", "true");
            USER_KERBEROS_OPTIONS.put("debug", "true");
        }
        String ticketCache = "/Users/swithana/krb5cc_apache_swithana";
//        String ticketCache = System.getenv("KRB5CCNAME");
        USER_KERBEROS_OPTIONS.put("ticketCache", ticketCache);
        USER_KERBEROS_OPTIONS.put("renewTGT", "true");
//        USER_KERBEROS_OPTIONS.putAll(BASIC_JAAS_OPTIONS);
    }

    private static final AppConfigurationEntry USER_KERBEROS_LOGIN =
            new AppConfigurationEntry("com.sun.security.auth.module.Krb5LoginModule",
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                    USER_KERBEROS_OPTIONS);
    private static final Map<String, String> KEYTAB_KERBEROS_OPTIONS =
            new HashMap<String, String>();

    static {
        if (false) {
            KEYTAB_KERBEROS_OPTIONS.put("credsType", "both");
        } else {
            KEYTAB_KERBEROS_OPTIONS.put("doNotPrompt", "true");
            KEYTAB_KERBEROS_OPTIONS.put("useKeyTab", "true");
            KEYTAB_KERBEROS_OPTIONS.put("storeKey", "true");
        }
        KEYTAB_KERBEROS_OPTIONS.put("refreshKrb5Config", "true");
        KEYTAB_KERBEROS_OPTIONS.putAll(BASIC_JAAS_OPTIONS);
    }

    private static final AppConfigurationEntry[] SIMPLE_CONF =
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