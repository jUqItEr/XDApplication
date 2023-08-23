package com.dita.xd.util.database;

import java.sql.Connection;

public class ConnectionObject {
    public Connection connection = null;
    public boolean inUse = false;

    public ConnectionObject(Connection c, boolean useFlag) {
        connection = c;
        inUse = useFlag;
    }
}