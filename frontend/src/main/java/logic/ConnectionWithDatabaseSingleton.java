package logic;

import connection.ConnectionWithServer;
import lombok.Getter;
import lombok.Setter;

public class ConnectionWithDatabaseSingleton {
    private static final ConnectionWithServer connection = new ConnectionWithServer();

    @Setter
    @Getter
    private static String userName;

    public static ConnectionWithServer getInstance() {
        return connection;
    }
}
