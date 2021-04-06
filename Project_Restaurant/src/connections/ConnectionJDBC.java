package connections;

import resources.ReadProperties;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC implements Connection {
    ReadProperties readProperties;
    private String JDBC_URL ;
    private String JDBC_USER;
    private String JDBC_PASSWORD;

    /**
     * Aqui tengo las propiedades de la conexion
     */
    public ConnectionJDBC() {
        readProperties = new ReadProperties();
        this.JDBC_URL = readProperties.getJdbc() + readProperties.getDatabase();
        this.JDBC_USER = readProperties.getUser();
        this.JDBC_PASSWORD = readProperties.getPassword();
    }

    @Override
    public void connect() {}

    @Override
    public void close() {}

    /**
     * @autor Leimer Castellano
     * @return devuelve una conexion a la base de datos donde apunta
     * @throws SQLException
     * @throws IOException
     */
    public java.sql.Connection getMyConnection() throws SQLException, IOException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}

