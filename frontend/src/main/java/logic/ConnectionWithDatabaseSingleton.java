package logic;

import connection.ConnectionWithServer;

public class ConnectionWithDatabaseSingleton {
    private static final ConnectionWithServer connection = new ConnectionWithServer();

    public static ConnectionWithServer getInstance() {
        return connection;
    }
}
