package com.dita.xd.service.implementation;

import com.dita.xd.service.Service;
import com.dita.xd.util.database.ConnectionObject;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class DBConnectionServiceImpl implements Service {
    private final String REMOTE_HOST = "hxlab.co.kr";
    private final String REMOTE_USER = "dita";
    private final String PASSPHRASE  = "sksmsahffdy";
    private final String SERVER_KEY  = "resources/server-key.pem";
    private final int    REMOTE_PORT = 2999;

    private final String DB_HOST     = "localhost";
    private final String DB_NAME     = "xd_chat";
    private final int    DB_PORT     = 3306;
    private final String DB_QUERY    = "characterEncoding=UTF-8&serverTimezone=Asia/Seoul&useSSL=false";
    private final String DB_DRIVER   = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL      = String.format("jdbc:mysql://%s:%d/%s?%s", DB_HOST, DB_PORT, DB_NAME, DB_QUERY);

    private final String SSHJ_URL    = String.format("jdbc:sshj://%s:%d?username=%s&private.key.file=%s&" +
                                                     "private.key.password=%s&remote=%s:%d;;;%s",
                                                     REMOTE_HOST, REMOTE_PORT, REMOTE_USER, SERVER_KEY, PASSPHRASE,
                                                     DB_HOST, DB_PORT, DB_URL);

    private Vector connections = new Vector(10);

    private String  _dbPwd           = "dita2414";
    private String  _dbUser          = "dita";
    private boolean _traceOn         = false;
    private boolean initialized      = false;
    private int     _openConnections = 50;

    private static DBConnectionServiceImpl instance = null;

    private DBConnectionServiceImpl() {
    }

    /**
     * Use this method to set the maximum number of open connections before
     * unused connections are closed.
     */

    public static DBConnectionServiceImpl getInstance() {
        if (instance == null) {
            synchronized (DBConnectionServiceImpl.class) {
                if (instance == null) {
                    instance = new DBConnectionServiceImpl();
                }
            }
        }

        return instance;
    }

    public void setOpenConnectionCount(int count) {
        _openConnections = count;
    }


    public void setEnableTrace(boolean enable) {
        _traceOn = enable;
    }


    /**
     * Returns a Vector of java.sql.Connection objects
     */
    public Vector getConnectionList() {
        return connections;
    }


    /**
     * Opens specified "count" of connections and adds them to the existing pool
     */
    public synchronized void setInitOpenConnections(int count)
            throws SQLException {
        Connection c = null;
        ConnectionObject co = null;

        for (int i = 0; i < count; i++) {
            c = createConnection();
            co = new ConnectionObject(c, false);

            connections.addElement(co);
            trace("ConnectionPoolManager: Adding new DB connection to pool (" + connections.size() + ")");
        }
    }


    /**
     * Returns a count of open connections
     */
    public int getConnectionCount() {
        return connections.size();
    }


    /**
     * Returns an unused existing or new connection.
     */
    public synchronized Connection getConnection()
            throws Exception {
        if (!initialized) {
            Class c = Class.forName(DB_DRIVER);
            DriverManager.registerDriver((Driver) c.newInstance());

            initialized = true;
        }


        Connection c = null;
        ConnectionObject co = null;
        boolean badConnection = false;


        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);

            // If connection is not in use, test to ensure it's still valid!
            if (!co.inUse) {
                try {
                    badConnection = co.connection.isClosed();
                    if (!badConnection)
                        badConnection = (co.connection.getWarnings() != null);
                } catch (Exception e) {
                    badConnection = true;
                    e.printStackTrace();
                }

                // Connection is bad, remove from pool
                if (badConnection) {
                    connections.removeElementAt(i);
                    trace("ConnectionPoolManager: Remove disconnected DB connection #" + i);
                    continue;
                }

                c = co.connection;
                co.inUse = true;

                trace("ConnectionPoolManager: Using existing DB connection #" + (i + 1));
                break;
            }
        }

        if (c == null) {
            c = createConnection();
            co = new ConnectionObject(c, true);
            connections.addElement(co);

            trace("ConnectionPoolManager: Creating new DB connection #" + connections.size());
        }

        return c;
    }


    /**
     * Marks a flag in the ConnectionObject to indicate this connection is no longer in use
     */
    public synchronized void freeConnection(Connection c) {
        if (c == null)
            return;

        ConnectionObject co = null;

        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);
            if (c == co.connection) {
                co.inUse = false;
                break;
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);
            if ((i + 1) > _openConnections && !co.inUse)
                removeConnection(co.connection);
        }
    }

    public void freeConnection(Connection c, PreparedStatement p, ResultSet r) {
        try {
            if (r != null) r.close();
            if (p != null) p.close();
            freeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection c, Statement s, ResultSet r) {
        try {
            if (r != null) r.close();
            if (s != null) s.close();
            freeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection c, PreparedStatement p) {
        try {
            if (p != null) p.close();
            freeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freeConnection(Connection c, Statement s) {
        try {
            if (s != null) s.close();
            freeConnection(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Marks a flag in the ConnectionObject to indicate this connection is no longer in use
     */
    public synchronized void removeConnection(Connection c) {
        if (c == null)
            return;

        ConnectionObject co = null;
        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);
            if (c == co.connection) {
                try {
                    c.close();
                    connections.removeElementAt(i);
                    trace("Removed " + c.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }


    private Connection createConnection()
            throws SQLException {
        Connection con = null;

        try {
            if (_dbUser == null)
                _dbUser = "";
            if (_dbPwd == null)
                _dbPwd = "";

            Properties props = new Properties();
            props.put("user", _dbUser);
            props.put("password", _dbPwd);

            con = DriverManager.getConnection(SSHJ_URL, props);
        } catch (Throwable t) {
            throw new SQLException(t.getMessage());
        }

        return con;
    }


    /**
     * Closes all connections and clears out the connection pool
     */
    public void releaseFreeConnections() {
        trace("ConnectionPoolManager.releaseFreeConnections()");

        Connection c = null;
        ConnectionObject co = null;

        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);
            if (!co.inUse)
                removeConnection(co.connection);
        }
    }


    /**
     * Closes all connections and clears out the connection pool
     */
    public void finalize() {
        trace("ConnectionPoolManager.finalize()");

        Connection c = null;
        ConnectionObject co = null;

        for (int i = 0; i < connections.size(); i++) {
            co = (ConnectionObject) connections.elementAt(i);
            try {
                co.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            co = null;
        }

        connections.removeAllElements();
    }


    private void trace(String s) {
        if (_traceOn)
            System.err.println(s);
    }

}
