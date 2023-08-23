package com.dita.xd.service;

import java.sql.*;


class ConnectionObject {
    public Connection connection = null;
    public boolean inUse = false;

    public ConnectionObject(Connection c, boolean useFlag) {
        connection = c;
        inUse = useFlag;
    }
}