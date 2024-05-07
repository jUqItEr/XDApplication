package com.dita.xd.util.security;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SSHUtil {
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;

    private static final String REMOTE_HOST = "hxlab.co.kr";
    private static final String REMOTE_USER = "dita";
    private static final int REMOTE_PORT = 2999;

    private static final String PASSPHRASE = "sksmsahffkdy";
    private static final String PEM_KEY = "resources/server-key.pem";

    private static Session session;

    public static int connect() {
        int forwardedPort;
        Properties props = new Properties();
        props.put("StrictHostKeyChecking", "no");

        try {
            JSch jSch = new JSch();
            jSch.addIdentity(PEM_KEY, PASSPHRASE);

            session = jSch.getSession(REMOTE_USER, REMOTE_HOST, REMOTE_PORT);
            session.setConfig(props);
            session.connect();

            forwardedPort = session.setPortForwardingL(0, DB_HOST, DB_PORT);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        return forwardedPort;
    }

    public static void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
